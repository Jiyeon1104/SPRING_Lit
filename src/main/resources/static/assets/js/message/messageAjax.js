let messageService = (function () {

    function send(message, callback, error) {
        $.ajax({
            url: "/message/send",
            type: "post",
            data: JSON.stringify(message),
            contentType: "application/json",
            dataType: "json",

            // 하나씩 저장할 때
            success: function (result) {
                if (callback) {
                    callback(result)
                }
            }
        });
    }

    function getFollowerList(userNumber, callback, error) {
        $.ajax({
            url: "/message/getFollowerList/" + userNumber,
            type: "get",
            dataType: "json",
            success: function (result) {
                if (callback) {
                    callback(result);
                }
            }
        });
    }

    function searchFollowerList(param, callback, error){
        $.ajax({
            url: "/message/searchFollower/" + param.keyword + "/" + param.userNumber,
            type: "get",
            dataType: "json",
            success: function (result) {
                if (callback) {
                    callback(result);
                }
            }
        });
    }

    function getMessageList(param, callback, error){
        $.ajax({
            url: "/message/getMessageList/ " + param.sendUserNumber + "/" + param.receiveUserNumber + "/" + param.pageNum,
            type: "get",
            dataType: "json",
            contentType: "application/json",
            success: function(result){
                if(callback){
                    callback(result);
                }
            }
        });
    }

    function getRoomId(param, error){
        let roomId;
        $.ajax({
            url: "/message/getRoomId/ " + param.sendUserNumber + "/" + param.receiveUserNumber,
            type: "get",
            async: false,
            success: function(result){
                roomId = result;
            }
        });
        return roomId;
    }

    function getProfileImage(userNumber, callback, error){
        let img;
        $.ajax({
            url: "/message/getUserProfileImage/" + userNumber,
            type: "get",
            async: false,
            success: function(result){
                img = result;
            }
        });
        return img;
    }


    return {send: send, getFollowerList: getFollowerList, searchFollowerList:searchFollowerList, getMessageList:getMessageList, getRoomId:getRoomId, getProfileImage:getProfileImage};
})();