function initNotice() {
  listNotice(1);
}

async function listNotice(page) {
  const url = root + `/notice?pageno=${page}`;
  const options = {
    method: "GET",
  };

  let data_json = null;
  await fetch(url, options)
    .then((response) => response.json())
    .then((data) => {
      console.log(data);
      data_json = data;
    });

  const tBody = document.querySelector("#noticeModalTable>tbody");
  const nBody = document.querySelector("#noticeModalNav");

  const dlist = data_json.list;
  const dnav = data_json.nav;

  let newInnerHTML = "";
  for (let m of dlist) {
    newInnerHTML += `
	        <tr>
	            <td class = "text-center">${m.notice_no}</td>
	            <td>
				<a
					onclick = "detailNotice(${m.notice_no})"
					data-bs-toggle="modal"
					data-bs-target="#noticeDetailModal"
					id="btn-list">
					${m.title}
				</a></td>
	            <td class = "text-center">${m.author}</td>
				<td class = "text-center">${m.notice_date}</td>
				<td class = "text-center">${m.hit}</td>
	        </tr>
	        `;
  }
  tBody.innerHTML = newInnerHTML;
  nBody.innerHTML = dnav;

  cur_page = page;
}

async function insertNotice() {
  const data = {
    title: document.querySelector("#subject").value,
    content: document.querySelector("#content").value,
    author: document.querySelector("#infoUserId").value,
    notice_no: ins_mode === "update" ? update_data.notice_no : null,
  };

  console.log(data);

  const url = root + `/notice`;
  const options = {
    method: ins_mode === "insert" ? "POST" : "PUT",
    headers: {
      "Content-Type": "application/json",
    },
    body: JSON.stringify(data),
  };

  await fetch(url, options).then((response) => console.log(response));

  document.querySelector("#subject").value = "";
  document.querySelector("#content").value = "";
  ins_mode = "insert";
  initNotice();
}

async function detailNotice(noticeno) {
  const url = root + `/notice/${noticeno}`;
  const options = {
    method: "GET",
  };

  let data_json = null;
  await fetch(url, options)
    .then((response) => response.json())
    .then((data) => {
      data_json = data;
    });

  const body = document.querySelector("#noticeDetailModalBody");
  let newInnerHTML = `
	<div class="row justify-content-center">
		<div class="col-lg-8 col-md-10 col-sm-12">
		   <div class="row my-2">
		      <h2 class="text-secondary">${data_json.notice_no}. ${data_json.title}</h2>
		   </div>
		   <div class="row">
		       <div class="col-md-8">
				  <div class="clearfix align-content-center">
				  <p>
			         <span class="fw-bold">${data_json.author}</span> <br />
			         <span class="text-secondary fw-light"> ${data_json.notice_date} 조회 : ${data_json.hit} </span>
				  </p>
			   	  </div>
		  	   </div>
	
			   <div class="col-md-4 align-self-center text-end"></div>
		   </div>
		   <hr/>
		   <div class="row">
			   <div class="divider mb-3"></div>
			   <div class="text-secondary">
			     ${data_json.content}
			   </div>
			   <div class="divider mt-3 mb-3"></div>
		   </div>
		   <hr/>
		   <div class="row">
			   <div class="d-flex justify-content-end">
			     <button
				 	type="button"
					onclick = "listNotice(${cur_page})"
					data-bs-toggle="modal"
					data-bs-target="#noticeModal"
					id="btn-list"
					class="btn btn-outline-primary mb-3">
			       글목록
			     </button>
			     <button
				 	type="button"
				 	onclick = "updateNoticeSetter(${data_json.notice_no})"
				 	data-bs-toggle="modal"
					data-bs-target="#noticeInsertModal"
					id="btn-mv-modify"
					class="btn btn-outline-success mb-3 ms-1">
			       글수정
			     </button>
			     <button
				 	type="button"
					onclick = "deleteNotice(${data_json.notice_no})"
					data-bs-toggle="modal"
					data-bs-target="#noticeModal"
					id="btn-delete"
					class="btn btn-outline-danger mb-3 ms-1">
			       글삭제
			     </button>
			   </div>
			</div>
		 </div>
	</div>
	`;

  body.innerHTML = newInnerHTML;
}

async function updateNoticeSetter(noticeno) {
  const url = root + `/notice/${noticeno}`;
  const options = {
    method: "GET",
  };

  let data_json = null;
  await fetch(url, options)
    .then((response) => response.json())
    .then((data) => {
      data_json = data;
    });

  const title = document.querySelector("#subject");
  const content = document.querySelector("#content");

  title.value = data_json.title;
  content.value = data_json.content;
  ins_mode = "update";
  update_data = data_json;
  console.log(update_data);
}

async function deleteNotice(noticeno) {
  const url = root + `/notice/${noticeno}`;
  const options = {
    method: "DELETE",
  };

  await fetch(url, options).then((response) => console.log(response));

  listNotice(cur_page);
}
