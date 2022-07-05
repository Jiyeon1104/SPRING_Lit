
let projectInfoPageNum = 1; // 인증글 리스트 페이지 번호
const contBox = $('.sec2 > div');  // 인증글 리스트 출력 위치

// 첫 이미지 리스트
litUpList();
// ajax 서버통신
function litUpList() {
    $.ajax({
        url: "/litUp/litInfo/" + projectInfoPageNum + "/" +projectNumber1,
        type: "get",
        dataType: 'json',
        success: function (result) {
            projectInfoPageNum++; // 다음 페이지 번호 지정
            litupadd(result);
        }, error: function (xhr, textStatus, errorThrown) {
            console.log('통신 실패');
            console.log(xhr);
            console.log(textStatus);
            console.log(errorThrown);
        }
    });
}
// LitUp 데이터 Html input
function litupadd(result) {
    let str = "";
    result.forEach( (data, i) => {
        let file = data.reviewFileList;
        if(file[0]){
            str += "<figure><a href=\"" + data.reviewNumber + "\">";
            str += "<img src=\"/litUp/display?fileName=" + file[0].uploadPath + "/" + file[0].uuid + "_" + file[0].name + "\">";
            str += "<input type='hidden' id='"+ data.userNumber +"'>";
            str += "</a></figure>";
        }
    })
    contBox.append(str);
}


//스크롤 이벤트
let timer;
$(window).scroll(function () {
    // 라스트 페이지면 실행 불가
    // if(projectInfoPageNum == last){
    //     return false;
    // }
    // 현 스크롤 탑의 위치
    let windowTop = $(window).scrollTop();
    // 변화될 아이
    let contentHeight = $(".photoContents").height();
    // 창의 전체 높이
    let windowHeight = $(window).height();

    // 스크롤이 마지막 이면 데이터 가져오기
    if (windowTop > (contentHeight - windowHeight)) {
        // 스크롤 맨 아래
        if (timer) {
            clearTimeout(timer);
        }
        timer = setTimeout(() => {
            litUpList();  // ajax 실행
        }, 500);
    } else {
        //스크롤중..
    }
});


// 리뷰 클릭 이벤트 
// $("#reviewList").on("click", "figure > a", function (e) {
//     e.preventDefault();
//     let href = $(this).attr("href");
//     console.log(href);
// })



$("label[for='btn1']").on("click", function () {
    let datas = {
        projectNumber: projectNumber1,
        userNumber: userNumber1,
        status : 1
    }
    replyService.challenge(datas, function () {
        alert("참여되었습니다.");
        let str = "<label class=\"btn\">참여중</label>";
        let num = Number( $("#pCount").text() ) + 1;
        $(".challengeBtn").html(str);
        $("#pCount").html(num);
    });
});

let replyService = (function(){
    function challenge(datas, callback){
        $.ajax({
            url: "/lit/challenge",
            type: "post",
            data: JSON.stringify(datas),
            contentType: "application/json",
            success: function (result) {
                if(callback){
                    callback(result);
                }
            }, error: function (xhr, textStatus, errorThrown) {
                console.log('통신 실패');
                console.log(xhr);
                console.log(textStatus);
                console.log(errorThrown);
            }
        });
    }
    return{
        challenge:challenge
    }
})();