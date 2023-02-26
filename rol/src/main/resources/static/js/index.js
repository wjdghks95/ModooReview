// 최신순, 인기순
$('.switch-input').on('click', function() {
    if(this.value === "latest") {
        $('.latest').show();
        $('.hot').hide();
        $('.switch-selection').css("left", "0");
    } else {
        $('.hot').show();
        $('.latest').hide();
        $('.switch-selection').css("left", "85px");
    }
    
    $('.switch-label').removeClass("checked");
    $('.switch-input:checked + .switch-label').addClass("checked");
})

// 베스트 리뷰 슬라이드
const swiper = new Swiper('.swiper', {
    effect: "fade", // fade 효과 적용
    speed: 1000, // 슬라이드 속도
	fadeEffect: {
		crossFade: true, // crossFade 활성화
	},
    loop: true, // 반복

    autoplay: {
        delay: 3000, // 자동 재생
        disableOnInteraction: false,
    },

    pagination: {
        el: '.swiper-pagination',
        clickable: true, // 클릭 가능여부
    }
});