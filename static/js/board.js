// 좋아요
$('.board__like-button').on('click', function() {
    $(this).children().toggleClass('active');
})