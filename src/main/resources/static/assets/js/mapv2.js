const MapManager = {
  MarkerManager: {
    gugunDataList: [],
    dongDataList: [],
    markerList: [],
    chunkRepository: null,

    // 각 레이어 별 확대 레벨
    outFocusLevel: 12,
    gugunLevel: 6,
    dongLevel: 3,

    // 아파트 마커 클리어
    clearAptMarkers: function () {
      this.setMarkersVisibility(null, this.markerList, false);
      this.markerList = [];
    },

    // 지도의 확대 수준에 따라 마커 표시 여부 조정
    updateVerticalMarkers: function (map) {
      const level = map.getLevel();
      this.setMarkersVisibility(map, this.gugunDataList, level <= this.outFocusLevel && level > this.gugunLevel);
      this.chunkRepository.setChunkedMarkersVisibility(map, level <= this.gugunLevel && level > this.dongLevel);
      this.setMarkersVisibility(map, this.markerList, level <= this.dongLevel);
    },
	
	// 수평 이동시 마커 표시 여부 조정
	updateParallelMarkers: function (map) {
		const level = map.getLevel();
		if(level <= this.gugunLevel && level > this.dongLevel) this.chunkRepository.updateParallelChunkedMarker(map);
		if(level <= this.dongLevel) this.setMarkersVisibility(map, this.markerList, true);
	},

    // 마커 리스트에 따라 지도에 표시/숨기기
    setMarkersVisibility: function (map, list, visible) {
      for (const item of list) item.overlay.setMap(visible ? map : null);
    },

    // 지역 데이터를 요청하고, 지도의 마커 추가
    loadRegionData: async function (endpoint, dataList, overlayTemplate) {
      const response = await fetch(`${root}/${endpoint}`);
      const data = await response.json();
      for (const item of data) {
        const coords = new kakao.maps.LatLng(item.latitude, item.longitude);
        item.overlay = this.createOverlay(coords, overlayTemplate(item));
        item.overlay.setMap(null);
      }
      dataList.push(...data);
    },

    // 오버레이 생성 함수
    createOverlay: function (coords, content) {
      return new kakao.maps.CustomOverlay({
        position: coords,
        content: content,
        xAnchor: 0,
        yAnchor: 0,
      });
    },

    // 구군 및 동 데이터 로드
    init: async function () {
      await Promise.all([
        (() => this.loadRegionData("stat/gugun", this.gugunDataList, (data) => createHighLevelMarkerTemplate(data)))(),
        (() => this.loadRegionData("stat/dong", this.dongDataList, (data) => createHighLevelMarkerTemplate(data)))(),
      ]);
      this.createChunkRepository(this.dongDataList);
    },

    // 최적화를 위한 청크 based 마커 저장소 생성
    createChunkRepository: function (dataList) {
      const lowerLat = 33,
        upperLat = 39,
        lowerLng = 124,
        upperLng = 132;

      const chunkAmount = 60;
      const chunkSizeLat = (upperLat - lowerLat) / chunkAmount;
      const chunkSizeLng = (upperLng - lowerLng) / chunkAmount;
      const chunk = Array.from({ length: chunkAmount }, () => Array.from({ length: chunkAmount }, () => []));

      // Chunking
      for (const e of dataList) {
        if (e.latitude < lowerLat || e.latitude >= upperLat || e.longitude < lowerLng || e.longitude >= upperLng) {
          continue;
        }
        const latidx = Math.floor((e.latitude - lowerLat) / chunkSizeLat);
        const lngidx = Math.floor((e.longitude - lowerLng) / chunkSizeLng);
        chunk[latidx][lngidx].push(e);
      }

      this.chunkRepository = {
        chunk,
        chunkSizeLat,
        chunkSizeLng,
        chunkAmount,
        lowerLat,
        upperLat,
        lowerLng,
        upperLng,
        loadedChunk: new Set(),
        setChunkedMarkersVisibility: function (map, visible) {
          const coords = map.getCenter();
          const latitude = coords.getLat();
          const longitude = coords.getLng();

          // Early Returns
          if (!visible) {
            this.clearChunkedMarkers();
            return;
          }
          if (latitude < this.lowerLat || latitude >= this.upperLat || longitude < this.lowerLng || longitude >= this.upperLng) {
            return;
          }

          // Make markers visible
          const latidx = Math.floor((latitude - this.lowerLat) / this.chunkSizeLat);
          const lngidx = Math.floor((longitude - this.lowerLng) / this.chunkSizeLng);

          for (let lati = latidx - 1; lati <= latidx + 1; lati++)
            for (let lngi = lngidx - 1; lngi <= lngidx + 1; lngi++) {
              for (const e of this.chunk[lati][lngi]) e.overlay.setMap(map);
              this.loadedChunk.add(lati * this.chunkAmount + lngi);
            }
        },
		
        clearChunkedMarkers: function () {
          for (const c of this.loadedChunk) {
            let lati = Math.floor(c / this.chunkAmount);
            let lngi = c % this.chunkAmount;
            for (const e of this.chunk[lati][lngi]) e.overlay.setMap(null);
          }
          this.loadedChunk.clear();
        },
		
		updateParallelChunkedMarker : function (map) {
			this.clearChunkedMarkers();
			this.setChunkedMarkersVisibility(map, true);
		}
      };
    },
  },

  map: null,
  geocoder: null,
  ps: null,

  // 현재 중심 좌표 법정동코드, 이동 후 코드와 같으면 재요청 안함
  current_regcode: "00000",
  current_address: "nowhere",

  // Kakao 지도 설정 및 이벤트 바인딩
  init: function () {
    this.map = new kakao.maps.Map(document.getElementById("map"), {
      center: new kakao.maps.LatLng(37.470701, 127.020667),
      level: 7,
    });
    this.geocoder = new kakao.maps.services.Geocoder();
    this.ps = new kakao.maps.services.Places();

    kakao.maps.event.addListener(
      this.map,
      "center_changed",
      debounce(() => this.refreshResult(), 200)
    );
    kakao.maps.event.addListener(this.map, "zoom_changed", () => this.MarkerManager.updateVerticalMarkers(this.map));

    this.MarkerManager.init();
  },

  // 카카오맵 API로부터 얻어낸 주소를 법정동코드로 변환
  convertAddressToRegcode: async function (result) {
    let nameDo = DO_CONVERTER.get(result[0].address.region_1depth_name);
    let nameSi = nameDo + " " + result[0].address.region_2depth_name;
    let nameDong = nameSi + " " + result[0].address.region_3depth_name;

    let regcode = await this.getRegcode("*00000000", nameDo);
    regcode = await this.getRegcode(regcode.slice(0, 2) + "*00000", nameSi);
    regcode = await this.getRegcode(regcode.slice(0, 5) + "*00", nameDong);
    return regcode;
  },

  // 특정 레벨의 행정구역 코드 요청
  getRegcode: async function (pattern, name) {
    const url = `https://grpc-proxy-server-mkvo6j4wsq-du.a.run.app/v1/regcodes?regcode_pattern=${pattern}&is_ignore_zero=true`;
    const response = await fetch(url);
    const data = await response.json();
    return data.regcodes.find((item) => item.name === name)?.code || "";
  },

  // 지도의 중심 좌표에 따라 아파트 정보 요청
  refreshResult: async function () {
    const coords = this.map.getCenter();

    this.geocoder.coord2Address(coords.getLng(), coords.getLat(), async (result, status) => {
      if (status === kakao.maps.services.Status.OK) {
        const converted_regcode = (await this.convertAddressToRegcode(result)).slice(0, 5);

        if (this.map.getLevel() <= this.MarkerManager.dongLevel) {
		  if (!converted_regcode || this.current_regcode == converted_regcode || yearDropdownTitle === "연도") return;
          console.log("APT Stat Requested :", converted_regcode);

          this.current_address = result[0].address;
          this.current_regcode = converted_regcode;

          this.MarkerManager.clearAptMarkers();

          await this.MarkerManager.loadRegionData(`stat/apt/${this.current_regcode}`, this.MarkerManager.markerList, (data) =>
            createLowLevelMarkerTemplate(data)
          );
        }
		this.MarkerManager.updateParallelMarkers(this.map);
      }
    });
  },
};

/*
// 즐겨찾기 추가/삭제 통합 함수
async function toggleFavorite(dongcode, add) {
  const userId = document.getElementById("infoUserId").value;
  const url = `${root}/member/favorite/${userId}/${dongcode}`;
  const method = add ? "POST" : "DELETE";
  await fetch(url, { method });
  updateBookmarkButton(add, dongcode);
  await getFavorites();
}

// 즐겨찾기 버튼 상태 업데이트
function updateBookmarkButton(isFavorite, dongcode) {
  const bookmarkButton = document.querySelector("#bookmarkbtn");
  bookmarkButton.className = isFavorite ? "btn btn-warning" : "btn btn-outline-warning";
  bookmarkButton.onclick = () => toggleFavorite(dongcode, !isFavorite);
}
*/

// 초기화 함수
async function initialize() {
  setYearDropdown("2011", "2024");
  MapManager.init();
}

window.onload = initialize;
