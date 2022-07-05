let chartResult;

window.onload = function () {
    sideAni(3); //사이드바 애니메이션 side-bar.js
    $(".menu-box").eq(0).addClass("menu-box__select");

};

$(document).ready(function(){
    chartData();

    searchUser(1);

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

    makechart("chart__recent", {
        label: "최근 가입한 회원",
        labels: getDateInfo(),
        data: chartResult,
    });
    // makechart("chart__complete", {
    //     label: "달성률 예시",
    //     labels: ["06.07", "06.08", "06.09", "06.10", "06.11", "06.12", "06.13"],
    //     data: [100, 77, 65, 85, 70, 55, 80],
    // });
})

$(".search-button").on("click", function(e){
    e.preventDefault();
    pageNum = 1;
    searchUser(pageNum);
})
let desc = false;
//============== 정렬 버튼 =======================
$(".order_btn").on("click",function () {
    let name = $(this).attr("name");
    $(".order").val(name);
    $(".desc").val(desc);
    desc = !desc;
    searchUser(pageNum)
})

//================================ ajax =========================================

//검색하기
function searchUser(page) {
    $(".list-table tr:not(.table-head)").html("");
    $("input[type=checkbox]").prop("checked", false);
    adminService.searchUser({
        page : page,
        startDate: $("input[name='startDate']").val(),
        endDate: $("input[name='endDate']").val(),
        type: $("select[name='type']").val(),
        keyword: $("input[name='keyword']").val(),
        kakao: $("input[name='kakao']:checked").val(),
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
        result.forEach(function (user, i) {
            str += " <tr>" +
                "<td class=\"list-checkbox\">" +
                "<input type=\"checkbox\" value=\"" + user.userNumber + "\" />" +
                "</td>\n" +
                "<td class=\"user-number\">" + user.userNumber + "</td>" +
                "<td class=\"user-name\">" + user.name + "</td>" +
                "<td class=\"user-email\">" + user.email + "</td>" +
                "<td class=\"user-nickname\">" + user.nickname + "</td>" +
                "<td class=\"user-kakao\">" + (user.kakao ? user.kakao : "")  + "</td>" +
                "<td class=\"user-status\">" + user.registerDate + "</td>" +
                "</tr>"
        })
        $(".list-table > tbody").append(str);
        pageBlock(result[0].total);//admin-common.js에 정의되어 있음
    });
}

$(".paging-block").on("click", "a.changePage", function (e) {
    e.preventDefault();
    pageNum = $(this).attr("href");
    searchUser(pageNum);
});



//삭제하기
let deleteUser = function(){
    let $checked = $(".list-checkbox > input[type='checkbox']:checked");
    let list = [];
    $checked.each((i, box) => {
        list.push(box.value);
    });

    adminService.deleteUser(list.join(","), function(){
        searchUser(pageNum);
    })

}
// 삭제 버튼 이벤트 ==========================================
//checkAlert() 는 admin-common.js 에 정의됨
//매개변수에 실행시킬 함수 콜백함수로 넘겨서 사용하기 -> checkAlert("msg", function(){......})
$(".delete-btn").on("click", function () {
    checkAlert("정말로 삭제하시겠습니까?", deleteUser);
});


//차트===============================

function chartData(){
    chartService.userChartData(function(result){
        chartResult = result;
    })
}




