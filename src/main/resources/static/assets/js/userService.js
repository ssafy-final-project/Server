var root = document.querySelector("#root").getAttribute("value");

// User constructor
function User(id = "", pw = "", name = "", address = "", phone = "") {
  return { id, pw, name, address, phone };
}

// fetch function
async function myFetch(url, method = "GET", data) {
  const config = {
    method: method,
    headers: { "Content-Type": "application/json" },
  };
  if (method != "GET" || method != "DELETE") config.body = JSON.stringify(data);
  // let error = "";
  const response = await fetch(url, config);
  console.log(response);
  return response;
  // const json = await fetch(url, config)
  //   .then((response) => response.json())
  //   .then((data) => data)
  //   .catch((e) => (error = e));
  // console.log(json);
  // return { json };
}

// log in
async function login() {
  var userId = document.getElementById("loginUserId").value;
  var userPw = document.getElementById("loginUserPw").value;
  var infoE = document.getElementById("loginModalInfo");

  // 유효성 검사
  if (!userId || !userPw) {
    infoE.innerText = "모든 칸을 채워주세요.";
    return;
  }

  const user = new User(userId, userPw);
  const url = `${root}/member/login`;
  const response = await myFetch(url, "POST", user);

  if (response.ok) {
    const jsonData = await response.json();
    console.log(jsonData);
    setUserInfo(jsonData);
    closeModal("login");
  }

  document.getElementById("loginModalBtn").style.display = "none";
  document.getElementById("registerModalBtn").style.display = "none";
  document.getElementById("infoModalBtn").style.display = "block";
  document.getElementById("logoutBtn").style.display = "block";
}

async function logout() {
  const url = `${root}/member/logout`;
  await myFetch(url, "GET");
  document.getElementById("loginModalBtn").style.display = "block";
  document.getElementById("registerModalBtn").style.display = "block";
  document.getElementById("infoModalBtn").style.display = "none";
  document.getElementById("logoutBtn").style.display = "none";

  const user = new User();
  setUserInfo(user);
}

// 회원가입
async function registerUser() {
  const userId = document.getElementById("registerUserId").value;
  const userPw = document.getElementById("registerUserPw").value;
  const userName = document.getElementById("registerUserName").value;
  const userAddress = document.getElementById("registerUserAddress").value;
  const userPhone = document.getElementById("registerUserPhone").value;

  const user = new User(userId, userPw, userName, userAddress, userPhone);
  var infoE = document.getElementById("registerModalInfo");

  // 값 유효성 검사
  if (!userId || !userPw || !userName || !userAddress || !userPhone) {
    infoE.innerText = "모든 칸을 채워주세요.";
    return;
  }

  // id 유효성 검증
  const url = `${root}/member`;
  const response = await myFetch(url, "POST", user);
  if (response.ok) {
    alert("회원가입이 완료되었습니다.");
  }
  closeModal("register");
  infoE.innerText = "";
}

function setUserInfo(user) {
  console.log("setUserinfo : " + user);
  console.log(user.id);
  var userId = document.getElementById("infoUserId");
  var userPw = document.getElementById("infoUserPw");
  var userName = document.getElementById("infoUserName");
  var userAddress = document.getElementById("infoUserAddress");
  var userPhone = document.getElementById("infoUserPhone");

  userId.value = user.id;
  userPw.value = "";
  userName.value = user.name;
  userAddress.value = user.address;
  userPhone.value = user.phone;
}

function unsetReadonly(modalType) {
  if (modalType === "info") {
    document.getElementById("infoUserPw").removeAttribute("readonly");
    document.getElementById("infoUserName").removeAttribute("readonly");
    document.getElementById("infoUserAddress").removeAttribute("readonly");
    document.getElementById("infoUserPhone").removeAttribute("readonly");

    document.getElementById("updateCompleteBtn").style.display = "block";
  }
}

function closeModal(prefix) {
  let modal = bootstrap.Modal.getInstance(document.getElementById(prefix + "Modal"));
  modal.hide();
}

function checkIdAvailability() {
  const checkid = document.getElementById("registerUserId").value;
  const messageBox = document.getElementById("idCheckMessage");

  // 아이디 길이 체크
  // console.log("Check ID:", checkid); // ID 확인
  if (checkid.length < 4 || checkid.length > 16) {
    // messageBox.style.display = ";";
    messageBox.textContent = "아이디는 4자에서 16자 사이여야 합니다.";
    return;
  } else {
    // console.log("Sending request to server...");
    // 서버에 아이디 중복 체크 요청
    fetch(`${root}/member/idchk/${checkid}`)
      .then((response) => {
        // console.log("Response received:", response);
        if (!response.ok) {
          throw new Error("네트워크 응답이 성공적이지 않습니다.");
        }
        return response.json();
      })
      .then((data) => {
        // console.log("Server response:", data);
        if (data == "1") {
          messageBox.textContent = "이미 사용 중인 아이디입니다.";
          messageBox.className = "text-danger";
        } else {
          messageBox.textContent = "사용 가능한 아이디입니다.";
          messageBox.className = "text-primary";
        }
      });
  }
}
async function deleteUser() {
  const userId = document.getElementById("infoUserId").value;
  const user = new User(userId);
  // const userInfo = { id: userId };
  const url = `${root}/member/${userId}`;
  const response = await myFetch(url, "DELETE", user);
  if (response.ok) {
    alert("회원탈퇴가 완료되었습니다.");
  }

  const infoModal = bootstrap.Modal.getInstance(document.getElementById("infoModal"));
  infoModal.hide();
  await logout();
}

async function updateUserInfo() {
  const userId = document.getElementById("infoUserId").value; // 아이디 추가
  const userPw = document.getElementById("infoUserPw").value;
  const userName = document.getElementById("infoUserName").value;
  const userAddress = document.getElementById("infoUserAddress").value;
  const userPhone = document.getElementById("infoUserPhone").value;

  const user = new User(userId, userPw, userName, userAddress, userPhone);
  const url = `${root}/member`;
  const response = await myFetch(url, "PUT", user);
  if (response.ok) {
    const jsonData = await response.json();
    console.log("updateUser" + jsonData);
    alert("회원정보가 수정되었습니다.");
  }

  setReadonly(true);

  // error
  //     document.getElementById("infoModalInfo").innerText = error.message;
}

function setReadonly(isReadonly) {
  const fields = ["infoUserPw", "infoUserName", "infoUserAddress", "infoUserPhone"];
  fields.forEach((field) => {
    const inputField = document.getElementById(field);
    if (isReadonly) {
      inputField.setAttribute("readonly", true);
    } else {
      inputField.removeAttribute("readonly");
    }
  });
  document.getElementById("updateCompleteBtn").style.display = isReadonly ? "none" : "block";
}

async function fetchUserInfo() {
  const userId = document.getElementById("infoUserId").value;
  console.log(userId);
  const url = `${root}/member/${userId}`;
  const response = await myFetch(url);

  if (response.ok) {
    const data = await response.json();
    console.log("Fetched user data:", data);
    document.getElementById("infoUserId").value = data.id;
    document.getElementById("infoUserPw").value = data.password;
    document.getElementById("infoUserName").value = data.name;
    document.getElementById("infoUserAddress").value = data.address;
    document.getElementById("infoUserPhone").value = data.phone;
  }

  // if error
  // document.getElementById("infoModalInfo").innerText = error.message;
}
