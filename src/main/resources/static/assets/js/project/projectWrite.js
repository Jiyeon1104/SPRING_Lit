// 파일 담을 변수
let fileType = /(.*?)\.(jpg|jpeg|png)$/;
let uploadFiles = [];
// ======================================================================
// 이미지 드롭 js
$(".imgData")
  .on("dragover", function (e) {
    // 이미지 오버
    e.stopPropagation();
    e.preventDefault();
    let $logo = $(".uploadLoge");
    $logo.attr("color", "#ff4543");
    $(e.target).css({
      "background-color": "#f2f2f2",
    });
  })
  .on("dragleave", function (e) {
    // 이미지 아웃
    e.stopPropagation();
    e.preventDefault();
    let $logo = $(".uploadLoge");
    $logo.attr("color", "#262626");
    $(e.target).css({
      "background-color": "#ffffff",
    });
  })
  .on("drop", function (e) {
    // 이미지 드랍
    e.stopPropagation();
    e.preventDefault();
    let $logo = $(".uploadLoge");
    $logo.attr("color", "#ff4543");
    $(e.target).css({
      "background-color": "#f2f2f2",
    });

    // 파일 객체화
    let file = e.originalEvent.dataTransfer.files[0];
    // 파일 유효성
    if (!file.name.match(fileType)) {
      alert("파일업로드 실패");
      return;
    }
    // 이미지 미리보기
    imgEvent(file);

    // 이미지 저장할 데이터 담기....
    uploadFiles.push(file);
  });

let arFile = Array.from($("input[type='file']")[0].files);

// 이미지 미리보기 화면에 출력
function imgEvent(files) {
  $(".imgData").hide();
  let str ="";

  console.log(files);

  let imageUrl = "";
  $(files).each(function(i, file) {
    str += "<div data-name='" + file.name + "' data-uuid='" + file.uuid + "' data-uploadpath='" + file.uploadPath + "' data-image='" + file.image + "'></div>";
    imageUrl += "/lit/display?fileName=" + file.uploadPath + "/" + file.uuid + "_"  + file.name;
  });

  $(".imgView").append(str);
  $(".imgView").show().css("background-image", "url(" + imageUrl +  ")");
  $(".topmenu > div").eq(0).show();
  $(".topmenu > div").eq(2).show();
}

// 파일 input 업로드
$("#btnImage").on("change", function (e) {
    e.stopPropagation();
    e.preventDefault();
    // 파일 객체화
    let file = e.target.files[0];

    let formData =   new FormData();
    let inputFile = $("input[name='uploadFiles']");
    let files = inputFile[0].files;
    for(let i=0; i<files.length; i++){
      if(!checkExtension(files[i].name, files[i].size)){ return; }
      formData.append("uploadFiles", files[i]);
    }
    Array.from($(this)[0].files).forEach(file => arFile.push(file));
    const dataTransfer = new DataTransfer();
    arFile.forEach(file => dataTransfer.items.add(file));
    $(this)[0].files = dataTransfer.files;

    $.ajax({
      url: "/lit/upload",
      type: "post",
      data: formData,
      contentType: false,
      processData: false,
      success: function(files){
        imgEvent(files);
      }
    });

  // 이미지 미리보기
  // 이미지 저장할 데이터 담기....
  uploadFiles.push(file);
});

function checkExtension(file) {
  // 파일 유효성
  console.log(file);
  if (!file.match(fileType)) {
    alert("파일업로드 실패");
    return false;
  }
  return true;
}

//=========================================================================

// function submitEvent() {
//   let form = $(projectForm);
//   console.log(form.find('input[name="titel"]').val());
// }

// 업로드 페이지 추가 이벤트
function formEvent() {
  $(".projectData").show();
  $(".topmenu > div").eq(2).hide();
  $(".topmenu > div").eq(3).show();
}

// 아코디언 메뉴 이벤트
$("label.togle").click(function () {
  if ($(this).next().is(":visible")) {
    $(this).next().css({ display: "none" });
  } else {
    $(this).next().css({ display: "block" });
  }
});
// 업로드 닫기 이벤트
function uploadClose() {
  if (
    confirm(
      "게시물을 삭제하시겠어요?\n지금 나가면 수정 내용이 저장되지 않습니다. "
    ) == true
  ) {
    //확인
    location.reload(); // 화면 초기화
  } else {
    //취소
    return false;
  }
}

//인증글 작성 textarea 글 길이 검사
$(".textareaBox > textarea").on("input", function () {
  let cnt = $(this).val().length;
  if (cnt > 400) {
    $(this).val($(this).val().substring(0, 400));
    $(this).next().find("span").text(400).parent("p").css({ color: "red" });
  } else {
    $(this).next().find("span").text(cnt);
  }
});

