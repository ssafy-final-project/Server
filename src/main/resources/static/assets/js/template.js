// 아파트 단위 마커 템플릿 생성 함수
function createLowLevelMarkerTemplate(data) {
  let id = 1
  let count = data.dealCount + " 건";
  let price = data.avgPrice;

  return `
    <button class = "card text-bg-primary align-items-center"
            style = "width: 6rem"
            onclick = "javascript:setBuilding(${id})">
        <div class = "h6 text-center pt-1">
            ${count}
        </div>
        <div class = "h5 text-center">
            <strong>${priceToUk(price)}</strong>
        </div>
    </button>
  `;
}

// 구군, 동 단위 마커 템플릿 생성 함수
function createHighLevelMarkerTemplate(data) {
  let name = data.statName;
  let price = data.avgPrice;
  return `
    <button class = "card ${price != 0 ? "text-bg-primary" : "text-bg-secondary"} align-items-center"
            style = "display: inline-block; border-radius: 30px;" ${price != 0 ? "" : "disabled"}>
        <div class = "text-center mx-3"
      style = "font-size: 12px;">
            ${name}</br>
    <strong style = "font-size: 14px;">${priceToUk(price)}</strong>
        </div>
    </button>
  `;
}

