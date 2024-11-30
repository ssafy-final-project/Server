const KAKAO_API_KEY = "2621b45f3e14deb49c3159dfbfcc533a"; //"53145e5b6696588dc70ac4fcb0dda36a"
const APART_API_KEY =
  "Lu%2BaLm1PD5tGJtG%2FN6MAj6whAQdq%2ByY4AJYCjpfUfA7Jti8ukfkQs6ec%2FI4nC5U%2Bo59NtWbeUB9OTjGbITDG1g%3D%3D";

var map;
var geocoder;
var ps;

var gugun_data_list = [];
var dong_data_list = [];
var marker_list = [];
var markersByDongCode = {};
let id;

var searched_regcode;
var address_regcode = "";
var searched_address = "";
var delayed_timeout;

var user_favorites;

var cur_page = 1;
var ins_mode = "insert";
var update_data = null;
var appmode = 12; // 1 Test, 12 Release

var root = document.querySelector("#root").getAttribute("value");

function loadMap() {
  var mapContainer = document.getElementById("map"), // 지도를 표시할 div
    mapOption = {
      center: new kakao.maps.LatLng(37.470701, 127.020667), // 지도의 중심좌표
      level: 7, // 지도의 확대 레벨
    };

  map = new kakao.maps.Map(mapContainer, mapOption);
  geocoder = new kakao.maps.services.Geocoder();
  ps = new kakao.maps.services.Places();

  let delayed_callback = function () {
    if (delayed_timeout) clearTimeout(delayed_timeout);
    delayed_timeout = setTimeout(refreshResult, 10);
  }

  kakao.maps.event.addListener(map, "center_changed", delayed_callback);
  kakao.maps.event.addListener(map, "zoom_changed", fliterMarker);
}

function refreshResult() {
  const coords = map.getCenter();
  const yearDropdownTitle = document.getElementById("yearDropdownTitle");

  geocoder.coord2Address(coords.getLng(), coords.getLat(), async function (result, status) {
    if (status === kakao.maps.services.Status.OK) {
      console.log(result);
      const converted_regcode = await convertAddressToRegcode(result);

      if (
        !converted_regcode ||
        address_regcode.substring(0, 5) == converted_regcode.substring(0, 5) ||
		yearDropdownTitle.innerText == "연도"
      ) {
        return;
      }

      if (map.getLevel() <= 3) {
        // Apt
        address_regcode = converted_regcode;
        searched_address = result[0].address;
        await sendRequestApartment(
          address_regcode.substring(0, 5),
          yearDropdownTitle.innerText,
          appmode
        );
      }
    }
  });
}

function fliterMarker() {
  const current_mapLevel = map.getLevel();

  if(current_mapLevel > 11) 
  if(current_mapLevel > 5) 
  if(current_mapLevel > 3) setMarker(marker_list, false);

  if(current_mapLevel <= 3) {
    setMarker(gugun_data_list, false);
    setMarker(dong_data_list, false);
    setMarker(marker_list, true);
  }
  else if(current_mapLevel <= 5) {
    setMarker(gugun_data_list, false);
    setMarker(dong_data_list, true);
    setMarker(marker_list, false);
  }
  else if(current_mapLevel <= 11) {
    setMarker(gugun_data_list, true);
    setMarker(dong_data_list, false);
    setMarker(marker_list, false);
  } else {
    setMarker(gugun_data_list, false);
    setMarker(dong_data_list, false);
    setMarker(marker_list, false);
  }
}

function setMarker(list, set) {
  for (m of list)
    m.overlay.setMap(set ? map : null);
}



function clearResultList() {
  var listE = document.getElementById("result_box");
  listE.innerHTML = "";
}

function setResultList(id, bname, price, area, jungong) {
  var listE = document.getElementById("result_box");
  var innerContent = listE.innerHTML;

  innerContent += `<li class="list-group-item" onclick="javascript:setBuilding(${id})">
        <div class="ms-2 me-auto">
            <div class="fw-bold">${bname}</div>
        </div>
        <span class="badge text-bg-primary rounded-pill">${price}</span>
        <span class="badge text-bg-primary rounded-pill">${area}</span>
        <span class="badge text-bg-primary rounded-pill">${jungong}년 준공</span>
    </li>`;

  listE.innerHTML = innerContent;
}

function searchList() {
  var keyword = document.getElementById("searchInput").value;

  // 키워드로 장소를 검색합니다
  ps.keywordSearch(keyword, (data, status, pagination) => {
    if (status === kakao.maps.services.Status.OK) {
      var coords = new kakao.maps.LatLng(data[0].y, data[0].x);

      // 지도의 중심을 결과값으로 받은 위치로 이동시킵니다
      map.setCenter(coords);
    }
  });
}

