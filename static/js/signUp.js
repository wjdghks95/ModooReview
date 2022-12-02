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