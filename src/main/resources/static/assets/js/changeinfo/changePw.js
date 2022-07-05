let $oldPw = $('#cppOldPassword');
let $newPw = $('#cppNewPassword');
let $newPwCk = $('#cppCheckPassword');

// 비밀번호 변경 버튼 활성화/비활성화
function buttonOn(oldCk){

    let newCk = $newPw.val() == $newPwCk.val();

    if(oldCk && newCk){
        $('#subBtn').attr("class", "buttonOn");
        $('#subBtn').attr("disabled", false);
    } else{
        $('#subBtn').attr("disabled", true);
    }
}

$('#subBtn').on("click", function(){
    alert("비밀번호 변경이 완료 되었습니다.");
});

// 새 비밀번호 확인 유효성 검사

$newPw.on("keyup", function () {
    if($newPw.val() != $newPwCk.val()){ // 새 비밀번호 / 비밀번호 확인
        $ ('._checkArea3').html('비밀번호가 일치하지 않습니다.');
        $('._checkArea3').css('color', 'red');
    } else if($newPw.val().length <= 0 && $newPwCk.val().length <= 0) {
        $ ('._checkArea3').html(' ');
    } else{
        $('._checkArea3').html('비밀번호가 일치합니다.');
        $('._checkArea3').css('color', 'black');
        if($oldPw.val().length > 0) {
            buttonOn(true);
        }
    }
});

$newPwCk.on("keyup", function () {
    if($newPw.val() != $newPwCk.val()){ // 새 비밀번호 / 비밀번호 확인
        $ ('._checkArea3').html('비밀번호가 일치하지 않습니다.');
        $('._checkArea3').css('color', 'red');
    } else if($newPw.val().length <= 0 && $newPwCk.val().length <= 0) {
        $ ('._checkArea3').html(' ');
    } else{
        $('._checkArea3').html('비밀번호가 일치합니다.');
        $('._checkArea3').css('color', 'black');
        if($oldPw.val().length > 0) {
            buttonOn(true);
        }
    }
});


function ckeckOldPw(result) {

    if($oldPw.val().length <= 0){
        $('._checkArea1').html(' ');
    }

    if(!result){ // 현재 비밀번호 유효성 검사
        $ ('._checkArea1').html('이전 비밀번호와 일치하지 않습니다.');
        $('._checkArea1').css('color', 'red');
            buttonOn(false);
    } else{
        $('._checkArea1').html('이전 비밀번호와 일치합니다.');
        $('._checkArea1').css('color', 'black');
        if($newPw.val().length > 0) {
            buttonOn(true);
        }
    }
}

// oldPw.keyup(buttonOn);
// $newPw.keyup(buttonOn);
// $newPwCk.keyup(buttonOn);
