let modal = document.querySelector('.detailModalBackground');
let projectDetailButton = document.querySelector('.projectDetailButton');
let profileImage = document.querySelectorAll('.detailProfileImage');
let profileModal = document.querySelector(".profileModal");
let detailContentLikeButton = document.querySelector(".detailContentLikeButton");
let detailContentLikeCancel = document.querySelector(".detailContentLikeCancel");
let commentInputArea = document.querySelector(".detailContentFooterCommentInputArea");
let commentButton = document.querySelector(".commentButton");

//textarea 내용이 없을 경우 게시 버튼 비활성화
commentInputArea.addEventListener("input", function (e) {
    if (commentInputArea.value == '') {
        commentButton.disabled = true;
    } else {
        commentButton.disabled = false;
    }
})

//프로젝트 모달 창 켜는 버튼
function projectDetailModalShow(reviewNumber,userNumber) {
    if(userNumber !=null){
    modal.style.display = 'block';
    // projectDetailButton.style.display = 'none';
    $('body').css("overflow", "hidden");
    pageNum = 1;
    showList(pageNum);
    $reviewNumber = reviewNumber;
    // 리뷰 디테일 조회, 리뷰넘버 받아와서 넣어줘야 함
    reviewDetailService.readDetail($reviewNumber,function (nickname, content, registerDate) {
        $(".detailContentProfileName").html(nickname);
        $(".detailContentWriteName").html(nickname);
        $(".detailContentWriteComment").html(content);
        $(".detailContentRegisterDate").html(reviewDetailService.getReplyDate(registerDate));
        $(".detailContentFooterWirtesInnerDate").html(reviewDetailService.getReplyDate(registerDate));
    })

    // 좋아요 총 갯수
    reviewDetailService.getLikeTotal($reviewNumber,function (result) {
        $("#likeCount").html(result);
    })

    // 좋아요 여부 확인
    reviewDetailService.getCheckLike({userNumber:userNumber, reviewNumber:$reviewNumber},function (result) {
        if(result){
            detailContentLikeButton.style.display = 'none';
            detailContentLikeCancel.style.display = 'block';
        }else{
            detailContentLikeButton.style.display = 'block';
            detailContentLikeCancel.style.display = 'none';
        }
    })
    }else{
        alert("로그인 후 이용해주세요")
    }
}

//프로젝트 모달 창 숨기는 버튼,
//이미지 슬라이드와 버튼의 active 클래스를 다시 초기화해줌
function projectDetailModalHide() {
    modal.style.display = 'none';
    // projectDetailButton.style.display = 'block';

    $('.innerImageWrapper ul li:first-child').addClass('active');
    $('.innerImageWrapper ul li:first-child').siblings('.active').removeClass('active');
    $('.innerImageWrapper div div:first-child').addClass("active");
    $('.innerImageWrapper div div:first-child').siblings('.active').removeClass('active');
    $('body').css("overflow", "auto");

}

//좋아요 버튼 누를 시의 이벤트
//하트 이미지 바꿔주기
detailContentLikeButton.addEventListener("click", function (e) {
    detailContentLikeButton.style.display = 'none';
    detailContentLikeCancel.style.display = 'block';
});

detailContentLikeCancel.addEventListener("click", function (e) {
    detailContentLikeButton.style.display = 'block';
    detailContentLikeCancel.style.display = 'none';
});


//프로필 이미지에 마우스 버튼 올릴 때
//작은 추가 프로필 정보 모달창 1초 뒤에 생성
//마우스를 떼면 사라짐
profileImage.forEach(function (item) {
    item.addEventListener("mouseover", function (e) {
        setTimeoutConst = setTimeout(function () {
            profileModal.style.display = 'block';
            profileModal.style.top = e.clientY - 5 + "px";
            profileModal.style.left = e.clientX - 5 + "px";
        }, 1000)
    }, function () {
        clearTimeout(setTimeoutConst);
    });

    item.addEventListener("mouseout", function (e) {
        clearTimeout(setTimeoutConst);
        profileModal.style.display = 'none';
    });

});

//버튼형 슬라이더
//이미지 아래 버튼 클릭했을 때 해당하는 이미지로 넘겨주는 기능
$('.innerImageWrapper > .innerImagePageButtons').on("click","div",function () {
    let $this = $(this);
    let index = $this.index();
    $this.addClass('active');
    $this.siblings('.active').removeClass('active');

    let $slider = $this.parent().parent();
    let $current = $slider.find('> .innerImageSlides > li.active');
    let $post = $slider.find('> .innerImageSlides > li').eq(index);

    $current.removeClass('active');
    $post.addClass('active');
});

//좌/우 슬라이더
//이미지 양 옆 버튼 클릭했을 때 다음/이전 이미지로 넘겨주는 기능
$('.innerImageWrapper > .innerImageSideButtons > div').click(function () {
    let $this = $(this);
    let $slider = $this.closest('.innerImageWrapper');
    let index = $this.index();
    let isLeft = index == 0;
    let $current = $slider.find(' > .innerImagePageButtons > div.active');
    let $post;

    if (isLeft) {
        $post = $current.prev();
    }
    else {
        $post = $current.next();
    };

    if ($post.length == 0) {
        if (isLeft) {
            $post = $slider.find(' > .innerImagePageButtons > div:last-child');
        }
        else {
            $post = $slider.find(' > .innerImagePageButtons > div:first-child');
        }
    };

    $post.click();
});