async function convertAddressToRegcode(result) {
  let nameDo = DO_CONVERTER.get(result[0].address.region_1depth_name);
  let nameSi = nameDo + " " + result[0].address.region_2depth_name;
  let nameDong = nameSi + " " + result[0].address.region_3depth_name;

  let regcodes = 0;
  await sendRequest("*00000000");
  for (regs of searched_regcode.regcodes) {
    if (nameDo === regs.name) regcodes = parseInt(regs.code);
  }

  await sendRequest(regcodes.toString().substring(0, 2) + "*00000");
  for (regs of searched_regcode.regcodes) {
    if (nameSi === regs.name) regcodes = parseInt(regs.code);
  }

  await sendRequest(regcodes.toString().substring(0, 5) + "*00");
  for (regs of searched_regcode.regcodes) {
    if (nameDong === regs.name) regcodes = parseInt(regs.code);
  }

  return regcodes.toString();
}

async function sendRequest(regcode) {
  const url = "https://grpc-proxy-server-mkvo6j4wsq-du.a.run.app/v1/regcodes";
  let params = "regcode_pattern=" + regcode + "&is_ignore_zero=true";
  await fetch(`${url}?${params}`)
    .then((response) => response.json())
    .then((data) => {
      searched_regcode = data;
    });
}

async function sendRequestApartment(regcode, year, mode) {
  const url = root + `/apt?district_code=${regcode}&year=${year}`;
  const options = {
    method: "GET",
  };

  // 기존 마커 및 오버레이 제거
  for (let m of marker_list) {
    if (m.overlay) {
      m.overlay.setMap(null); // 기존 오버레이 제거
    }
  }
  marker_list = []; // 마커 리스트 초기화
  clearResultList(); // 결과 리스트 초기화
  id = 0;

  // 아파트 정보를 가져오는 요청
  const apartmentData = await fetch(url, options)
    .then((response) => {
      if (!response.ok) {
        throw new Error("Network response was not ok");
      }
      return response.json();
    })
    .catch((error) => {
      console.error("Error fetching apartment data:", error);
      return []; // 오류 발생 시 빈 배열 반환
    });

  // 아파트 데이터 파싱 및 마커 추가
  parseApartmentItems(apartmentData);

  // // 각 동마다 오염도와 쓰레기 정보 마커 추가
  // markersByDongCode = {}; // 동 코드별 마커 저장

  // for (const apt of apartmentData) {
  //   const dongcode = apt.district_code; // 해당 아파트의 동 코드

  //   // 동 코드가 이미 추가되었는지 확인
  //   if (!markersByDongCode[dongcode]) {
  //     await sendRequestPollution(apt, dongcode);
  //   }
  // }
}

async function sendRequestDistrict() {
  const url = root + `/apt?district_code=${regcode}&year=${year}`;
  const options = {
    method: "GET",
  };
}

async function sendRequestPollution(apt, dongcode) {
  const url = root + `/env/pollution/${dongcode}`;
  const options = {
    method: "GET",
  };

  // 오염도 정보 요청
  let pollutionData = null;
  await fetch(url, options)
    .then((response) => response.json())
    .then((data) => {
      pollutionData = data;
    })
    .catch((error) => {
      console.error("Error fetching pollution data:", error);
      return null; // 오류 발생 시 null 반환
    });

  // 오염도 마커 추가
  const avgpm2_5 = pollutionData ? pollutionData[0].avgpm2_5 : "N/A";
  const avgpm10 = pollutionData ? pollutionData[0].avgpm10 : "N/A";
  const name = pollutionData ? pollutionData[0].locName : "N/A";
  const coords = new kakao.maps.LatLng(apt.latitude, apt.longitude);

  var content = `
	<button class = "card text-bg-warning align-items-center"
	        style = "width: 9rem;"
			data-bs-toggle="modal"
			data-bs-target="#trashModal"
			id="miscInfoBtn"
			onclick = "javascript:setTrashInfo(${dongcode})">
	    <div class = "h3 text-center pt-1">
	        <strong>${name}</strong>
	    </div>
	    <div class = "text-center">
	        PM2.5 ${(Math.round(avgpm2_5 * 100) / 100).toFixed(2)}
	    </div>
		<div class = "text-center">
		    PM10 ${(Math.round(avgpm10 * 100) / 100).toFixed(2)}
		</div>
	</button>
	`;

  // 결과값으로 받은 위치를 마커로 표시합니다
  let customOverlay = new kakao.maps.CustomOverlay({
    position: coords,
    content: content,
    xAnchor: 0,
    yAnchor: 0,
    zIndex: 1,
  });
  customOverlay.setMap(map);

  markersByDongCode[dongcode] = { pollution: customOverlay }; // 동 코드별로 오염도 마커 저장
}

