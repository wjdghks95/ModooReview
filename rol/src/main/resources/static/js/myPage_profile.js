// 프로필 사진 변경
$('.profile__image').on('click', function() {
    $('input[name="multipartFile"]').click();
})

$('input[name="multipartFile"]').on('change', function() {
    $('.profile__img-form').submit();
})

// 프로필 닉네임 변경
$('.modify-btn').on('click', function() {
    $(this).hide();
    $('.profile__nickname').hide();
    $('.profile__nick-form, .update-btn, .cancel-btn').show();
})

$('.cancel-btn').on('click', function() {
    $('.modify-btn').show();
    $('.profile__nickname').show();
    $('.profile__nick-form, .update-btn, .cancel-btn').hide();
})