
let joinAjaxService = (function(){


    function checkEmail(email, callback, error) {
        console.log("dbEmailCheck...........");
        $.ajax({
            url:"/user/dbEmailCheck/" + email,
            type: "get",
            dataType: "json",
            success: function (result) {
                console.log("성공입니다.");
                if(callback){
                    callback(result);
                }
            },
            error: function () {
                console.log("에러입니다.");
            }
        });
    }

    function checkNickname(nickname, callback, error) {
        console.log("dbNicknameCheck...........");
        $.ajax({
            url:"/user/dbNicknameCheck/" + nickname,
            type: "get",
            dataType: "json",
            success: function (result) {
                console.log("성공입니다.");
                if(callback){
                    callback(result);
                }
            },
            error: function () {
                console.log("에러입니다.");
            }
        });
    }

    return {checkEmail:checkEmail, checkNickname:checkNickname}

})();