function parseApartmentItems(json) {
  for (let apt of json) {
    let jibun = apt.juso;
    let dealday =
      apt.latest_deal_year + ". " + apt.latest_deal_month + ". " + apt.latest_deal_day + "";
    let bname = apt.apt_name;
    let jungong = apt.build_year;
    let price = priceToUk(apt.deals[apt.deals.length - 1].deal_price);
    let area = areaToPY(apt.deals[apt.deals.length - 1].exclu_use_ar);
    let code = apt.apt_seq;
    let lat = apt.latitude;
    let lng = apt.longitude;

    var coords = new kakao.maps.LatLng(apt.latitude, apt.longitude);

    var content = `
        <button class = "card text-bg-primary align-items-center"
                style = "width: 6rem"
                onclick = "javascript:setBuilding(${id})">
            <div class = "h6 text-center pt-1">
                ${area}
            </div>
            <div class = "h5 text-center">
                <strong>${price}</strong>
            </div>
        </button>
        `;

    // 결과값으로 받은 위치를 마커로 표시합니다
    let customOverlay = new kakao.maps.CustomOverlay({
      position: coords,
      content: content,
      xAnchor: 0,
      yAnchor: 0,
    });
    customOverlay.setMap(map);

    let marker = {
      overlay: customOverlay,
      id: id,
      name: bname,
      dealday: dealday,
      buildyear: jungong,
      price: price,
      area: area,
      address:
        searched_address.region_1depth_name +
        " " +
        searched_address.region_2depth_name +
        " " +
        jibun,
      code: code,
      lat: lat,
      lng: lng,
    };

    marker_list.push(marker);
    id++;
  }

  filterList("");
}

function filterList(pattern, order) {
  console.log(pattern);
  clearResultList();
  let filtered_list = [];
  for (let marker of marker_list) {
    if (pattern != "" && KMP(marker.name, pattern) == -1) continue;
    filtered_list.push(marker);
  }

  if (order) {
    let ordered_list = mergeSort(filtered_list, order);
    filtered_list = ordered_list;
  }

  let cnt = 0;
  for (let marker of filtered_list) {
    let id = marker.id;
    let bname = marker.name;
    let price = marker.price;
    let area = marker.area;
    let jungong = marker.buildyear;

    setResultList(id, bname, price, area, jungong);
    if (cnt++ > 100) break;
  }
}

async function setBuilding(id) {
  const url = root + `/apt/${marker_list[id].code}`;
  const options = {
    method: "GET",
  };

  let data_json = null;
  await fetch(url, options)
    .then((response) => response.json())
    .then((data) => {
      data_json = data;
    });

  list_html = "";
  for (let m of data_json) {
    list_html += `
        <tr>
            <td>${ymdToDate(m.deal_year, m.deal_month, m.deal_day)}</td>
            <td>${priceToUk(m.deal_price)}</td>
            <td>${areaToPY(m.exclu_use_ar)}</td>
        </tr>
        `;
  }

  let resbox = document.getElementById("spec_result_box");
  resbox.innerHTML = `
    <div class = "h5 p-2 text-bg-light"><strong>${marker_list[id].name}</strong></div>
    <div class = "p-2 text-bg-light">${marker_list[id].address}</div>
    <div class = "p-2 text-bg-light"><strong>거래 내역</strong></div>
    <table class="table">
        <thead>
            <tr class="table-dark">
                <th>계약일</th>
                <th>가격</th>
                <th>평형</th>
            </tr>
        </thead>
        <tbody>
            ${list_html}
        </tbody>
    </table>
    `;
}

async function setTrashInfo(dongcode) {
  const url = root + `/env/trashbag/${dongcode}`;
  const options = {
    method: "GET",
  };
  // 쓰레기 정보 요청
  let trashData = null;
  await fetch(url, options)
    .then((response) => response.json())
    .then((data) => {
      trashData = data;
    })
    .catch((error) => {
      console.error("Error fetching trash data:", error);
      return null; // 오류 발생 시 null 반환
    });

  const tBody = document.querySelector("#trashModalTable>tbody");
  const bookmarkButton = document.querySelector("#bookmarkbtn");

  if (user_favorites) {
    bookmarkButton.onclick = function () {
      addFavorites(dongcode);
    };
  }
  if (user_favorites && user_favorites.find((e) => e.dong_code.substr(0, 5) == dongcode)) {
    bookmarkButton.className = "btn btn-warning";
    bookmarkButton.onclick = function () {
      removeFavorites(dongcode);
    };
  }

  let newInnerHTML = "";
  for (let t of trashData) {
    newInnerHTML += `
	        <tr>
	            <td class = "text-center">${t.bagType}</td>
				<td class = "text-center">${t.target}</td>
				<td class = "text-center">${t.usage}</td>
				<td class = "text-center">${t.disposalMethod}</td>
				<td class = "text-center">${t.managingDepartment}</td>
				<td class = "text-center">${t.type}</td>
				<td class = "text-center">${t.price}</td>
	        </tr>
	        `;
  }
  tBody.innerHTML = newInnerHTML;
}

