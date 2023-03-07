// 휴대폰 인증
let code = "";
let isPhoneChk = false;

$('.sign-up-modal__sms-button').on('click', function() {
    const phoneNum = $('#user-phone').val();

    if (phoneValidator(phoneNum)) {
        alert("인증번호가 발송되었습니다.");
        $.ajax({
            // 요청 시작 부분
            type: "GET", // 전송 타입
            url: "/api/SMS", //주소
            data: {"phoneNum": phoneNum}, // 파라미터

            // 응답 확인 부분
            success: function(data) {
                $('.sign-up-modal__sms-button').children().attr('disabled', true);
                $('#user-phone').attr('readonly', true);
                $('.sign-up-modal__phoneChk').show();
                code = data;
            },

            // 응답 실패
            error: function() {
                console.log("ajax 통신 실패");
            }
        });
    } else {
        alert("유효하지 않는 전화번호입니다.");
    }
})

function phoneValidator(phoneNum) {
    let patternPhone = new RegExp("01[016789][^0][0-9]{2,3}[0-9]{4}$");
    if(!patternPhone.test(phoneNum)) {
        return false;
    }
    return true;
}

$('.sign-up-modal__phoneChk-button').on('click', function() {
    const phoneChkNum = $('#user-phoneChk').val();
    phoneChk(phoneChkNum);
})

function phoneChk(phoneChkNum) {
    if (phoneChkNum === code) {
        alert("인증이 완료되었습니다.");

        $('#user-phoneChk').attr("disabled", true);
        $('.sign-up-modal__resend-sms-button').children().attr("disabled", true);
        isPhoneChk = true;
    } else {
        alert("인증번호가 올바르지 않습니다. 다시 확인해 주세요.");
        $('#user-phoneChk').focus();
        isPhoneChk = false;
    }
}

$('.sign-up-modal__resend-sms-button').on('click', function() {
    $('.sign-up-modal__sms-button').click();
})

// 이메일 주소 선택
$('#user-email, #user-email-addr').on('blur', function() {
    addUpEmail();
})

$('#user-email-addr-list').on('change', function() {
    const selectedValue = $("#user-email-addr-list option:selected").val();
    if (selectedValue === "input") {
        $('#user-email-addr').show().val("").focus();
    } else {
        $('#user-email-addr').hide().val(selectedValue);
        addUpEmail();
    }
})

function addUpEmail() {
    if($('#user-email').val() !== "" && $('#user-email-addr').val() !== "") {
        $('#user-total-email').val($('#user-email').val() + "@" + $('#user-email-addr').val());
    } else {
        $('#user-total-email').val("");
    }
};

// 비밀번호 확인
let isPwdChk = false;

$('#user-password, #user-passwordChk').on('keyup', function() {
    if($('#user-password').val() !== "" && $('#user-passwordChk').val() !== "") {
        if($('#user-password').val() !== $('#user-passwordChk').val()) {
            $('.sign-up-modal__pwdChk-error-msg').text('비밀번호가 일치하지 않습니다').addClass('error-msg');
            isPwdChk = false;
        } else {
            $('.sign-up-modal__pwdChk-error-msg').text('비밀번호가 일치합니다.').removeClass('error-msg');
            isPwdChk = true;
        }
    } else {
        $('.sign-up-modal__pwdChk-error-msg').text('').removeClass('error-msg');
        isPwdChk = false;
    }
})

// Submit
$('.sign-up-modal__submit-button').on('click', function() {
    if(!isPhoneChk) {
        alert('휴대폰 인증을 완료해주세요.');
        return;
    }

    if(!isPwdChk) { // 패스워드 일치 확인
        alert('비밀번호를 확인해주세요.');
        return;
    }

    $('.sign-up-modal__form').submit();
})

// 주소 검색
$('.sign-up-modal__search-addr-button').on('click', function() {
    execDaumPostcode();
})

// 테마 커스텀
var themeObj = {
    searchBgColor: "#0C9EE8",
    queryTextColor: "#FFFFFF",
};

// 팝업 객체 생성
function execDaumPostcode() {
    new daum.Postcode({
        theme: themeObj,
        oncomplete: function(data) {
            var addr = '';

            // 사용자가 도로명 주소를 선택했을 경우
            if (data.userSelectedType === 'R') { 
                addr = data.roadAddress;
            } else {
                // 사용자가 지번 주소를 선택했을 경우(J)
                addr = data.jibunAddress;
            }

            $('#user-zipcode').val(data.zonecode);
            $('#user-addr').val(addr);
            $('#user-detail-addr').focus();
        }
    }).open();
}