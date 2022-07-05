

let myPageAjaxService = (function () {

    function getMedal(userNumber, callback){
        console.log("getMedal................");
        $.getJSON("/user/getMedal/" + userNumber, function(medals){
            if(callback){
                callback(medals);
            }
        });
    }

    //모달 - 팔로우 삭제
    function removeFollower(followerNumber, followingNumber, callback) {
        console.log("removeFollower..........");
        $.ajax({
            url:"/user/removeFollower/" + followerNumber + "/" + followingNumber,
            type: "delete",
            success: function (result) {
                console.log("삭제 성공");
                if(callback){
                    callback(result);
                }
            }
        });
    }



    function insertFollowing(param, callback) {
        $.ajax({
            url:"/user/follow",
            type:"post" ,
            data: JSON.stringify(param),
            contentType:"application/json",
            success:function (result) {
                if(callback){
                    callback()
                }
            }
        })

    }

    function get2Medal(userNumber, callback) {
        console.log("get2Medal..........");
        $.ajax({
            url:"/user/get2Medal/" + userNumber,
            type: "get",
            success: function (result) {
                if(callback){
                    callback(result);
                }
            }
        });
    }

    function get3Medal(userNumber, callback) {
        console.log("get3Medal..........");
        $.ajax({
            url:"/user/get3Medal/" + userNumber,
            type: "get",
            success: function (result) {
                if(callback){
                    callback(result);
                }
            }
        });
    }

    function get4Medal(userNumber, callback) {
        console.log("get4Medal..........");
        $.ajax({
            url:"/user/get4Medal/" + userNumber,
            type: "get",
            success: function (result) {
                if(callback){
                    callback(result);
                }
            }
        });
    }

    function get4MedalPercent(userNumber, callback) {
        console.log("get4Medal..........");
        $.ajax({
            url:"/user/get4MedalPercent/" + userNumber,
            type: "get",
            success: function (result) {
                if(callback){
                    callback(result);
                }
            }
        });
    }

    function get5Medal(userNumber, callback) {
        console.log("get5Medal..........");
        $.ajax({
            url:"/user/get5Medal/" + userNumber,
            type: "get",
            success: function (result) {
                if(callback){
                    callback(result);
                }
            }
        });
    }

    function get6Medal(userNumber, callback) {
        console.log("get6Medal..........");
        $.ajax({
            url:"/user/get6Medal/" + userNumber,
            type: "get",
            success: function (result) {
                if(callback){
                    callback(result);
                }
            }
        });
    }

    function get7Medal(param, callback) {
        console.log("get7Medal..........");
        $.ajax({
            url:"/user/get7Medal/" + param.userNumber + "/" + param.category,
            type: "get",
            success: function (result) {
                if(callback){
                    callback(result);
                }
            }
        });
    }

    function get8Medal(param, callback) {
        console.log("get8Medal..........");
        $.ajax({
            url:"/user/get8Medal/" + param.userNumber + "/" + param.category,
            type: "get",
            success: function (result) {
                console.log("ajax 콜백함수 들어옴")
                if(callback){
                    callback(result);
                }
            }
        });
    }

    function get9Medal(param, callback) {
        console.log("get9Medal..........");
        $.ajax({
            url:"/user/get9Medal/" + param.userNumber + "/" + param.category,
            type: "get",
            success: function (result) {
                if(callback){
                    callback(result);
                }
            }
        });
    }

    function get10Medal(param, callback) {
        console.log("get10Medal..........");
        $.ajax({
            url:"/user/get10Medal/" + param.userNumber + "/" + param.category,
            type: "get",
            success: function (result) {
                if(callback){
                    callback(result);
                }
            }
        });
    }

    function get11Medal(param, callback) {
        console.log("get11Medal..........");
        $.ajax({
            url:"/user/get11Medal/" + param.userNumber + "/" + param.category,
            type: "get",
            success: function (result) {
                if(callback){
                    callback(result);
                }
            }
        });
    }

    function get12Medal(userNumber, callback) {
        console.log("get12Medal..........");
        $.ajax({
            url:"/user/get12Medal/" + userNumber,
            type: "get",
            success: function (result) {
                if(callback){
                    callback(result);
                }
            }
        });
    }

    function get13Medal(userNumber, callback) {
        console.log("get13Medal..........");
        $.ajax({
            url:"/user/get13Medal/" + userNumber,
            type: "get",
            success: function (result) {
                if(callback){
                    callback(result);
                }
            }
        });
    }

    function get14Medal(userNumber, callback) {
        console.log("get14Medal..........");
        $.ajax({
            url:"/user/get14Medal/" + userNumber,
            type: "get",
            success: function (result) {
                if(callback){
                    callback(result);
                }
            }
        });
    }

    function get15Medal(userNumber, callback) {
        console.log("get15Medal..........");
        $.ajax({
            url:"/user/get15Medal/" + userNumber,
            type: "get",
            success: function (result) {
                if(callback){
                    callback(result);
                }
            }
        });
    }

    //litUp(리뷰) 리스트
    function litUpList(userPageNumber, listInfo, callback, error){

        $.ajax({
            url : "/litUp/getMyList/"+userPageNumber,
            type : "post",
            data: JSON.stringify(listInfo),
            contentType: "application/json",
            dataType : "json",
            success : function(result){
                if(callback){ callback(result); }
            },
            error : function(xhr, status, er){
                if(error) { error(er); }
            }
        })
    }

    //lit(프로젝트) 리스트
    function litList(userPageNumber, listInfo, callback, error){
        $.ajax({
            url : "/lit/getMyList/"+userPageNumber,
            type : "post",
            data: JSON.stringify(listInfo),
            contentType: "application/json",
            dataType : "json",
            success : function(result){
                if(callback){ callback(result); }
            },
            error : function(xhr, status, er){
                if(error) { error(er); }
            }
        })
    }

    function getMyProfileImg(userNumber,callback){
        $.getJSON("/litUp/profilePic" , {userNumber: userNumber}, function(pic){
            if(callback){callback(pic);
            }
        });

    }


    return {removeFollower: removeFollower, getMedal: getMedal, get4Medal: get4Medal,
        get5Medal: get5Medal, get8Medal: get8Medal, get9Medal: get9Medal, get10Medal: get10Medal,
        get11Medal: get11Medal, get7Medal: get7Medal, get6Medal: get6Medal, get12Medal: get12Medal,
        get13Medal: get13Medal, get14Medal: get14Medal, get15Medal: get15Medal, litUpList:litUpList,
        litList:litList, getMyProfileImg:getMyProfileImg, insertFollowing:insertFollowing,
        get2Medal: get2Medal, get3Medal: get3Medal, get4MedalPercent: get4MedalPercent
    }

})();