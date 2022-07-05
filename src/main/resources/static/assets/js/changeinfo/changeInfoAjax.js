
let changeInfoAjax = (function () {

    // 이전 비밀번호 확인
    function checkOldPw(param, callback) {
        $.ajax({
            url:"/user/oldPwCheck",
            type: "post",
            dataType: "json",
            data: JSON.stringify(param),
            contentType: "application/json",
            success: function (result) {
                if(callback){
                    callback(result);
                }
            }
        });
    }

    // 회원탈퇴시 비밀번호 확인
    function checkWithdrawPw(param, callback) {
        $.ajax({
            url:"/user/withdrawCheck",
            type: "post",
            dataType: "json",
            data: JSON.stringify(param),
            contentType: "application/json",
            success: function (result) {
                if(callback){
                    callback(result);
                }
            }
        });
    }

    // 회원탈퇴 버튼 클릭시
    function withdrawUser(userNumber, callback){
        $.ajax({
            url: "/user/withdraw/" + userNumber,
            type: "delete",
            success: function(result){
                if(callback){
                    callback(result);
                }
            }
        });
    }

    // 비밀번호 변경 버튼 클릭시
    function modifyPw(param) {
        console.log(param.newPassword + "$newPassword");
        $.ajax({
            url: "/user/modifyPw/" + param.userNumber + "/" + param.newPassword,
            type: "patch"
        });
    }

    // 프로필 편집 - 닉네임 중복 검사
    function profileEditNicknameCheck(nickname, callback) {
        console.log("profileEditNicknameCheck...........");
        $.ajax({
            url:"/user/profileEditNicknameCheck/" + nickname,
            type: "get",
            async : false,
            success: function (result) {
                console.log("+++++++++++++++++++++++++++++++++++");
                console.log(result)
                console.log("+++++++++++++++++++++++++++++++++++");
                if(callback){
                    callback(result);
                }
            }
        });
    }

    function changeImg(formData, callback, error){
        $.ajax({
            url : "/user/changeImg",
            type : "post",
            data : formData,
            contentType : false,
            processData : false,
            success : function(){
                if(callback) { callback(); }
            },
            error : function(xhr, status, er){
                if(error){ error(er); }
            }
        })
    }

    function deleteImg(userNumber){
        $.ajax({
            url: "/user/profileImageDelete/" + userNumber,
            type: "get"
        })
    }

    return {
        checkOldPw: checkOldPw,
        checkWithdrawPw: checkWithdrawPw,
        withdrawUser: withdrawUser,
        modifyPw: modifyPw,
        profileEditNicknameCheck: profileEditNicknameCheck,
        changeImg : changeImg, deleteImg: deleteImg
    }

})();