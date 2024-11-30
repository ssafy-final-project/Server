<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%> <%@ taglib prefix = "c" uri="jakarta.tags.core" %>
<c:set var="root" value="${pageContext.request.contextPath}" scope="session" />

<!DOCTYPE html>
<html lang="en">
  <head>
    <meta charset="UTF-8" />
    <meta name="viewport" content="width=device-width, initial-scale=1.0" />
    <title>Document</title>
    <link
      href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css"
      rel="stylesheet"
      integrity="sha384-QWTKZyjpPEjISv5WaRU9OFeRpok6YctnYmDr5pNlyT2bRjXh0JMhjY6hW+ALEwIH"
      crossorigin="anonymous"
    />
  </head>
  <body>
    <!-- variables-->
    <input type="hidden" id="uid" value="" />

    <!-- map container-->
    <div class="fixed-top vh-100 z-n1" id="map"></div>

    <!-- 로그인 modal -->
    <div class="modal" tabindex="-1" id="loginModal">
      <div class="modal-dialog">
        <div class="modal-content">
          <div class="modal-header">
            <h5 class="modal-title">로그인</h5>
            <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
          </div>
          <div class="modal-body">
            <form action="">
              <label for="userid">아이디</label>
              <input class="form-control mb-3" type="text" name="userId" id="loginUserId" />
              <label for="userpw">비밀번호</label>
              <input class="form-control mb-3" type="password" name="userPw" id="loginUserPw" />
              <div class="text-danger text-center" id="loginModalInfo"></div>
            </form>
          </div>
          <div class="modal-footer">
            <button type="button" class="btn btn-secondary" data-bs-dismiss="modal">닫기</button>
            <button type="button" class="btn btn-primary" onclick="javascript:login()">로그인</button>
          </div>
        </div>
      </div>
    </div>
    <!-- 로그인 modal END -->
    <!-- 회원가입 modal -->
    <div class="modal" tabindex="-1" id="registerModal">
      <div class="modal-dialog">
        <div class="modal-content">
          <div class="modal-header">
            <h5 class="modal-title">회원가입</h5>
            <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
          </div>
          <div class="modal-body">
            <form action="">
              <label for="userid">아이디</label>
              <input class="form-control mb-3" type="text" name="id" id="registerUserId" onkeyup="javascript:checkIdAvailability()" />
              <p id="idCheckMessage"></p>
              <label for="userpw">비밀번호</label>
              <input class="form-control mb-3" type="password" name="pw" id="registerUserPw" />
              <label for="userpw">이름</label>
              <input class="form-control mb-3" type="text" name="name" id="registerUserName" />
              <label for="userpw">주소</label>
              <input class="form-control mb-3" type="text" name="address" id="registerUserAddress" />
              <label for="userpw">전화번호</label>
              <input class="form-control mb-3" type="tel" name="phone" id="registerUserPhone" />
              <div class="text-danger text-center" id="registerModalInfo"></div>
            </form>
          </div>
          <div class="modal-footer">
            <button type="button" class="btn btn-secondary" data-bs-dismiss="modal">닫기</button>
            <button type="button" class="btn btn-primary" onclick="javascript:registerUser()">회원가입</button>
          </div>
        </div>
      </div>
    </div>
    <!-- 회원가입 modal END -->
    <!-- 회원정보 modal -->
    <div class="modal" tabindex="-1" id="infoModal">
      <div class="modal-dialog">
        <div class="modal-content">
          <div class="modal-header">
            <h5 class="modal-title">회원정보 확인</h5>
            <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
          </div>
          <div class="modal-body">
            <form action="">
              <label for="infoUserId">아이디 <span class="text-danger">*</span></label>
              <input class="form-control mb-3" type="text" name="userId" id="infoUserId" readonly />
              <label for="infoUserPw">비밀번호 <span class="text-danger">*</span></label>
              <input class="form-control mb-3" type="password" name="userPw" id="infoUserPw" readonly />
              <label for="infoUserName">이름 <span class="text-danger">*</span></label>
              <input class="form-control mb-3" type="text" name="userName" id="infoUserName" readonly />
              <label for="infoUserAddress">주소 <span class="text-danger">*</span></label>
              <input class="form-control mb-3" type="text" name="userAddress" id="infoUserAddress" readonly />
              <label for="infoUserPhone">전화번호 <span class="text-danger">*</span></label>
              <input class="form-control mb-3" type="tel" name="userPhone" id="infoUserPhone" readonly />
              <div class="text-danger text-center" id="infoModalInfo"></div>
            </form>
          </div>
          <div class="modal-footer">
            <button type="button" class="btn btn-secondary" data-bs-dismiss="modal">닫기</button>
            <button type="button" class="btn btn-primary" id="updateBtn" onclick='javascript:unsetReadonly("info")'>수정하기</button>
            <button type="button" class="btn btn-primary" style="display: none" id="updateCompleteBtn" onclick="javascript:updateUserInfo()">수정완료</button>
            <button type="button" class="btn btn-danger" id="deleteUserBtn" onclick="javascript:deleteUser()">계정삭제</button>
          </div>
        </div>
      </div>
    </div>
    <!-- 회원정보 modal END -->

    <!-- 공지사항 modal -->

    <!-- 공지사항 list modal -->
    <div class="modal modal-xl" tabindex="-1" id="noticeModal">
      <div class="modal-dialog" style="height: 94%">
        <div class="modal-content" style="height: 100%">
          <div class="modal-header">
            <h5 class="modal-title">공지사항</h5>
          </div>
          <div class="modal-body" id="noticeModalBody">
            <table class="table" id="noticeModalTable">
              <thead>
                <tr>
                  <th scope="col" class="text-center" style="width: 10%">글 번호</th>
                  <th scope="col" style="width: 50%">글 제목</th>
                  <th scope="col" class="text-center" style="width: 15%">작성자</th>
                  <th scope="col" class="text-center" style="width: 15%">작성일</th>
                  <th scope="col" class="text-center" style="width: 10%">조회수</th>
                </tr>
              </thead>
              <tbody></tbody>
            </table>
            <div class="d-flex justify-content-end">
              <button class="btn btn-md btn-primary m-2" style="width: 100px" data-bs-toggle="modal" data-bs-target="#noticeInsertModal" id="noticeInsertModalBtn">글 쓰기</button>
            </div>
            <div class="text-center" id="noticeModalNav"></div>
          </div>
        </div>
      </div>
    </div>
    <!-- 공지사항 list modal END -->

    <!-- 공지사항 detail modal -->
    <div class="modal modal-xl" tabindex="-1" id="noticeDetailModal">
      <div class="modal-dialog" style="height: 90%">
        <div class="modal-content" style="height: 100%">
          <div class="modal-header">
            <h5 class="modal-title">공지사항 글 보기</h5>
          </div>
          <div class="modal-body" id="noticeDetailModalBody"></div>
        </div>
      </div>
    </div>
    <!-- 공지사항 detail modal END -->

    <!-- 공지사항 insert modal -->
    <div class="modal modal-xl" tabindex="-1" id="noticeInsertModal">
      <div class="modal-dialog" style="height: 90%">
        <div class="modal-content" style="height: 100%">
          <div class="modal-header">
            <h5 class="modal-title">공지사항 글 쓰기</h5>
          </div>
          <div class="modal-body" id="noticeInsertModalBody">
            <form id="form-register" method="POST" action="">
              <input type="hidden" name="action" value="write" />
              <div class="mb-3">
                <label for="subject" class="form-label">제목 : </label>
                <input type="text" class="form-control" id="subject" name="subject" placeholder="제목..." />
              </div>
              <div class="mb-3">
                <label for="content" class="form-label">내용 : </label>
                <textarea class="form-control" id="content" name="content" rows="7"></textarea>
              </div>
              <div class="col-auto text-center">
                <button type="button" onclick="javascript:insertNotice()" data-bs-toggle="modal" data-bs-target="#noticeModal" id="btn-register" class="btn btn-outline-primary mb-3">글작성</button>
                <button type="reset" class="btn btn-outline-danger mb-3">초기화</button>
                <button type="button" onclick="javascript:initNotice()" data-bs-toggle="modal" data-bs-target="#noticeModal" class="btn btn-outline-secondary mb-3">취소</button>
              </div>
            </form>
          </div>
        </div>
      </div>
    </div>
    <!-- 공지사항 insert modal END -->

    <!-- 공지사항 modal END -->

    <!-- 쓰레기 봉투 정보 modal -->
    <div class="modal modal-xl" tabindex="-1" id="trashModal">
      <div class="modal-dialog" style="height: 90%">
        <div class="modal-content" style="height: 100%">
          <div class="modal-header">
            <h5 class="modal-title me-3">지역 쓰레기 봉투 정보</h5>
            <button type="button" class="btn btn-outline-warning" id="bookmarkbtn">
              <svg xmlns="http://www.w3.org/2000/svg" width="16" height="16" fill="currentColor" class="bi bi-bookmark-star" viewBox="0 0 16 16">
                <path
                  d="M7.84 4.1a.178.178 0 0 1 .32 0l.634 1.285a.18.18 0 0 0 .134.098l1.42.206c.145.021.204.2.098.303L9.42 6.993a.18.18 0 0 0-.051.158l.242 1.414a.178.178 0 0 1-.258.187l-1.27-.668a.18.18 0 0 0-.165 0l-1.27.668a.178.178 0 0 1-.257-.187l.242-1.414a.18.18 0 0 0-.05-.158l-1.03-1.001a.178.178 0 0 1 .098-.303l1.42-.206a.18.18 0 0 0 .134-.098z"
                />
                <path
                  d="M2 2a2 2 0 0 1 2-2h8a2 2 0 0 1 2 2v13.5a.5.5 0 0 1-.777.416L8 13.101l-5.223 2.815A.5.5 0 0 1 2 15.5zm2-1a1 1 0 0 0-1 1v12.566l4.723-2.482a.5.5 0 0 1 .554 0L13 14.566V2a1 1 0 0 0-1-1z"
                />
              </svg>
            </button>
          </div>
          <div class="modal-body" id="trashModalBody" style="overflow: hidden scroll">
            <table class="table" id="trashModalTable">
              <thead>
                <tr>
                  <th scope="col" class="text-center" style="width: 20%">종류</th>
                  <th scope="col" class="text-center" style="width: 10%">사용처</th>
                  <th scope="col" class="text-center" style="width: 20%">용도</th>
                  <th scope="col" class="text-center" style="width: 10%">처리 방식</th>
                  <th scope="col" class="text-center" style="width: 20%">관리 부서</th>
                  <th scope="col" class="text-center" style="width: 10%">타입</th>
                  <th scope="col" class="text-center" style="width: 10%">가격</th>
                </tr>
              </thead>
              <tbody></tbody>
            </table>
          </div>
        </div>
      </div>
    </div>
    <!-- 쓰레기 봉투 정보 modal END -->

    <!-- left side -->
    <!-- buttons -->
    <div style="right: 5px; position: fixed">
      <button class="btn btn-md btn-primary m-2" style="width: 100px" data-bs-toggle="modal" data-bs-target="#loginModal" id="loginModalBtn">로그인</button>
      <button class="btn btn-md btn-danger m-2" style="width: 100px; display: none" id="logoutBtn" onclick="javascript:logout()">로그아웃</button>
      <button class="btn btn-md btn-primary m-2" style="width: 100px" data-bs-toggle="modal" data-bs-target="#registerModal" id="registerModalBtn">회원가입</button>

      <button class="btn btn-md btn-primary m-2" style="width: 100px; display: none" data-bs-toggle="modal" data-bs-target="#infoModal" id="infoModalBtn" onclick="fetchUserInfo()">내 정보</button>
    </div>
    <!-- main page -->
    <div class="bg-body p-2" style="left: 10px; width: 20vw; min-width: 300px; top: 2vh; height: 96vh; position: fixed">
      <div class="row mb-4">
        <div class="col-3 p-0 my-auto text-end">
          <div class="dropdown">
            <a class="btn btn-sm btn-secondary dropdown-toggle" href="#" id="yearDropdownTitle" role="button" data-bs-toggle="dropdown" aria-expanded="false">연도</a>

            <ul class="dropdown-menu" id="yearDropdown">
              <li><a class="dropdown-item" href="#" onclick="javascript:setYearDropdownTitle();"></a></li>
            </ul>
          </div>
        </div>
        <div class="col-9 ms-0 me-0 position-relative">
          <input class="form-control" type="text" id="searchInput" placeholder="어디가 궁금하세요?" />
          <div class="position-absolute top-0 end-0 p-1">
            <button class="btn btn-link p-0" onclick="javascript:searchList();">
              <div class="pe-3">
                <svg height="100%" width="30px" viewBox="0 0 24 24" fill="none" xmlns="http://www.w3.org/2000/svg">
                  <path
                    d="M11 6C13.7614 6 16 8.23858 16 11M16.6588 16.6549L21 21M19 11C19 15.4183 15.4183 19 11 19C6.58172 19 3 15.4183 3 11C3 6.58172 6.58172 3 11 3C15.4183 3 19 6.58172 19 11Z"
                    stroke="#000000"
                    stroke-width="2"
                    stroke-linecap="round"
                    stroke-linejoin="round"
                  />
                </svg>
              </div>
            </button>
          </div>
        </div>
      </div>
      <hr />
      <div class="row">
        <div class="col-9 ms-0 me-0">
          <input class="form-control" type="text" id="resultSerachInput" placeholder="결과 내 재검색" onInput="javascript:filterList(this.value)" />
        </div>

        <div class="dropdown col-3">
          <a class="btn btn-sm btn-secondary dropdown-toggle" href="#" id="orderDropdownTitle" role="button" data-bs-toggle="dropdown" aria-expanded="false"> 정렬 </a>

          <ul class="dropdown-menu" id="orderDropdown">
            <li><a class="dropdown-item" onclick="javascript:filterList(document.getElementById('resultSerachInput').value, orderby_name)">이름 순</a></li>
            <li><a class="dropdown-item" onclick="javascript:filterList(document.getElementById('resultSerachInput').value, orderby_distance)">거리 순</a></li>
            <li><a class="dropdown-item" onclick="javascript:filterList(document.getElementById('resultSerachInput').value, orderby_recomm)">추천 순</a></li>
          </ul>
        </div>
      </div>
      <div class="shadow m-1" style="height: 32%; overflow: hidden scroll">
        <ul class="list-group list-group-flush" id="result_box"></ul>
      </div>
      <div class="card text-center shadow m-1 mt-4" id="spec_result_box" style="height: 50%; overflow: hidden scroll"></div>
    </div>

    <!-- right side -->

    <div class="" style="bottom: 10px; right: 10px; position: fixed">
      <button class="rounded-circle btn btn-md btn-danger m-2" style="height: 60px; width: 60px" data-bs-toggle="modal" data-bs-target="#noticeModal" id="noticeModalBtn">
        <svg xmlns="http://www.w3.org/2000/svg" width="32" height="32" fill="currentColor" class="bi bi-bell" viewBox="0 0 16 16">
          <path
            d="M8 16a2 2 0 0 0 2-2H6a2 2 0 0 0 2 2M8 1.918l-.797.161A4 4 0 0 0 4 6c0 .628-.134 2.197-.459 3.742-.16.767-.376 1.566-.663 2.258h10.244c-.287-.692-.502-1.49-.663-2.258C12.134 8.197 12 6.628 12 6a4 4 0 0 0-3.203-3.92zM14.22 12c.223.447.481.801.78 1H1c.299-.199.557-.553.78-1C2.68 10.2 3 6.88 3 6c0-2.42 1.72-4.44 4.005-4.901a1 1 0 1 1 1.99 0A5 5 0 0 1 13 6c0 .88.32 4.2 1.22 6"
          />
        </svg>
      </button>
    </div>

    <input id="root" value="${root}" style="visibility: hidden" />

    <script
      src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/js/bootstrap.bundle.min.js"
      integrity="sha384-YvpcrYf0tY3lHB60NNkmXc5s9fDVZLESaAA55NDzOxhy9GkcIdslK1eN7N6jIeHz"
      crossorigin="anonymous"
    ></script>
    <script type="text/javascript" src="//dapi.kakao.com/v2/maps/sdk.js?appkey=2621b45f3e14deb49c3159dfbfcc533a&libraries=services,clusterer"></script>
    <script type="text/javascript" src="${root}/assets/js/noticeService.js"></script>
    <script type="text/javascript" src="${root}/assets/js/searchService.js"></script>
    <script type="text/javascript" src="${root}/assets/js/userService.js"></script>
    <script type="text/javascript" src="${root}/assets/js/util.js"></script>
    <script type="text/javascript" src="${root}/assets/js/map.js"></script>
    <script type="text/javascript" src="${root}/assets/js/mapv2.js"></script>
    <script type="text/javascript" src="${root}/assets/js/template.js"></script>
  </body>
</html>
