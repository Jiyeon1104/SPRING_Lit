// $(document).ready(function () {
//
// });

window.onload = function () {
  sideAni(0); //사이드바 애니메이션 side-bar.js
  $(".menu-box").eq(3).addClass("menu-box__select");
};

$(document).ready(function(){
  searchProject(1);
})

$(".search-button").on("click", function(e){
  e.preventDefault();
  pageNum = 1;
  searchProject(pageNum);
})

// ========================================================

let desc = false;
//============== 정렬 버튼 =======================
$(".order_btn").on("click",function () {
  let name = $(this).attr("name");
  $(".order").val(name);
  $(".desc").val(desc);
  desc = !desc;
  searchProject(pageNum);
})


//================================ ajax =========================================

//삭제하기
let deleteProject = function (){
  let $checked = $(".list-checkbox > input[type='checkbox']:checked");
  let list = [];
  $checked.each((i, box) => {
    list.push(box.value);
  });

  adminService.deleteProject(list.join(","), function(){
    searchProject(pageNum);
  })
}

//검색하기
function searchProject(page) {
  $(".list-table tr:not(.table-head)").html("");
  $("input[type=checkbox]").prop("checked", false);


  adminService.searchProject({
    page : page,
    startDate: $("input[name='startDate']").val(),
    endDate: $("input[name='endDate']").val(),
    type: $("select[name='type']").val(),
    keyword: $("input[name='keyword']").val(),
    category: $("select[name='category']").val(),
    status : $("input[name='status']:checked").val(),
    order : $(".order").val(),
    desc : $(".desc").val()
  }, function (result) {
    //검색 결과 건수
    if(result == null || result.length == 0){
      $(".searchResult").text(0);
      return;
    }
    $(".searchResult").text(result[0].total);

    //결과 리스트 처리
    let str = "";
    result.forEach(function (project, i) {
      str +=
          "<tr>" +
          "<td class=\"list-checkbox\">" +
          "<input type=\"checkbox\" value=\"" + project.projectNumber + "\" />" +
          "</td>" +
          "<td class=\"project-number\">" + project.projectNumber + "</td>" +
          "<td class=\"project-category\">" + renameCategory(project.category) + "</td>" +
          "<td class=\"project-title\">" + project.title + "</td>" +
          "<td class=\"project-view\">" +
          "<div>" +
          "<a class=\"a-btn not-selected\" id="+ project.projectNumber +" href='javascript:void(0)'>보기</a>" +
          "</div>" +
          "</td>" +
          "<td class=\"user-email\">" + project.email + "</td>" +
          "<td class=\"project-apply-cnt\">" + project.applyCount + "</td>" +
          "<td class=\"project-start-date\">" +
          project.startDate +
          "</td>" +
          "<td class=\"project-end-date\">" + project.endDate + "</td>" +
          "<td class=\"project-status\">" + renameStatus(project.status) + "</td>" +
          "</tr>"
    })
    $(".list-table > tbody").append(str);
    pageBlock(result[0].total); //admin-common.js에 정의되어 있음
  });
}

$(".list-tbody").on("click",".a-btn",function () {
  let getProjectNum =$(this).attr("id")
  window.open("/lit/info?projectNumber=" + getProjectNum);
})

$(".paging-block").on("click", "a.changePage", function (e) {
  e.preventDefault();
  pageNum = $(this).attr("href");
  searchProject(pageNum);
});
// 삭제 버튼 이벤트 ==========================================
//checkAlert() 는 admin-common.js 에 정의됨
//매개변수에 실행시킬 함수 콜백함수로 넘겨서 사용하기 -> checkAlert("msg", function(){......})
$(".delete-btn").on("click", function () {
  checkAlert("정말로 삭제하시겠습니까?", deleteProject);
});