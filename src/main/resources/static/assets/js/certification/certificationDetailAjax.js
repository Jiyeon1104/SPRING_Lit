// let reviewNumber = [[${report.reviewNumber}]];
// let userNumber = [[${user.userNumber}]];

// Ajax area
let reviewDetailService = (function () {

    // 신고하기
    function addReport(report, callback, error) {
        $.ajax({
            url: "/litUp/report",
            type: "post",
            data: JSON.stringify(report),
            contentType: "application/json",
            success: function (result) {
                if(callback){
                    callback(result);
                }
            },
            error: function(xhr, status, er){
                if(error){
                    error(xhr, status, er);
                }
            }
        })
    }

    //댓글 작성
    function addReply(reply, callback, error) {
        $.ajax({
            url:"/litUp/reply",
            type:"post",
            data:JSON.stringify(reply),
            contentType: "application/json",
            success:function (result) {
                if(callback){
                    callback(result)
                }
            },
            error: function (xhr, status, er) {
                if (error){
                    error(xhr,status,er)
                }
            }
        })
    }

    // 좋아요 추가
    function addLike(like, callback, error) {
        $.ajax({
            url:"/litUp/like",
            type:"post",
            data:JSON.stringify(like),
            contentType: "application/json",
            success:function (result) {
                if(callback){
                    callback(result)
                }
            },
            error: function (xhr, status, er) {
                if (error){
                    error(xhr,status,er)
                }
            }
        })

    }

    // 좋아요 취소
    function removeLike(like, callback, error) {
        $.ajax({
            url:"/litUp/removeLike",
            type:"post",
            data:JSON.stringify(like),
            contentType: "application/json",
            success:function (result) {
                if(callback){
                    callback(result)
                }
            },
            error: function (xhr, status, er) {
                if (error){
                    error(xhr,status,er)
                }
            }
        })

    }

    // 좋아요 전체 갯수 가져오기
    function getLikeTotal(reviewNumber, callback) {
        $.ajax({
            url:"/litUp/getLikeTotal/" + parseInt(reviewNumber),
            type:"get",
            success:function (result) {
                if(callback){
                    callback(result)
                }
            }
        })
    }

    //댓글 목록
    function getList(param, callback, error){
        let page = param.page || 1;
        $.getJSON("/litUp/reply/" + parseInt(param.reviewNum) + "/" + page, function(replyPageDTO){
            if(callback){
                callback(replyPageDTO.total, replyPageDTO.list);
            }
        }).fail(function(xhr, status, er){
            if(error){
                error(er);
            }
        });
    }

    // 댓글 삭제
    function removeReply(replyNumber,userNumber, callback) {
        $.ajax({
            url:"/litUp/delete/" + parseInt(replyNumber)+ "/" +parseInt(userNumber),
            type:"get",
            success:function (result) {
                if(callback){
                    callback(result)
                }
            }
        })
    }

    //댓글 작성 시간 처리
    function getReplyDate(replyDate){
        let today = new Date();
        let rDate = new Date(replyDate);
        let gap = today.getTime() - rDate.getTime();

        if(gap < 1000 * 60 * 60 * 24){
            let h = rDate.getHours();
            let m = rDate.getMinutes();

            return [(h < 10 ? '0' : '') + h, (m < 10 ? '0' : '') + m].join(":");
        }else{
            let y = rDate.getFullYear();
            let m = rDate.getMonth() + 1;
            let d = rDate.getDate();

            return [y, (m < 10 ? '0' : '') + m, (d < 10 ? '0' : '') + d].join("-")
        }
    }

    // 리뷰 디테일 조회
    function readDetail(reviewNumber, callback) {
        $.ajax({
            url:"/litUp/read/" + parseInt(reviewNumber),
            type:"get",
            success:function (reviewVO) {
                if(callback){
                    callback(reviewVO.nickname, reviewVO.content, reviewVO.registerDate)
                }
            }
        })
    }

    function getCheckLike(param,callback) {
        $.ajax({
            url:"/litUp/like/" + param.userNumber + "/" +param.reviewNumber,
            type:"get",
            success:function (result) {
                if(callback){
                    callback(result)
                }
            }
        })

    }




    return{addReport:addReport, addReply:addReply, addLike:addLike, removeLike:removeLike, getList:getList, removeReply:removeReply, getLikeTotal:getLikeTotal, getReplyDate:getReplyDate, readDetail:readDetail, getCheckLike:getCheckLike};
})();


