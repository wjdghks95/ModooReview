// 콘텐츠 뷰
$('.content__view').on('click', function() {
    const index = $(this).index();
    $('.content__boards').hide();
    $('.content__boards').eq(index).show();

    $('.view-btn').removeClass('active');
    $('.view-btn').eq(index).addClass('active');
})

// 카테고리 슬라이드
let currentIdx = 0;
let slideCount = $('.slide').length;

function moveSlide(num) {
    $('.slides').css('left', -num * 120 + 'px');
    currentIdx = num;
}

$('.arrow-btn--next').on('click', function() {
    if(currentIdx < slideCount - 11) {
        moveSlide(currentIdx + 1);
    } else {
        return;
    }
})

$('.arrow-btn--prev').on('click', function() {
    if(currentIdx > 0) {
        moveSlide(currentIdx - 1);
    } else {
        return;
    }
})