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
    }
};

// 비밀번호 확인
let isPwdChk = false;

$('#user-password, #user-passwordChk').on('keyup', function() {
    if($('#user-password').val() !== $('#user-passwordChk').val()) {
        $('.sign-up-modal__pwdChk-error-msg').text('비밀번호가 일치하지 않습니다').addClass('error-msg');
        isPwdChk = false;
    } else {
        $('.sign-up-modal__pwdChk-error-msg').text('비밀번호가 일치합니다.').removeClass('error-msg');
        isPwdChk = true;
    }
})

// Submit
$('.sign-up-modal__submit-button').on('click', function() {
    if(!isPwdChk) { // 패스워드 일치 확인
        alert('비밀번호를 확인해주세요.');
        return;
    }

    $('.sign-up-modal__form').submit();
})