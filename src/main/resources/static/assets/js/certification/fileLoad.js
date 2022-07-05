let $fileUploadArea = $('.fileUploadArea');
let $fileUploadAreaWrapper = $('.fileUploadAreaWrapper');
let $fileClickInput = $('.fileClickInput #fileClickInputLabel');
let $fileUploadPreview = $('.fileUploadPreview');
let $certificationImageInner = $('.certificationImageInner');
let $certificationImageWrapper = $('.certificationImageWrapper');
let $innerImagePageButtons = $('.innerImagePageButtons1');
let $previewButton = $('.previewButton');
let $deleteBackground = $('.deleteBackground');
let $deleteImageBackground = $('.deleteImageBackground');

let fileType1 = /(.*?)\.(jpg|jpeg|png)$/;

let uploadFiles1 = [];

//파일 직접 올릴 때 실행되는 함수
$('.fileClickInput').on('change', function (e) {

    let files = e.target.files;
    let check = $('#fileClickInput').val();

    //유효성검사
    if (!check.match(fileType1)) {
        $('.uploadLoge').css("display", "none");
        $('.uploadError').css("display", "block");
        $certificationHeaderLabel.text('파일 업로드 실패');
        $fileClickInput.text('다른 파일 선택');
        $('.fileUploadLabel').text('지원되지 않는 파일입니다');
        $('#fileClickInput').val("");
        uploadFiles1 = [];
        return;
    }

    for (let i = 0; i < files.length; i++) {
        let file = files[i];
        let size = uploadFiles1.push(file);  //업로드 목록에 추가
        preview(file, size - 1);  //미리보기 만들기
    }

    twinkle();
    $certificationHeaderLabel.text('사진 업로드');
    $BackButton.css("display", "block");
    $fileUploadAreaWrapper.css("display", "none");
    $certificationImageWrapper.css("display", "block");
    $previewButton.css("display", "block");
    $certificationFirstButton.css("display", "block");
    $certificationNextButton.css("display", "block");
}).on('dragover',function(){
    $(this).css("pointerEvents","none");
});

// 드래그-드랍 구역에
// 이미지 드래그, 드랍 할 때 실행되는 함수
$fileUploadArea.on("dragenter", function (e) {  //드래그 요소가 들어왔을때
    $(this).addClass('drag-over');
    $('.uploadLoge').attr("color", "rgb(250,100,98)");

}).on("dragleave", function (e) {  //드래그 요소가 나갔을때
    $(this).removeClass('drag-over');
    $('.uploadLoge').attr("color", "");
}).on("dragover", function (e) {
    e.stopPropagation();
    e.preventDefault();
}).on('drop', function (e) {  //드래그한 항목을 떨어뜨렸을때
    e.preventDefault();
    $(this).removeClass('drag-over');
    $('.uploadLoge').attr("color", "");
    $('.fileClickInput').css("pointerEvents","all");

    let files = e.originalEvent.dataTransfer.files;

    //유효성 검사 및 파일 추가
    for (let i = 0; i < files.length; i++) {
        let file = files[i];
        if (!file.name.match(fileType1)) {
            $('.uploadLoge').css("display", "none");
            $('.uploadError').css("display", "block");
            $certificationHeaderLabel.text('파일 업로드 실패');
            $fileClickInput.text('다른 파일 선택');
            $('.fileUploadLabel').text('지원되지 않는 파일입니다');
            $('#fileClickInput').val("");
            uploadFiles1 = [];
            return;
        }
        let size = uploadFiles1.push(file);  //업로드 목록에 추가
        preview(file, size - 1);  //미리보기 만들기
    }

    twinkle();
    $certificationHeaderLabel.text('사진 업로드');
    $BackButton.css("display", "block");
    $fileUploadAreaWrapper.css("display", "none");
    $certificationImageWrapper.css("display", "block");
    $previewButton.css("display", "block");
    $certificationFirstButton.css("display", "block");
    $certificationNextButton.css("display", "block");
});

//사진 삭제용 미리보기 버튼
$previewButton.on('click', 'button', function () {
    if ($previewButton.val() != 1) {
        $fileUploadPreview.css("display", "-webkit-box");
        $previewButton.find('button').css("backgroundColor", 'white');
        $('.btnIcon').attr("fill", "black");
        $previewButton.val('1');
    } else {
        $fileUploadPreview.css("display", "none");
        $previewButton.find('button').css("backgroundColor", '#373737');
        $('.btnIcon').attr("fill", "white");
        $previewButton.val('0');
    }
})


