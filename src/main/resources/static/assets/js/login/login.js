const loginBtn = $('#login_btn');
const email = $('#email');
const passwordValue = $('#pwVal');
var filter = /^([\w-\.]+)@((\[[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\.)|(([\w-]+\.)+))([a-zA-Z]{2,4}|[0-9]{1,3})(\]?)$/;

const loginBtnF = function (e) {

    if(passwordValue.val().length >= 6 && filter.test(email.val())){
        loginBtn.attr("class", "buttonforjoin formEvent");
        loginBtn.attr("disabled", false);
    } else {
        loginBtn.attr("class", "buttonforjoin jonbuttoncolor");
        loginBtn.attr("disabled", true);
    }
}

email.keyup(loginBtnF)
passwordValue.keyup(loginBtnF)

