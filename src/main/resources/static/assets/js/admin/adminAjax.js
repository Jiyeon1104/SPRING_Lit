console.log("Admin Module......");

let adminService = (function(){
    console.log("adminSevice");
    
    //유저 찾기
    function searchUser(searchInfo, callback, error){
        let page = searchInfo.page || 1;
        $.ajax({
            url: "/admin/searchUser/" + page,
            type: "post",
            data: JSON.stringify(searchInfo),
            contentType: "application/json",
            dataType: "json",
            success: function(result){
                if(callback){
                    callback(result);
                }
            },
            error: function(xhr, status, er) {
                if(error){
                    error(er);
                }
            }
        });
    }

    //유저 삭제
    function deleteUser(list, callback, error){
        $.ajax({
            url: "/admin/user/" + list,
            type : "delete",
            success: function () {
                if(callback){
                    console.log("삭제 완료")
                    callback();
                }
            },
            error: function(xhr, status, er){
                if(error){
                    error(er);
                }
            }
        })
    }

    //프로젝트 찾기 (대기, 승인)
    function searchProject(searchInfo, callback, error) {
        // console.log(searchInfo);
        let page = searchInfo.page || 1;
        $.ajax({
            url: "/admin/project/search/" + page,
            type : "post",
            data : JSON.stringify(searchInfo),
            contentType: "application/json",
            dataType: "json",
            success : function (result) {
                if(callback){
                    callback(result);
                }
            },
            error : function (xhr, status, er) {
                if(error){
                    error(er);
                }
            }
        })

    }

    //프로젝트 삭제
    function deleteProject(list, callback, error){
        $.ajax({
            url: "/admin/project/" + list,
            type : "delete",
            success: function () {
                if(callback){
                    console.log("삭제 완료")
                    callback();
                }
            },
            error: function(xhr, status, er){
                if(error){
                    error(er);
                }
            }
        })
    }

    //프로젝트 상태 변경
    function changeStatus(info, callback, error) {
        $.ajax({
            url : "/admin/project/" + info.projectNumber + "/" + info.status,
            type : "get",
            success: function () {
                if(callback){
                    callback();
                }
            },
            error: function(xhr, status, er){
                if(error){
                    error(er);
                }
            }
        })

    }

    //리뷰 검색
    function searchReview(searchInfo, callback, error){
        let page = searchInfo.page || 1;
        $.ajax({
            url : "/admin/review/search/" + page,
            type : "post",
            data : JSON.stringify(searchInfo),
            contentType : "application/json",
            dataType : "json",
            success : function(result) {
                if(callback){ callback(result); }
            },
            error : function(xhr, status, er){
                if(error){ error(er); }
            }
        })
    }

    //리뷰 삭제
    function deleteReview(list, callback, error){
        $.ajax({
            url: "/admin/review/"+list,
            type : "delete",
            success: function () {
                if(callback){
                    console.log("삭제 완료")
                    callback();
                }
            },
            error: function(xhr, status, er){
                if(error){
                    error(er);
                }
            }
        })
    }

    //리포트 검색
    function searchReport(searchInfo, callback, error){
        let page = searchInfo.page || 1;
        $.ajax({
            url : "/admin/report/search/" + page,
            type : "post",
            data : JSON.stringify(searchInfo),
            contentType : "application/json",
            dataType : "json",
            success : function(result) {
                if(callback) { callback(result); }
            },
            error : function(xhr, status, er) {
                if(error) { error(er); }
            }
        })
    }

    //리포트 삭제
    function deleteReport(list, callback, error){
        $.ajax({
            url : "/admin/report/"+list,
            type : "delete",
            success : function(){
                if(callback){ callback(); }
            },
            error : function(xhr, status, er) {
                if(error) { error(er); }
            }
        })
    }

    //신고 취소
    function cancelReport(list, callback, error){
        $.ajax({
            url : "/admin/report/cancel/"+list,
            type : "delete",
            success : function(){
                if(callback){ callback(); }
            },
            error : function(xhr, status, er) {
                if(error) { error(er); }
            }
        })
    }


    return {
        searchUser:searchUser,
        deleteUser:deleteUser,
        searchProject:searchProject,
        deleteProject:deleteProject,
        changeStatus:changeStatus,
        searchReview:searchReview,
        deleteReview:deleteReview,
        searchReport:searchReport,
        deleteReport:deleteReport,
        cancelReport:cancelReport
    };
})();

let chartService = (function(){
    console.log("chartService");

    function userChartData(callback, error){
        $.ajax({
            url : "/admin/user/chart",
            type : "get",
            dataType : "json",
            async : false,
            success : function(result){
                if(callback) { callback(result); }
            },
            error : function (xhr, status, er) {
                if(error) { error(er); }
            }
        });
    }

    function reviewChartData(callback, error){
        $.ajax({
            url : "/admin/review/chart",
            type : "get",
            dataType : "json",
            async : false,
            success : function(result){
                if(callback) { callback(result); }
            },
            error : function (xhr, status, er) {
                if(error) { error(er); }
            }
        });
    }

    function reportChartData(callback, error){
        $.ajax({
            url : "/admin/report/chart",
            type : "get",
            dataType : "json",
            async : false,
            success : function(result){
                if(callback) { callback(result); }
            },
            error : function (xhr, status, er) {
                if(error) { error(er); }
            }
        });
    }

    return {
        userChartData:userChartData,
        reviewChartData:reviewChartData,
        reportChartData:reportChartData
    }
})();