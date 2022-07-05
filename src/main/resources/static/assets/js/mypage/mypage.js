let follower = document.querySelector(".follower");
let followering = document.querySelector(".followering");
let medal = document.querySelector(".medal");
let followingBtnn = document.querySelector(".followingBtnn");
let followBtnn = document.querySelector(".followBtnn");
const lit1 = $('#lits1');
const lit2 = $('#lits2');
let $contents = $(".content2 > div");
let pagingNum = 1;

// 게시글 마우스 오버 이벤트
$.each($contents, function (i, item) {
    $(this).mouseover(function () {
        $(this).find('span').css('display', 'flex');

        // 임시 조건, 백엔드 연결 후 조건 수정
        if (i % 3 == 0) {
            $(this).find('span').html('심사 중');
        } else if (i % 3 == 1) {
            $(this).find('span').html('삭제된 챌린지');
        } else {
            $(this).find('span').html('진행 중');
        }

    });

    $(this).mouseout(function () {
        $(this).find('span').css('display', 'none');
    });

})


// 팔로워 모달 보이기
function followerModal() {
    follower.style.display = "block";
    follower.style.overflow = "none";
}

// 팔로워 모달 숨기기
function followerModalClose() {
    follower.style.display = "none";
}

// 팔로우 모달 보이기
function followeringModal() {
    followering.style.display = "block";
    followering.style.overflow = "none";
}

// 팔로우 모달 숨기기
function followeringModalClose() {
    followering.style.display = "none";
}

/*
// 메달 모달 보이기
function medalModal() {
  medal.style.display = "block";
  medal.style.overflow = "none";
}

// 메달 모달 숨기기
function medalModalClose() {
  medal.style.display = "none";
}

// 메달 선택시 해당 클릭 사항으로 변경
function representMedal(e) {
  let changeImg = e.getAttribute("src");
  document.getElementById("medalImg").src = changeImg;
  medal.style.display = "none";
}
*/


// 팔로우 모달에서 팔로잉-팔로우 변경
// function changeFollow() {
//     if (followingBtnn.style.display == "block") {
//         followingBtnn.style.display = "none";
//         followBtnn.style.display = "block";
//     } else {
//         followingBtnn.style.display = "block";
//         followBtnn.style.display = "none";
//     }
// }

// =================================================
//모달창
$("#medalImg").on("click", function () {
    $(".medal-background").addClass("display-toggle");
});

$(".btn__x").on("click", function () {
    $(".medal-background").removeClass("display-toggle");
});