//작은 미리보기 창 생성
//모달 창에도 이미지 리스트 생성
function preview(file, idx) {
    let reader = new FileReader();
    reader.onload = (function (f, idx) {
        return function (e) {
            let res;
            let div;
            let str = '<div class="preview">';
            str += '<div class="close" data-idx="' + idx + '"><svg aria-label="닫기" class="fg7vo5n6 lrzqjn8y" color="#ffffff" fill="#ffffff" height="10" role="img" viewBox="0 0 48 48" width="10"><title>닫기</title><path clip-rule="evenodd" d="M41.8 9.8L27.5 24l14.2 14.2c.6.6.6 1.5 0 2.1l-1.4 1.4c-.6.6-1.5.6-2.1 0L24 27.5 9.8 41.8c-.6.6-1.5.6-2.1 0l-1.4-1.4c-.6-.6-.6-1.5 0-2.1L20.5 24 6.2 9.8c-.6-.6-.6-1.5 0-2.1l1.4-1.4c.6-.6 1.5-.6 2.1 0L24 20.5 38.3 6.2c.6-.6 1.5-.6 2.1 0l1.4 1.4c.6.6.6 1.6 0 2.2z" fill-rule="evenodd"></path></svg></div>';
            str += '<img src="' + e.target.result + '"/></div>';
            $fileUploadPreview.append(str);


            if ($certificationImageInner.children().length == 0) {
                res = '<li class="active" id="' + idx + '"><img src= "' + e.target.result + '"/></li>';
                div = '<div class="active" id="' + idx + '"></div>';
                $certificationImageInner.html(res);
                $innerImagePageButtons.html(div);
            } else {
                res = '<li id="' + idx + '"><img src= "' + e.target.result + '"/></li>';
                div = '<div id="' + idx + '"></div>';
                $certificationImageInner.append(res.trim());
                $innerImagePageButtons.append(div.trim());
            }

        };
    })(file, idx);
    reader.readAsDataURL(file);
}

// 작은 미리보기 창에서 x버튼 누를 때
$fileUploadPreview.on("click", ".close", function (e) {

    if ($certificationImageInner.children().length == 1) {
        $deleteImageBackground.css("display", "block");

        $('.deleteImageModalButton').on("click", function () {
            if ($(this).val() == 'y') {
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
                uploadFiles1 = [];
                $deleteImageBackground.css("display", "none");
            } else {
                $deleteImageBackground.css("display", "none");
                return;
            }
        });
    } else {
        let $target = $(e.target);
        let idx = $target.attr('data-idx');

        uploadFiles1[idx].upload = 'disable';  //삭제된 항목은 업로드하지 않기 위해 플래그 생성
        $target.parent().remove();  //프리뷰 삭제
        $certificationImageInner.children('#' + idx).remove();
        $certificationImageInner.children().eq(0).addClass("active");
        $innerImagePageButtons.children('#' + idx).remove();
        $innerImagePageButtons.children().eq(0).addClass("active");
    }

});


// 작은 미리보기 창 가로 스크롤
$(".fileUploadPreview").on('mousewheel', function (e) {
    var wheelDelta = e.originalEvent.wheelDelta;
    if (wheelDelta > 0) {
        console.log("up");
        $(this).scrollLeft(-wheelDelta + $(this).scrollLeft());
    } else {
        console.log("down");
        $(this).scrollLeft(-wheelDelta + $(this).scrollLeft());
    }
});



// 제출 시에 사용
// $("#btnSubmit").on("click", function () {
//     var formData = new FormData();
//     $.each(uploadFiles1, function (i, file) {
//         if (file.upload != 'disable')  //삭제하지 않은 이미지만 업로드 항목으로 추가
//             formData.append('upload-file', file, file.name);  //모든 첨부파일은 upload-file 이름으로 전달함
//     });
//     $.ajax({
//         url: '업로드URL',
//         data: formData,
//         type: 'post',
//         contentType: false,
//         processData: false,
//         success: function (ret) {
//             alert("완료");
//         }
//     });
// });