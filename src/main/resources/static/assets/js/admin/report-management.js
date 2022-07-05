let chartResult;

$(document).ready(function(){
    chartData();
    searchReport(1);
    // ====== 차트 ======
    //makechart 메소드는 admin-common.js에 정의되어 있음

    //makechart("domId", myData)
    //domId는 그래프를 꽂을 요소의 id (설정완료)
    //myData는 객체이며 필요 프로퍼티는 label, labels, data (아래는 예시, 설정필요)

    //DB에서 가져온 값을 넘겨줘야함 각 프로퍼티별 타입은 아래와 같음
    //label : String, labels : Array, data : Array
    //label은 그래프 상단의 라벨
    //labels는 x축 데이터
    //data는 그래프에 그려질 값
    makechart("chart__report", {
        label: "최근 등록된 신고글",
        labels: getDateInfo(),
        data: chartResult,
    });
})
window.onload = function () {
    sideAni(); //사이드바 애니메이션 side-bar.js
    $(".menu-box").eq(5).addClass("menu-box__select");
};

$(".search-button").on("click", function(e){
    e.preventDefault();
    pageNum = 1;
    searchReport(pageNum);
})
//============== 정렬 버튼 =======================
let desc = false;
$(".order_btn").on("click",function () {
    let name = $(this).attr("name");
    $(".order").val(name);
    $(".desc").val(desc);
    console.log($(".order").val());
    console.log($(".desc").val());
    desc = !desc;
    searchReport(pageNum);
})

//================================ ajax =========================================

function searchReport(page) {
    $(".list-table tr:not(.table-head)").html("");
    $("input[type=checkbox]").prop("checked", false);


    adminService.searchReport({
        page : page,
        startDate: $("input[name='startDate']").val(),
        endDate: $("input[name='endDate']").val(),
        type: $("select[name='type']").val(),
        keyword: $("input[name='keyword']").val(),
        category: $("select[name='category']").val(),
        order : $(".order").val(),
        desc : $(".desc").val()
    }, function (result) {

        if(result == null || result.length == 0){
            $(".searchResult").text(0);
            return;
        }

        $(".searchResult").text(result[0].total);
        let str = "";
        result.forEach((report, i) => {
            str +=
                "<tr>" +
                "<td class=\"list-checkbox\">" +
                "<input type=\"checkbox\" value=\"" + report.reportNumber + "\" />" +
                "</td>" +
                "<td class=\"report-number\">" + report.reportNumber + "</td>" +
                "<td class=\"report-user-email\">" + report.reportEmail + "</td>" +
                "<td class='report-reason'>" + report.reason + "</td>" +
                "<td class=\"report-register-date\">" +
                report.reportRegisterDate +
                "</td>" +
                "<td class=\"review-number\">" + report.reviewNumber + "</td>" +
                "<td id='" + report.reviewUserNumber + "' class=\"review-user-email\">" + report.reviewEmail + "</td>" +
                "<td class=\"review-register-date\">" +
                report.reviewRegisterDate +
                "</td>" +
                "<td class=\"category\">" +
                renameCategory(report.category) +
                "</td>" +
                "<td class=\"project-view\">" +
                "<div>" +
                "<a class=\"a-btn not-selected\" href='javascript:void(0)'>보기</a>" +
                "</div>" +
                "</td>" +
                "</tr>"
        })
        $(".list-table > tbody").append(str);
        pageBlock(result[0].total);//admin-common.js에 정의되어 있음
    });
}
$(".paging-block").on("click", "a.changePage", function (e) {
    e.preventDefault();
    pageNum = $(this).attr("href");
    searchReport(pageNum);
});
//신고된 글 삭제
let deleteReport = function(){
    let $checked = $(".list-checkbox > input[type='checkbox']:checked");
    let $reviewNum = $checked.parent('.list-checkbox').siblings(".review-number");
    let list = [];

    $reviewNum.each((i, num) => {
        list.push(num.innerText);
    });

    adminService.deleteReport(list.join(","), function(){
        searchReport(pageNum);
    })
}

//신고 취소
let cancelReport = function(){
    let $checked = $(".list-checkbox > input[type='checkbox']:checked");
    let list = [];

    $checked.each((i, box) => {
        list.push(box.value);
    });

    adminService.cancelReport(list.join(","), function(){
        searchReport(pageNum);
    })
}




// 버튼 이벤트 ==========================================
//checkAlert() 는 admin-common.js 에 정의됨
//매개변수에 실행시킬 함수 콜백함수로 넘겨서 사용하기 -> checkAlert("msg", function(){......})
$(".cancel-btn").on("click", function () {
    checkAlert("해당 신고를 취소하시겠습니까?", cancelReport);
});

$(".delete-btn").on("click", function () {
    checkAlert("정말로 삭제하시겠습니까?", deleteReport);
});
// ========================================================

//차트=====

function chartData(){
    chartService.reportChartData(function(result){
        chartResult = result;
    })
}