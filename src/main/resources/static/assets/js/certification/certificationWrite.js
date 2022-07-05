let certificationWriteBackground = document.querySelector('.certificationWriteBackground');
let typeDownButton = document.querySelector('.typeDownButton');
let typeUpButton = document.querySelector('.typeUpButton');
let warningDownButton = document.querySelector('.warningDownButton');
let warningUpButton = document.querySelector('.warningUpButton');
let projectInfoDropDown = document.querySelector('.projectInfoDropDown');
let warningDropDown = document.querySelector('.warningDropDown');
// let certificationWriteButton = document.querySelector('.certificationWriteButton');

let $BackButton = $('.certificationBackButton');
let $NextButton = $('.certificationNextButton');

let $certificationFirstButton = $('.certificationBackButton > button.firstButton');
let $certificationBackButton = $('.certificationBackButton > button.backButton');
let $certificationBackBackButton = $('.certificationBackButton > button.backBackButton');
let $certificationNextButton = $('.certificationNextButton > button.nextButton');
let $certificationNextNextButton = $('.certificationNextButton > button.nextNextButton');
let $certificationCompleteButton = $('.certificationNextButton > button.completeButton');

let $certificationWriteModal = $('.certificationWriteModal');
let $certificationContent = $('.certificationContent');
let $detailProjectContent = $('.detailProjectContent');
let $detailProjects = $('.detailProjectContentList');
let $certificationHeaderLabel = $('.certificationHeaderLabel > strong');
let $modalLight = $('.modalLight');
let $projectAlert = $('.projectAlert');
let $whiteBackground = $('.whiteBackground');
let $deleteModalButton = $('.deleteModalButton');
let $deleteImageModalButton = $('.deleteImageModalButton');
let $clickFlag

//프로젝트 모달 창 켜는 버튼
function certificationWriteModalShow() {
    if(userNumber != null) {
        certificationWriteBackground.style.display = 'block';
        // certificationWriteButton.style.display = 'none';
        uploadFiles1 = [];
        $('body').css("overflow", "hidden");
    }else{
        alert("로그인 후 이용해 주세요")
    }
}

//프로젝트 모달 창 숨기는 버튼,
//이미지 슬라이드와 버튼의 active 클래스를 다시 초기화해줌

function certificationWriteModalHide() {
    $deleteBackground.css("display", "block");
    $deleteBackground.css("z-index", "11");

    $('.deleteModalButton').on("click",function(){
        if($(this).val() == 'y'){
            certificationWriteBackground.style.display = 'none';
            // certificationWriteButton.style.display = 'block';

            $('.certificationImages ul li:first-child').addClass('active');
            $('.certificationImages ul li:first-child').siblings('.active').removeClass('active');
            $('.certificationImages div div:first-child').addClass("active");
            $('.certificationImages div div:first-child').siblings('.active').removeClass('active');
            $('.certificationContentArea > textarea').val('');
            $('.certificationImageInner').empty();
            $('.innerImagePageButtons1').empty();
            $fileUploadPreview.empty();
            $('#fileClickInput').val("");
            $('body').css("overflow", "auto");

            $.each($detailProjects, function () {
                $(this).removeClass("on");
            });

            uploadFiles1 = [];

            $detailProjectContent.css("width", "0");
            $certificationWriteModal.css("width", "35%");
            $whiteBackground.css("display", "block");
            $certificationNextButton.css("display", "none");
            $certificationHeaderLabel.text('사진 업로드');
            $certificationContent.css("display", "none");
            $detailProjectContent.css("display", "none");
            $certificationNextNextButton.css("display", "none");
            $certificationBackButton.css("display", "none");
            $certificationBackBackButton.css("display", "none");
            $certificationCompleteButton.css("display", "none");
            $certificationImageWrapper.css("display", "none");
            $fileUploadAreaWrapper.css("display", "block");
            $certificationFirstButton.css("display", "none");
            $fileUploadPreview.css("display", "none");
            $previewButton.find('button').css("backgroundColor", '#373737');
            $('.btnIcon').attr("fill", "white");
            $previewButton.val('0');
            $deleteBackground.css("display", "none");
        }else{
            $deleteBackground.css("display", "none");
        }
    });


}


$.each($detailProjects, function () {
    $(".detailProjectContentList").on("mouseover",".projectPic",function () {

        $(this).find('span').css("display", "block");
    });
    $(".detailProjectContentList").on("mouseleave",".projectPic",function (){

        $(this).find('span').css("display", "none");
    });
    $(".detailProjectContentList").on("click",".projectPic",function () {
        if ($(this).hasClass("on")) {
            $(this).removeClass("on");
            $clickFlag = false;
        } else {
            $(this).addClass("on");
            $(this).siblings(".on").removeClass("on");
            $projectNumber = $(this).attr("id");
            $clickFlag = true;

        }
    })
});


//모달창 화면 깜빡임 효과
function twinkle() {
    $modalLight.css('display', 'block');
    $modalLight.animate({ 'opacity': 0.8 }, 100, function () {
        $modalLight.animate({ 'opacity': 0 }, 300, function () {
            $modalLight.css('display', 'none');
        });
    });
}

//사진 미리보기 중 뒤로가기 눌렀을 때
//미리보기, 파일 비워주고 화면전환
$certificationFirstButton.on("click", function () {

    $deleteImageBackground.css("z-index", "11");
    $deleteImageBackground.css("display", "block");

    $('.deleteImageModalButton').on("click",function(){
        if($(this).val() == 'y'){
            twinkle();
            $BackButton.css("display", "none");
            $certificationFirstButton.css("display", "none");
            $certificationNextButton.css("display", "none");
            $fileUploadAreaWrapper.css("display", "block");
            $certificationImageWrapper.css("display", "none");
            $previewButton.css("display", "none");
            $('.certificationImageInner').empty();
            $('.innerImagePageButtons1').empty();
            $fileUploadPreview.empty();
            $('#fileClickInput').val("");
            uploadFiles = [];
            $deleteImageBackground.css("display", "none");
            $deleteImageBackground.css("z-index", "0");
        }else{
            $deleteImageBackground.css("display", "none");
            $deleteImageBackground.css("z-index", "0");
        }
    });
});

