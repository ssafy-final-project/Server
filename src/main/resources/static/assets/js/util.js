var orderby_name = (m1, m2) => m1.name.localeCompare(m2.name);
var orderby_distance = (m1, m2) => Haversine(m1) - Haversine(m2);
var orderby_recomm = (m1, m2) => calcRecomm(m2) - calcRecomm(m1);

function ymdToDate(year, month, day) {
	return "" + year + ". " + (month < 10 ? "0" + month : month) + ". " + (day < 10 ? "0" + day : day);
}

function areaToPY(area) {
	return (area / 3.33).toFixed(2) + "평";
}

function priceToUk(price) {
	return (price / 10000).toFixed(2) + "억";
}

function getFF(pattern) {
	const _pattern = pattern + "";
	let N = _pattern.length;
	let FF = new Array(N);
	let l = 0, r = 1;

	for (let i = 0; i < N; i++)
		FF[i] = 0;

	for (; r < N; r++) {
		while (l > 0 && _pattern[l] != _pattern[r])
			l = FF[l - 1];
		if (_pattern[l] == _pattern[r])
			FF[r] = ++l;
	}
	return FF;
}

function KMP(text, pattern) {
	const _text = text + "";
	const _pattern = pattern + "";
	let FF = getFF(pattern);
	let N = _text.length, M = _pattern.length;
	let l = 0, r = 0;

	for (; r < N; r++) {
		while (l > 0 && _pattern[l] != _text[r])
			l = FF[l - 1];
		if (_pattern[l] == _text[r]) {
			if (l == M - 1)
				return r - l;
			else
				++l;
		}
	}
	return -1;
}

function Haversine(marker) {
	// 출발지 (지도 중심)
	const lat1 = map.getCenter().getLat();
	const lon1 = map.getCenter().getLng();

	// 목적지 (마커)
	const lat2 = marker.lat;
	const lon2 = marker.lng;

	// phi, lambda을 라디안으로 변환
	const phi1 = lat1 * Math.PI / 180
	const phi2 = lat2 * Math.PI / 180;
	const delta = (lon2 - lon1) * Math.PI / 180;

	// 지구의 반지름 (meter)
	const R = 6371 * 1000;

	const d = Math.acos(Math.sin(phi1) * Math.sin(phi2) +
		Math.cos(phi1) * Math.cos(phi2) * Math.cos(delta)
	) * R;

	return d;
}

function calcRecomm(marker) {
	const dist = Haversine(marker) / 1000;
	const price = marker.price;
	const area = marker.area;
	const buildyear = marker.buildyear;
	const todayYear = (new Date()).getFullYear();

	const ppa = price.substr(0, price.indexOf("억")) / area.substr(0, area.indexOf("평"));
	const regBY = (todayYear - buildyear);

	const mulBY = 3000;
	const mulPPA = 600;
	const mulDist = 800;

	const pts = mulBY / regBY + mulPPA / ppa + mulDist / dist;
	return pts;
}

function mergeSort(arr, order) {
	if (arr.length <= 1) {
		return arr;
	}

	const middle = Math.floor(arr.length / 2);
	const left = arr.slice(0, middle);
	const right = arr.slice(middle);

	return merge(mergeSort(left, order), mergeSort(right, order), order);
}

function merge(left, right, order) {
	let result = [];
	let i = 0, j = 0;

	while (i < left.length && j < right.length) {
		if (order(left[i], right[j]) <= 0) {
			result.push(left[i]);
			i++;
		} else {
			result.push(right[j]);
			j++;
		}
	}

	return result.concat(left.slice(i)).concat(right.slice(j));
}

// 지연 호출을 위한 디바운스 함수
function debounce(func, delay) {
  return function() {
    if (delayed_timeout) clearTimeout(delayed_timeout);
    delayed_timeout = setTimeout(func, delay);
  };
}

var DO_CONVERTER = new Map();
DO_CONVERTER.set('전남', '전라남도');
DO_CONVERTER.set('경남', '경상남도');
DO_CONVERTER.set('경북', '경상북도');
DO_CONVERTER.set('전북특별자치도', '전라북도');
DO_CONVERTER.set('충북', '충청북도');
DO_CONVERTER.set('충남', '충청남도');
DO_CONVERTER.set('경기', '경기도');
DO_CONVERTER.set('강원도', '강원특별자치도');
DO_CONVERTER.set('제주특별자치도', '제주특별자치도');
DO_CONVERTER.set('서울', '서울특별시');
DO_CONVERTER.set('인천', '인천광역시');
DO_CONVERTER.set('부산', '부산광역시');
DO_CONVERTER.set('울산', '울산광역시');
DO_CONVERTER.set('대구', '대구광역시');
DO_CONVERTER.set('광주', '광주광역시');
DO_CONVERTER.set('대전', '대전광역시');