function setYearDropdown(startYear, endYear) {
	var menuE = document.getElementById("yearDropdown");

	var newElementHTML = "";
	for (let year = startYear; year <= endYear; year++) {
		newElementHTML += `<li><a class="dropdown-item" href="#" onclick="javascript:setYearDropdownTitle(${year})">${year}</a></li>`
	}
	menuE.innerHTML = newElementHTML;
}

function setYearDropdownTitle(title) {
	var menuTitleE = document.getElementById("yearDropdownTitle");
	menuTitleE.innerText = title;
}