$(document).ready(function () {
    litUpList(pagingNum);
    let $imgAr = $(".medal-icon-col > img");

    // 메달 목록 띄워주는 ajax
    myPageAjaxService.getMedal(mypageUser, function (medals) {
        let $imgAr = $(".medal-icon-col > img");

        for (let i = 0; i < medals.length; i++) {
            $($imgAr[medals[i] - 1]).attr("src", "/images/mypage/medal" + medals[i] + ".png");
            // $($imgAr[medals[i] - 1]).attr("src", "/images/mypage/medal.png");
        }
        // $($imgAr[3]).data("bar", medals.length + "/10");

        /*########################메달 4번##########################*/
        if (medals.length >= 10) {
            console.log("메달 10개 모으기 성공");
            myPageAjaxService.get4Medal(mypageUser)
        }

        // 해제된 메달에 medal__unlock 클래스 부여(마우스 오버 이벤트 등등에 사용)
        $imgAr
            .filter((idx, img) => {
                return img.src.substring(img.src.lastIndexOf("/") + 1) != "padlock.png";
            })
            .each((idx, img) => {
                $(img).parent(".medal-icon-col").addClass("medal__unlock");
            });

        //해제된 메달 수 세기
        let medalNumber = $(".medal__unlock").length || 0;
        $("#medal-selected_number").text(medalNumber);
    });
    //모달창 열면 내 대표 메달 이미지 가져오기
    //프론트 작업에서는 아이디 옆 메달을 가져왔지만 DB에서 가져오는게 좋을거 같음
    $(".medal-selected_medal > img").attr("src", $("#medalImg").attr("src"));
    setMedalMedal();

    // 대표 메달 사용하기 버튼
    $(".medal-selected_button").on("click", function () {
        let src = $(".medal-selected_medal > img").attr("src");
        if (src.substring(src.lastIndexOf("/") + 1) == "padlock.png") {
            return;
        }
        $("#medalImg").attr("src", src);
        $(".btn__x").trigger("click");
    });

    /*########################메달 4번##########################*/
    myPageAjaxService.get4MedalPercent(mypageUser, function (result) {
        $($imgAr[3]).data("bar", result + "/10");
    });

    /*########################메달 2번##########################*/
    myPageAjaxService.get2Medal(mypageUser, function (result) {
        $($imgAr[1]).data("bar", result + "/1");
    });

    /*########################메달 3번##########################*/
    myPageAjaxService.get3Medal(mypageUser, function (result) {
        $($imgAr[2]).data("bar", result + "/1");
    });

    /*########################메달 5번##########################*/
    myPageAjaxService.get5Medal(mypageUser, function (result) {
        $($imgAr[4]).data("bar", result + "/100");
    });

    /*########################메달 6번##########################*/
    myPageAjaxService.get6Medal(mypageUser, function (result) {
        $($imgAr[5]).data("bar", result + "/10");
    });

    /*########################메달 7번##########################*/
    myPageAjaxService.get7Medal({
        userNumber: mypageUser,
        category: "life"
    }, function (result) {
        $($imgAr[6]).data("bar", result + "/5");
    });

    /*########################메달 8번##########################*/
    myPageAjaxService.get8Medal({
        userNumber: mypageUser,
        category: "exercise"
    }, function (result) {
        console.log("마이페이지자바스크립트 들어옴");
        $($imgAr[7]).data("bar", result + "/5");
    });

    /*########################메달 9번##########################*/
    myPageAjaxService.get9Medal({
        userNumber: mypageUser,
        category: "heart"
    }, function (result) {
        $($imgAr[8]).data("bar", result + "/5");
    });

    /*########################메달 10번##########################*/
    myPageAjaxService.get10Medal({
        userNumber: mypageUser,
        category: "hobby"
    }, function (result) {
        $($imgAr[9]).data("bar", result + "/5");
    });

    /*########################메달 11번##########################*/
    myPageAjaxService.get11Medal({
        userNumber: mypageUser,
        category: "art"
    }, function (result) {
        $($imgAr[10]).data("bar", result + "/5");
    });

    /*########################메달 12번##########################*/
    myPageAjaxService.get12Medal(mypageUser, function (result) {
        $($imgAr[11]).data("bar", result + "/1000");
    });

    /*########################메달 13번##########################*/
    myPageAjaxService.get13Medal(mypageUser, function (result) {
        $($imgAr[12]).data("bar", result + "/1000");
    });

    /*########################메달 14번##########################*/
    myPageAjaxService.get14Medal(mypageUser, function (result) {
        $($imgAr[13]).data("bar", result + "/1000");
    });

    /*########################메달 15번##########################*/
    myPageAjaxService.get15Medal(mypageUser, function (result) {
        $($imgAr[14]).data("bar", result + "/1000000");
    });

    //클릭하면 내 대표메달로 변경하기
    $(".medal__unlock").on("click", function () {
        // 백엔드 작업 : 메달을 클릭하면 DB에 내 대표메달이 변경되어야 함

        //프론트
        $(".medal__unlock").removeClass("medal__check");
        $(this).addClass("medal__check");

        //대표 메달 이미지 가져오기
        //프론트 작업에서는 아이디 옆 메달을 가져왔지만 DB에서 가져오는게 좋을거 같음
        let $bigImg = $(".medal-selected_medal > img");
        $bigImg.attr("src", $(".medal__check > img").attr("src"));
        setMedalMedal();
    });

    // 대표 메달 사용하기 버튼
    $(".medal-selected_button").on("click", function () {
        let src = $(".medal-selected_medal > img").attr("src");
        if (src.substring(src.lastIndexOf("/") + 1) == "padlock.png") {
            return;
        }
        $("#medalImg").attr("src", src);
        $(".btn__x").trigger("click");
    });
});

// 미획득 메달 정보띄우기
$(".medal-icon-col").on("click", function () {
    // if ($(this).hasClass("medal__unlock")) {
    //   return;
    // }
    $(".medal__unlock").removeClass("medal__check");
    let $img = $(this).find("img");
    let $bigImg = $(".medal-selected_medal > img");
    $bigImg.attr("src", $img.attr("src"));
    setLockedMedal($img);
});

function setLockedMedal(img) {
    let $bigImg = $(".medal-selected_medal > img");

    $(".medal-selected_info > h3").text(img.attr("alt"));
    $(".medal-selected_detail > span").text(img.data("detail"));

    let barNum = img.data("bar");
    if (!barNum) {
        makeBar("1/1");
    } else {
        makeBar(barNum);
    }
}

// 메달 이름 dom으로 꽂기
// 프론트작업 백 작업시 DB 조회로 꽂으면 될듯함
function setMedalMedal() {
    let $imgAr = $(".medal-icon-col > img");
    let $bigImg = $(".medal-selected_medal > img");
    let img = $imgAr.filter((idx, img) => {
        return img.src.substring(img.src.lastIndexOf("/") + 1) ==
        $bigImg.attr("src").substring($bigImg.attr("src").lastIndexOf("/") + 1)
            ? img
            : "";
    });
    $(".medal-selected_info > h3").text(img.attr("alt"));
    $(".medal-selected_detail > span").text(img.data("detail"));

    let barNum = img.data("bar");
    if (!barNum) {
        makeBar("1/1");
    } else {
        makeBar(barNum);
    }
}

