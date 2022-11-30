// 콘텐츠 뷰
$('.content__view').on('click', function() {
    const index = $(this).index();
    $('.content__boards').hide();
    $('.content__boards').eq(index).show();

    $('.view-btn').removeClass('active');
    $('.view-btn').eq(index).addClass('active');
})