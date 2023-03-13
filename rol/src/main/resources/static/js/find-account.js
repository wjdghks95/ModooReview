// 휴대폰 인증
let code = "";
let isPhoneChk = false;

$('.check-phone-number__send-btn').on('click', function() {
    const phoneNum = $('.check-phone-number__phone-number').val();

    if (phoneValidator(phoneNum)) {
        alert("인증번호가 발송되었습니다.");
        $(".check-phone-number__message").text("");
        $.ajax({
            url: "/api/SMS",
            method: "GET", 
            data: {"phoneNum": phoneNum},
            success: function(data) {
                $('.check-phone-number__send-btn').attr('disabled', true);
                $('.check-phone-number__phone-number').attr('readonly', true);
                $('.check-auth-number__auth-number').attr('disabled', false);
                $('.check-auth-number__btn').attr('disabled', false);
                code = data;
            }
        });
    }
})

function phoneValidator(phoneNum) {
    if(phoneNum === "" || !phoneNum) {
        $(".check-phone-number__message").text("휴대폰 번호를 입력하세요");
        return false;
    } else {
        let patternPhone = new RegExp("01[016789][^0][0-9]{2,3}[0-9]{4}$");
        if(!patternPhone.test(phoneNum)) {
            $(".check-phone-number__message").text("유효하지 않은 휴대폰 번호입니다");
            return false;
        }
        return true;
    }
}

$('.check-auth-number__btn').on('click', function() {
    const phoneChkNum = $('.check-auth-number__auth-number').val();
    phoneChk(phoneChkNum);
})

function phoneChk(phoneChkNum) {
    if (phoneChkNum === code) {
        $(".check-auth-number__message").text("인증성공");
        $(".check-auth-number__message").css("color", "green");
        $('.check-auth-number__auth-number').attr("disabled", true);
        $(".find-account__find-button > button").attr("disabled", false);
    } else {
        $(".check-auth-number__message").text("인증번호가 올바르지 않습니다.");
        $('.check-auth-number__auth-number').focus();
    }
}