function makeBar(barNum) {
    let barRate = eval(barNum) * 100;
    $(".bar-rate").css({width: barRate + "%"});
    $(".rate").text(barNum);
    $(".rate").css({left: 50 - barNum.length * 2 + "%"});
}

// lit Up LITS 버튼 액션
lit1.on("click", function () {
    // color: rgb(142, 142, 142); 검은색
    lit1.attr('class', 'lits1On');
    $('#lit1Img').attr('src', '/images/mypage/menu.png');
    lit2.attr('class', 'lits2Off');
    $('#lit2Img').attr('src', '/images/mypage/fire.png');
    $(".photoContents > div").html("");
    pagingNum = 1;
    litUpList();
});

lit2.on("click", function () {
    // color: rgb(142, 142, 142); 검은색
    lit2.attr('class', 'lits2On');
    $('#lit2Img').attr('src', '/images/mypage/lists.png');
    lit1.attr('class', 'lits1Off');
    $('#lit1Img').attr('src', '/images/mypage/menu2.png');
    $(".photoContents > div").html("");
    pagingNum = 1;
    litList();
});


//마우스 오버
$(".photoContents > div").on("mouseenter", "figure>a", function () {
    // console.log("aaaaaaaaaaaa mouseOver");
    $(this).find(".over-box_div").css("display", "flex");
})
    .on("mouseleave", "figure>a", function () {
        $(this).find(".over-box_div").css("display", "none");
    })

function litUpList() {
    myPageAjaxService.litUpList(userPageNumber, {
        pageNum: pagingNum,
        amount: 9,
    }, function (result) {
        let str = "";
        // $(".photoContents > div").html("");
        result.forEach((data, i) => {
            let fileWriter = data.userNumber;
            let file = data.reviewFileList;
            if (file[0]) {
                str +=
                    "<figure class='reviewView' id='" + data.reviewNumber + "'>" +
                    "<a class='over-box_a' href='javascript:void(0)'>" +
                    "<img alt=\"\" src=\"/litUp/display?fileName=" + file[0].uploadPath + "/" + file[0].uuid + "_" + file[0].name + "\">" +
                    "<div class='over-box_div'>" +
                    "<div class='over-box_content1'>" +
                    "<img src='/images/main/heart__white.png' class='over-box_img'/>" +
                    "<div>&nbsp&nbsp" + data.likeCount + "</div>" +
                    "</div>" +
                    "<div class='over-box_content1'>" +
                    "<img src='/images/main/reply__white.png' class='over-box_img'/>" +
                    "<div>&nbsp&nbsp" + data.replyCount + "</div>" +
                    "</div>" +
                    "</div>" +
                    "</a>" +
                    "<input type='hidden' id='" + fileWriter + "' />" +
                    "</figure>";
            }
        })
        $(".photoContents > div").append(str);
    })
    pagingNum++;
}

function litList() {
    myPageAjaxService.litList(userPageNumber, {
            pageNum: pagingNum,
            amount: 9,
        },
        function (result) {
            let str = "";
            // $(".photoContents > div").html("");
            result.forEach((data, i) => {
                console.log(data)
                let status;
                let file = data.projectFile;
                switch (data.status) {
                    case 0 :
                        status = "대기중";
                        break;
                    case 1 :
                        status = "진행중";
                        break;
                    case 2 :
                        status = "완료됨";
                        break;
                }
                if (file) {
                    str +=
                        "<figure class='projectView' id='" + data.projectNumber + "'>" +
                        "<a class='over-box_a' href='javascript:void(0)'>" +
                        "<img alt=\"\" src=\"/lit/display?fileName=" + file.uploadPath + "/" + file.uuid + "_" + file.name + "\">" +
                        "<div class='over-box_div'>" +
                        "<div class='over-box_content1'>" +
                        "<div>" + status + "</div>" +
                        "</div>" +
                        "</div>" +
                        "</a>" +
                        "</figure>";
                }
            });
            $(".photoContents > div").append(str);
        });
    pagingNum++;
}

$(".a").on("click", "figure.projectView", function () {
    let getProjectNum = $(this).attr("id")
    location.href = "/lit/info?projectNumber=" + getProjectNum;

})


//스크롤 이벤트
let timer;
$(window).scroll(function () {
    // 현 스크롤 탑의 위치
    let windowTop = $(window).scrollTop();
    // 결과 리스트를 감싸는 요소 높이
    let contentHeight = $(".photoContents").height();
    // 창의 전체 높이
    let windowHeight = $(window).height();
    console.log("***************************************************")
    console.log("***************************************************")


    // 스크롤이 마지막 이면 데이터 가져오기
    if (windowTop > (contentHeight - windowHeight)) {
        if (timer) {
            clearTimeout(timer);
        }
        timer = setTimeout(() => {
            // if(pagingNum > realEnd){ return; }
            if(lit1.hasClass("lits1On")){
                litUpList();
            } else if(lit2.hasClass("lits2On")){
                litList();
            }

        }, 500);
    } else {
    }
});