//사진 업로드 후 다음 버튼 누를때 모달창 크기 늘이기
$certificationNextButton.on("click", function () {
    twinkle();

    $certificationWriteModal.css("width", "54%");
    $detailProjectContent.css("width", "74%");
    $detailProjectContent.css("display", "block");
    $whiteBackground.css("display", "none");
    $certificationHeaderLabel.text('챌린지 선택');
    $certificationNextButton.css("display", "none");
    $certificationNextNextButton.css("display", "block");
    $certificationBackButton.css("display", "block");
    $certificationFirstButton.css("display", "none");
    $fileUploadPreview.css("display", "none");
    $previewButton.find('button').css("backgroundColor", '#373737');
    $('.btnIcon').attr("fill", "white");
    $previewButton.val('0');
    $previewButton.css("display", 'none');
});

//다음 버튼 두 번 눌렀을 때
$certificationNextNextButton.on("click", function () {
    if (!$clickFlag) {
        $projectAlert.css("display", "block");
        $projectAlert.animate({ "opacity": 1 }, 400, function () {
            $projectAlert.animate({ "opacity": 0 }, 600, function () {
                $projectAlert.css('display', 'none');
            })
        })
        return;
    }
    twinkle();
    //////////////// 프로젝트 넘버, 유저 넘버 받아오기
    reviewWriteService.getProject({projectNumber:$projectNumber, userNumber:userNumber},
        function (title, content, startDate, nickname) {
            $(".projectInfoDropDownContent span.title").html(title)
            $(".projectInfoDropDownContent span.content").html(content)
            $(".projectInfoDropDownContent span.startDate").html(reviewWriteService.getRegisterDate(startDate))
            $(".projectInfoDropDownContent span.writer").html(nickname)
            $(".certificationUserId").html(nickname)

        },function (a,b,c) {
            console.log("error");
        })

    $certificationContent.css("display", "block");
    $detailProjectContent.css("display", "none");
    $certificationNextNextButton.css("display", "none");
    $certificationCompleteButton.css("display", "block");
    $certificationHeaderLabel.text('새 인증글 작성');
    $certificationBackButton.css("display", "none");
    $certificationBackBackButton.css("display", "block");
})

//프로젝트 선택에서 이전 화살표 누를 때
//모달창 크기 줄이기, 버튼 변경
$certificationBackButton.on("click", function () {
    twinkle();

    $certificationWriteModal.css("width", "35%");
    $detailProjectContent.css("width", "0");
    $whiteBackground.css("display", "block");
    $certificationHeaderLabel.text('사진 업로드');
    $certificationNextButton.css("display", "block");
    $certificationNextNextButton.css("display", "none");
    $certificationBackButton.css("display", "none");
    $certificationFirstButton.css("display", "block");
    $previewButton.css("display", 'block');
});

//인증글 작성에서 이전 화살표 누를 시 모달창 안 내용 변경
$certificationBackBackButton.on("click", function () {
    twinkle();

    $certificationContent.css("display", "none");
    $detailProjectContent.css("display", "block");
    $certificationNextNextButton.css("display", "block");
    $certificationCompleteButton.css("display", "none");
    $certificationHeaderLabel.text('챌린지 선택');
    $certificationBackButton.css("display", "block");
    $certificationBackBackButton.css("display", "none");
});



//인증글 작성에서 프로젝트 정보 관련 v자 버튼
typeUpButton.addEventListener("click", function () {
    typeUpButton.style.display = 'none';
    typeDownButton.style.display = 'block';
    projectInfoDropDown.style.height = '0px';
});

typeDownButton.addEventListener("click", function () {
    typeUpButton.style.display = 'block';
    typeDownButton.style.display = 'none';
    projectInfoDropDown.style.height = '100px';


});

//인증글 작성에서 인증글 신고 안내글 관련 v자 버튼
warningUpButton.addEventListener("click", function () {
    warningUpButton.style.display = 'none';
    warningDownButton.style.display = 'block';
    warningDropDown.style.height = '0px';
});

warningDownButton.addEventListener("click", function () {
    warningUpButton.style.display = 'block';
    warningDownButton.style.display = 'none';
    warningDropDown.style.height = '100px';
});

//인증글 작성 textarea 글 길이 검사
$('.certificationContentArea > textarea').on('input', function () {
    let cnt = $(this).val().length
    if (cnt > 400) {
        $('.certificationContentButtons > div > div:first-child').css("display", "block");
        $(this).val($(this).val().substring(0, 400));
        $('.certificationContentButtons > div > div > button > span').text(400);
    } else {
        $('.certificationContentButtons > div > div:first-child').css("display", "none");
        $('.certificationContentButtons > div > div > button > span').text(cnt);
    }
});

//버튼형 슬라이더
//이미지 아래 버튼 클릭했을 때 해당하는 이미지로 넘겨주는 기능
$('.certificationImages > .innerImagePageButtons1').on('click', 'div', function () {
    let $this = $(this);
    let index = $this.index();
    $this.addClass('active');
    $this.siblings('.active').removeClass('active');

    let $slider = $this.parent().parent();
    let $current = $slider.find('> .certificationImageInner > li.active');
    let $post = $slider.find('> .certificationImageInner > li').eq(index);

    $current.removeClass('active');
    $post.addClass('active');
});