async function getFavorites() {
  const userId = document.getElementById("infoUserId").value;

  const url = root + `/member/favorite/${userId}`;
  const options = {
    method: "GET",
  };

  await fetch(url, options)
    .then((response) => response.json())
    .then((data) => {
      user_favorites = data;
    })
    .catch((error) => {
      console.error("Error fetching trash data:", error);
      return null; // 오류 발생 시 null 반환
    });

  console.log(user_favorites);
}

async function addFavorites(dongcode) {
  const userId = document.getElementById("infoUserId").value;

  const url = root + `/member/favorite/${userId}/${dongcode}`;
  const options = {
    method: "POST",
  };

  await fetch(url, options)
    .then((response) => console.log(response))
    .catch((error) => {
      console.error("Error fetching trash data:", error);
    });

  const bookmarkButton = document.querySelector("#bookmarkbtn");
  bookmarkButton.className = "btn btn-warning";
  bookmarkButton.onclick = function () {
    removeFavorites(dongcode);
  };
  getFavorites();
}

async function removeFavorites(dongcode) {
  const userId = document.getElementById("infoUserId").value;

  const url = root + `/member/favorite/${userId}/${dongcode}`;
  const options = {
    method: "DELETE",
  };

  await fetch(url, options)
    .then((response) => console.log(response))
    .catch((error) => {
      console.error("Error fetching trash data:", error);
    });

  const bookmarkButton = document.querySelector("#bookmarkbtn");
  bookmarkButton.className = "btn btn-outline-warning";
  bookmarkButton.onclick = function () {
    addFavorites(dongcode);
  };
  getFavorites();
}

async function loadGugunData() {
  const url = root + `/stat/gugun`;
  const options = {
    method: "GET",
  };

  await fetch(url, options)
    .then(response => response.json())
    .then(data => gugun_data_list = data)

  for (let data of gugun_data_list) {
    let content = `
	        <button class = "card ${data.avgPrice != 0 ? "text-bg-primary" : "text-bg-secondary"} align-items-center"
	                style = "display: inline-block; border-radius: 30px;" ${data.avgPrice != 0 ? "" : "disabled"}>
	            <div class = "text-center mx-3"
					 style = "font-size: 12px;">
	                ${data.statName}</br>
					<strong style = "font-size: 14px;">${priceToUk(data.avgPrice)}</strong>
	            </div>
	        </button>
	        `;

    let coords = new kakao.maps.LatLng(data.latitude, data.longitude);
    let customOverlay = new kakao.maps.CustomOverlay({
      position: coords,
      content: content,
      xAnchor: 0,
      yAnchor: 0,
    });

    data.overlay = customOverlay;
    data.overlay.setMap(null);
  }
}

async function loadDongData() {
  const url = root + `/stat/dong`;
  const options = {
    method: "GET",
  };

  await fetch(url, options)
    .then(response => response.json())
    .then(data => dong_data_list = data)

  for (let data of dong_data_list) {
    let content = `
  	        <button class = "card ${data.avgPrice != 0 ? "text-bg-primary" : "text-bg-secondary"} align-items-center"
  	                style = "display: inline-block; border-radius: 30px;" ${data.avgPrice != 0 ? "" : "disabled"}>
  	            <div class = "text-center mx-3"
  					 style = "font-size: 12px;">
  	                ${data.statName}</br>
  					<strong style = "font-size: 14px;">${priceToUk(data.avgPrice)}</strong>
  	            </div>
  	        </button>
  	        `;

    let coords = new kakao.maps.LatLng(data.latitude, data.longitude);
    let customOverlay = new kakao.maps.CustomOverlay({
      position: coords,
      content: content,
      xAnchor: 0,
      yAnchor: 0,
    });

    data.overlay = customOverlay;
    data.overlay.setMap(null);
  }
}

// window.onload = function () {
//   setYearDropdown("2011", "2024");
//   loadMap();
//   loadGugunData();
//   loadDongData();
//   initNotice();
// };
