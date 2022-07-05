
let changeProfile = document.querySelector('#changeProfile');
let proFileImg = document.querySelector('#proFileImg');
const nickNameCk = $('#pepUsername');
const userName = $('#pepName');
const userContent = $('#pepBio');
const eamil = $('#email');

let deleteImgChk = false;

// 프로필 사진 바꾸기 모달 띄우기
function changeProfileModal(){
    changeProfile.style.display='block';
    changeProfile.style.overflow='none';
}

// 취소 버튼 클릭시
function changeProfileModalClose(){
    changeProfile.style.display='none';
}

// 현재 사진 삭제 버튼 클릭시
function changeProfileDelete(){
    document.getElementById('proFileImg').src = "/images/main/profile_ex.png";
    changeProfile.style.display='none';

    deleteImgChk = true;
    $('#subBtn').attr("class", "submitBtnOn");
    $('#subBtn').attr("disabled", false);
}

// 사진 업로드 버튼 클릭시
function uploadProfile(){
    let fileInput = document.getElementById('ModalFileInput');
    let file = fileInput.click();

    changeProfile.style.display='none';
}

// 변경된 사진으로 프로필 사진 변경
$('#ModalFileInput').on("change", function(e){
    // console.log($('#ModalFileInput'));
    let reader = new FileReader();

    let fileList = e.target.files;
    let file = fileList[0];
    // console.log(fileList);
    // console.log(file);
    reader.onload = function(e){
        // console.log("bbb");

        console.log(e.target.result);
        proFileImg.src = e.target.result;
    }
    reader.readAsDataURL(file);

    $('#subBtn').attr("class", "submitBtnOn");
    $('#subBtn').attr("disabled", false);
})

function checkNick(result) {
    // console.log(nickNameCk.val() + "++++++++++++");
    if (!result) {

        $('._checkArea1').html('사용이 불가능한 사용자 이름입니다.');
        $('._checkArea1').css('color', 'red');

    } else {

        $('._checkArea1').html('사용 가능한 사용자 이름입니다.');
        $('._checkArea1').css('color', 'black');
    }
}


//버튼 활성화/비활성화
function buttonOn2(){
    if(userName.val().length > 0 && nickNameCk.val().length > 0){
        $('#subBtn').attr("class", "submitBtnOn");
        $('#subBtn').attr("disabled", false);
    } else{
        $('#subBtn').attr("class", "submitBtn");
        $('#subBtn').attr("disabled", true);
    }
}

$('#subBtn').on("click", function(e){
    e.preventDefault();

    if(deleteImgChk){
        changeInfoAjax.deleteImg(userNumber)
        alert("프로필 사진이 삭제되었습니다.");
    }

    let formData = new FormData();
    let inputFile = $("#ModalFileInput");
    let file = inputFile[0].files;
    formData.append("uploadFiles", file[0]);
    changeInfoAjax.changeImg(formData, function(){
        $("#editForm").submit();
    });

});

userName.keyup(buttonOn2);
// nickNameCk.keyup(buttonOn);
userContent.keyup(buttonOn2);
eamil.keyup(buttonOn2);
