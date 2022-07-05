//8자리 이상, 대문자, 소문자, 숫자, 특수문자 모두 포함되어 있는 지 검사
const loginBtn = $('#login_btn');
const email = $('#email');
const passwordValue = $('#pwVal');
const userName = $('#userName')
const nickName = $('#nickName');
const onPwBtn = $('.onBtn');

// 이메일 유효성 검사
function checkEmail() {
   var filter = /^([\w-\.]+)@((\[[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\.)|(([\w-]+\.)+))([a-zA-Z]{2,4}|[0-9]{1,3})(\]?)$/;
   if(filter.test(email.val())){
      $('#emailCk').css("display", "grid");
      $('#emailCk').attr('class', 'iconCheck');
      return true;
   } else if(email.val().length <= 0){
      $('#emailCk').attr('class', 'iconCheck');
      $('#emailCk').css("display", "none");
      return false;
   } else{
      $('#emailCk').attr('class', 'iconX');
      $('#emailCk').css("display", "grid");
      return false;
   }
}

function checkUserName() {
   if(userName.val().length <= 0){
      $('#uNameCk').css("display", "none");
      return false;
   }else{
      $('#uNameCk').css("display", "grid");
      return true;
   }
}

// 닉네임 중복 검사
function checkNickName() {
   if(nickName.val().length <= 0){
      $('#nNameCk').css("display", "none");
      return false;
   }else if(nickName.val() == '비교할 값') { // 유저 닉네임 중복 확인
      $('#nNameCk').attr('class', 'iconX');
      $('#nNameCk').css("display", "grid");
      return false;
   } else{
      $('#nNameCk').attr('class', 'iconCheck');
      $('#nNameCk').css("display", "grid");
      return true;
   }
}

// 비밀번호 보이기 버튼
function onPwBtnFun() {
   if (passwordValue.val().length > 0) {
      onPwBtn.css('display', 'inline-block');
      $('#pwCk').attr('class', 'iconX');
      $('#pwCk').css("display", "grid");
      if(onPwBtn.html() == "숨기기"){
         onPwBtn.html("숨기기");
      } else{
         onPwBtn.html("비밀번호 표시");
      }
   } else {
      onPwBtn.css('display', 'none');
      $('#pwCk').css("display", "none");
      return false;
   }
   checkPw()
   return checkPw();
}

// 비밀번호 보이기
onPwBtn.on('click', function () {
   if (passwordValue.attr('type') == 'text') {
      passwordValue.attr('type', 'password');
      onPwBtn.html("비밀번호 표시");
   } else {
      passwordValue.attr('type', 'text');
      onPwBtn.html("숨기기");
   }
});

function checkPw() {
   // 비밀번호를 보이기로 할시 한국어 쓰기가 가능하기 떄문에 유효성 검사로 막아줌
   let hangleCheck = /[ㄱ-ㅎ | ㅏ-ㅣ |가-힣]/
   if(!hangleCheck.test(passwordValue.val()) && passwordValue.val().length >= 6){
      $('#pwCk').css("display", "grid");
      $('#pwCk').attr('class', 'iconCheck');
      return true;
   }else{
      return false;
   }
}

// 버튼 이벤트 효과적용 및 색상 변경
function loginBtnF() {
   var ckE = checkEmail();
   var ckU = checkUserName();
   var ckN = checkNickName();
   var ckP = onPwBtnFun();
   if (ckE && ckU && ckN && ckP) {
      loginBtn.attr("class", "buttonforjoin formEvent");
      loginBtn.attr("disabled", false)
   } else {
      loginBtn.attr("class", "buttonforjoin jonbuttoncolor");
      loginBtn.attr("disabled", true)
   }
}

email.keyup(loginBtnF)
userName.keyup(loginBtnF)
nickName.keyup(loginBtnF)
passwordValue.keyup(loginBtnF)
