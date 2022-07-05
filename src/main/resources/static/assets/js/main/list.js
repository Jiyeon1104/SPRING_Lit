// 기본 시작은 lit
litList();

function litList() {
    console.log("ajax----------------");
    $.ajax({
        url: "/list/" +cate1+ "/" + order +"/" + cate2 + "/" + pageNum,
        type: "get",
        dataType: 'json',
        success: function (result) {
            console.log("page : " + pageNum);
            pageNum++; // 다음 페이지 번호 지정

            if( cate1 == "litups"){
                console.log("litup");
                litupadd(result);
            }else {
                litsadd(result);
            }

        }, error: function (xhr, textStatus, errorThrown) {
            console.log('통신 실패');
            console.log(xhr);
            console.log(textStatus);
            console.log(errorThrown);
        }
    });
}

// 리스트에 추가하기
function litsadd(result) {
    let str = "";  // 리스트 html 담을 변수

    $.each(result, function (i, projectVO) {
        let src = "/lit/display?fileName=";
        //이미지 경로
        src += projectVO.projectFile.uploadPath + "/";
        src += projectVO.projectFile.uuid + "_";
        src += projectVO.projectFile.name;
        // console.log(src);
        str += "<figure><a href='"+projectVO.projectNumber+"'>";
        str += "<img src='"+src+"'>";
        str += "</a></figure>";
    });
    contBox.append(str);
}

function litupadd(result) {
    let str = "";
    result.forEach( (data, i) => {
        let file = data.reviewFileList;
        if(file[0]){
            str += "<figure><a href=\"" + data.reviewNumber + "\">";
            str += "<img src=\"/litUp/display?fileName=" + file[0].uploadPath + "/" + file[0].uuid + "_" + file[0].name + "\">";
            str += "</a></figure>";
        }
    })
    $(".photoContents > div").append(str);
}

//-----------------------------------------------------

// 카테고리 클릭시 이벤트 페이지 이동
$("._category_wrapper a").on("click", function (e) {
    e.preventDefault();
    if( $(this).hasClass("on") ){  // 같은 태그 선택시 이벤트 취소
        return false;
    }

    // 선택 태그 클래스 주입
    $("._category_wrapper a").removeClass("on");
    $(this).addClass("on");
    contBox.html("");                 // 이미지 리스트 초기화
    pageNum = 1;                    // 페이지번호 초기화
    cate2 = $(this).attr("href");   // 링크 변수값( 카테고리 값 )
    console.log(cate2);
    litList();                      // ajax 실행
});

// 이미지 클릭시 모달창 오픈 ----------------------------------------------------------------
$("photoContents a").on("click", function (e) {
    e.preventDefault();
    let link = $(this).attr("href"); // 링크 변수값

});
//---------------------------------------------------------------------------------------

//스크롤 이벤트
let timer;
$(window).scroll(function () {
    // 라스트 페이지면 실행 불가
    // if(pageNum == last){
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
            litList();  // ajax 실행
        }, 500);
    } else {
        //스크롤중..
    }
});

// lis litup 스위치 -----------------
const lit1 = $('#lits1');
const lit2 = $('#lits2');
// lit Up 버튼 액션
lit1.on("click", function(){
    if( !$(this).hasClass("on") ){
        $(this).addClass("on");
        lit2.removeClass("on")
        cate1 = "litups";   // lits / litups
        contBox.html(""); // 이미지리스트 초기화
        pageNum = 1;                    // 페이지번호 초기화
        litList(); //리스트 가져오기
    }

    lit1.attr('class', 'lits1On');
    $('#lit1Img').attr('src', '/images/mypage/menu.png');

    lit2.attr('class', 'lits2Off');
    $('#lit2Img').attr('src', '/images/mypage/fire.png');

    // getLitUpList();
});

// LITS 버튼 액션
lit2.on("click", function(){
    if( !$(this).hasClass("on") ){
        $(this).addClass("on");
        lit1.removeClass("on");
        cate1 = "lits";     // lits / litups
        contBox.html("");     // 이미지리스트 초기화
        pageNum = 1;         // 페이지번호 초기화
        litList();          //리스트 가져오기
    }
    lit2.attr('class', 'lits2On');
    $('#lit2Img').attr('src', '/images/mypage/lists.png');
    lit1.attr('class', 'lits1Off');
    $('#lit1Img').attr('src', '/images/mypage/menu2.png');
});