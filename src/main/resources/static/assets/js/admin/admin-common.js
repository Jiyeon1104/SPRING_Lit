function checkAlert(msg, callback) {
    //테스트용
    //실제 사용시 매개변수로 콜백받기 -> function checkAlert(msg, callback)
    if (confirm(msg)) {
        // 확인을 눌렀을 때 실행할 코드
        //매개변수로 받은 콜백함수를 실행시키면 됨 callback()
        callback();
    } else {
        console.log("실패");
    }
}

//====== 상태값 수정 ======
function renameStatus(status) {
    let result;
    switch (status) {
        case 0:
            result = "대기중";
            break;
        case 1:
            result = "진행중";
            break;
        case 2:
            result = "완료";
            break;
    }
    return result;
}

//====== 카테고리 값 수정 ========
function renameCategory(category) {
    let result;
    switch (category) {
        case "life":
            result = "생활";
            break;
        case "exercise":
            result = "운동";
            break;
        case "hobby":
            result = "취미";
            break;
        case "art":
            result = "예술";
            break;
        case "heart":
            result = "정서";
            break;
        case "eating":
            result = "식습관";
            break;
        case "study":
            result = "공부";
            break;
    }
    return result;
}

//차트에서 사용할 최근 1주일의 월.일 리스트
function getDateInfo() {
    let today = new Date();
    let dayList = [];
    let date, month;

    for (let i = 0; i < 7; i++) {
        date = today.getDate();
        month = today.getMonth() + 1;
        dayList.push(month + "." + date);

        today.setDate(today.getDate() - 1);
    }
    return dayList.reverse();
}


//date-picker 설정 ====================================
window.addEventListener("load", function () {
    if ($.datepicker === undefined) {
        return;
    }
    $.datepicker.setDefaults({
        dateFormat: "yy-mm-dd",
        prevText: "이전 달",
        nextText: "다음 달",
        monthNames: [
            "1월",
            "2월",
            "3월",
            "4월",
            "5월",
            "6월",
            "7월",
            "8월",
            "9월",
            "10월",
            "11월",
            "12월",
        ],
        monthNamesShort: [
            "1월",
            "2월",
            "3월",
            "4월",
            "5월",
            "6월",
            "7월",
            "8월",
            "9월",
            "10월",
            "11월",
            "12월",
        ],
        dayNames: ["일", "월", "화", "수", "목", "금", "토"],
        dayNamesShort: ["일", "월", "화", "수", "목", "금", "토"],
        dayNamesMin: ["일", "월", "화", "수", "목", "금", "토"],
        showMonthAfterYear: true,
        yearSuffix: "년",
    });
});

// ========================================================
//기간 버튼
$(".a-btn").on("click", function (e) {
    e.preventDefault();
    $(".period-button-wrap > .a-btn").removeClass("a-btn__selected");
    $(this).addClass("a-btn__selected");

    //기간 버튼 클릭시 input에 자동 삽입
    let $startInput = $("input[name='startDate']");
    let $endInput = $("input[name='endDate']");
    let val = $(this).attr("href");

    //전체 버튼 선택시 공백으로 바꾸기
    if (val == "") {
        $startInput.val("");
        $endInput.val("");
        return;
    }

    let todayObj = new Date();
    let dateResult = new Date(
        todayObj.getTime() - 1000 * 60 * 60 * 24 * parseInt(val)
    );
    let year = dateResult.getFullYear();
    let month = dateResult.getMonth() + 1;
    let date = dateResult.getDate();

    let resultDateAr = [
        year,
        (month < 10 ? "0" : "") + month,
        (date < 10 ? "0" : "") + date,
    ];

    $endInput.val(
        [
            todayObj.getFullYear(),
            (todayObj.getMonth() + 1 < 10 ? "0" : "") + (todayObj.getMonth() + 1),
            (todayObj.getDate() < 10 ? "0" : "") + todayObj.getDate(),
        ].join("-")
    );
    $startInput.val(resultDateAr.join("-"));
});

//date picker

$(function () {
    let $startDate = $("input[name=startDate]");
    let $endDate = $("input[name=endDate]");

    $(".datepicker").datepicker({
        showAnim: "slide",
        dateFormat: 'yy-mm-dd'
    })

    $endDate.datepicker('option', 'minDate', $startDate.val());
    $startDate.datepicker("option", "onClose", function (selectedDate) {
        $endDate.datepicker("option", "minDate", selectedDate);
    });

    $endDate.datepicker();
    $endDate.datepicker("option", "minDate", $startDate.val());
    $endDate.datepicker("option", "onClose", function (selectedDate) {
        $startDate.datepicker("option", "maxDate", selectedDate);
    });
});


$(".calendar-icon-wrap").on("click", function () {
    let $input = $(this).prev("div").find("input");
    $input.trigger("focus");
});

//chart 설정======================================
//요소ID와 객체하나 받으면 차트 꽂히도록 함수 정의
function makechart(domId, myData) {
    let chartArea = document.getElementById(domId).getContext("2d");
    // 차트생성
    let mychart = new Chart(chartArea, {
        type: "line", //string

        // data : Object
        data: {
            // x축 ar[]
            labels: myData.labels,

            //datasets : ar[{dataset1, ds2, ds3...}]
            datasets: [
                {
                    label: myData.label, //string
                    data: myData.data,
                    lineTension: 0.3,
                    borderColor: "rgba(255, 69, 67, 1)", //string
                    backgroundColor: "rgba(255, 69, 67, 0.2)", //string
                    borderWidth: 1,
                    fill: true,
                },
            ],
        },
        options: {
            responsive: false,
            scales: {
                y: {
                    beginAtZero: true,
                },
            },
        },
    });
}


// 체크박스 이벤트 ==========================================
$(".check-all").change(function () {
    let $allBox = $(this).is(":checked");
    let $otherBox = $(".list-checkbox > input[type='checkbox']");
    if ($allBox) {
        $otherBox.prop("checked", true);
    } else {
        $otherBox.prop("checked", false);
    }
});

$(".list-tbody").on("change", "input[type='checkbox']", function (e) {
    e.preventDefault();
    if (!$(this).is(":checked")) {
        $(".check-all").prop("checked", false);
    }
});


//페이징 ==========================================
let pageNum = 1;

function pageBlock(total) {
    console.log(pageNum)
    let endPage = Math.ceil(pageNum / 10.0) * 10;
    let startPage = endPage - 9;
    let realEnd = Math.ceil(total / 10.0);
    const $paging = $(".paging-block");

    if (endPage > realEnd) {
        endPage = realEnd;
    }

    let prev = startPage > 1;
    let next = endPage * 10 < total;
    let str = "";

    if (prev) {
        str += "<a class='changePage' href='" + (startPage - 1) + "'><li>&lt;</li></a>"
    }
    for (let i = startPage; i <= endPage; i++) {
        str += pageNum == i ? "<li>" + i + "</li>" : "<a class='changePage' href='" + i + "'><li>" + i + "</li></a>";
    }
    if (next) {
        str += "<a class='changePage' href='" + (endPage + 1) + "'><li>&gt;</li></a>"
    }
    $paging.html(str);
}