// submit 유효성 감사
function submitEvent(e) {
  let projectTitle = $("input[name='title']");
  let projectCategory = $("input[name='category']:checked");
  let projectContent = $("textarea[name='content']");
  let projectAuth = $("textarea[name='authentication']");
  let startDate = $("input[name='startDate']:checked");
  let projectEnd = $("input[name='endDate']:checked");

  // 제목 입력
  if (projectTitle.val() == "") {
    alert("제목을 입력하세요");
    projectTitle.focus();
    return false;
  }
  // 카테고리
  if (projectCategory.length < 1) {
    alert("카테고리를 선택하세요");
    return false;
  }
  // 프로젝트 내용
  if (projectContent.val() == "") {
    alert("첼린지소개를 입력하세요");
    projectContent.focus();
    return false;
  }
  // 프로젝트 인증입력
  if (projectAuth.val() == "") {
    alert("인증방법을 입력하세요");
    return false;
  }
  // 시작일 선택
  if (startDate.length < 1) {
    alert("시작일을 선택하세요");
    projectContent.focus();
    return false;
  }
  // 기간을 선택
  if (projectEnd.length < 1) {
    alert("기간을 선택하세요");
    return false;
  }

  // 유효성 이후 submit
  let str = "";
  var form = $('#projectForm');
  $.each($(".imgView div"), function (i, div) {
    str += "<input type='hidden' name='projectFile.name' value='" + $(div).data("name") + "'>"
    str += "<input type='hidden' name='projectFile.uuid' value='" + $(div).data("uuid") + "'>"
    str += "<input type='hidden' name='projectFile.uploadPath' value='" + $(div).data("uploadpath") + "'>"
    str += "<input type='hidden' name='projectFile.image' value='" + $(div).data("image") + "'>"
  });
  form.append(str).submit();
}


// 스타트 / 마지막 날짜 계산기
$().ready(function dateInputSet() {
  let date = new Date();
  const startTags = $("input[name='startDate']"); // start 데이터를 넣어줄 곳
  const endTags = $("input[name='endDate']"); // end 데이터 넣어줄 곳
  const dateVals = [6, 0, 1, 2, 3, 4, 5]; //요일별 더할 날짜

  // Start 월요일 날짜 계산
  let standDate = date.getDate() - dateVals[date.getDay()];
  date.setDate(standDate);

  let calcDate; // 넣어줄 데이터
  // 선택 태그 val 뿌리기
  for (let i = 0; i < startTags.length; i++) {
    date.setDate(date.getDate() + 7);
    // console.log("======================");
    // console.log("일 : " + date.getDate());
    // console.log("월 : " + (date.getMonth() + 1));
    calcDate = date.getMonth() + 1 + "-" + date.getDate();
    startTags.eq(i).next().text(calcDate);
    calcDate = date.getFullYear() + "-" + calcDate;
    startTags.eq(i).attr("value", calcDate);
  }

  // 기간 선택 js
  startTags.on("change", function () {
    let calcDate;
    let dateArr = $(this).val().split("-");
    let endDate = parseInt(dateArr[2]);
    date.setMonth(parseInt(dateArr[1]) - 1);
    date.setDate(endDate);

    date.setDate(date.getDate());
    console.log("년도" + date.getFullYear());
    console.log("월" + (date.getMonth() + 1));
    console.log("일" + date.getDate());

    for (let i = 0; i < endTags.length; i++) {
      date.setDate(date.getDate() + 7);
      calcDate =
        date.getFullYear() + "-" + (date.getMonth() + 1) + "-" + date.getDate();
      endTags.eq(i).attr("value", calcDate);
    }
  });

  $("input[name='endDate']").on("click", function () {
    const dateInputCk = $("input[name='startDate']:checked"); // 기간 값
    if (dateInputCk.length < 1) {
      alert("시작일을 먼저 선택하세요");
      return false;
    }
  });
});

//==========================================
//    transform: rotate(180deg);
$("label.togle").on("click", function(){
  $(this).toggleClass("rotate");
})

$(function userFile() {
  let userNum = $('input[name=userNumber]').val();
  $.ajax({
    url: "/user/getUser/" + userNum,
    type: "get",
    contentType: "application/json; charset=utf-8;",
    success: function (userVO) {
      setUserCon(userVO);
    }
  });
})

function setUserCon(userVO) {
  if(userVO.userFileList != null) {
    $("#userFile").attr("src", "/lit/display?fileName=" + userVO.userFileList.uploadPath + "/" + userVO.userFileList.uuid + "_" + userVO.userFileList.name);
  } else{
    $("#userFile").attr("src", "/images/main/profile_ex.png");
  }

  $("#nickName").text(userVO.nickname);
}
