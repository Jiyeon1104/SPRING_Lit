$("#id, #pw").on("keyup keydown", function () {
    let $button = $(".btn");

    if ($("#id").val() && $("#pw").val()) {
        $button.removeClass("background__red-none");
        $button.addClass("background__red");
    } else {
        $button.removeClass("background__red");
        $button.addClass("background__red-none");
    }
});

$(".btn").on("click", function(e){
    e.preventDefault();

    if(!$(this).hasClass("background__red-none")){
        $(".login-form").submit();
    }
})
