new Swiper('.best-review-swiper', {
    // Optional parameters
    spaceBetween: 30,
    effect: "fade",
	fadeEffect: {
		crossFade: true,
	},
    loop: true,

    autoplay: {
        delay: 2500,
        disableOnInteraction: false,
    },

    // If we need pagination
    pagination: {
        el: '.swiper-pagination',
        clickable: true,
    }
});

new Swiper(".category-swiper", {
    slidesPerView: 10,    
    touchRatio: 0,
    navigation: {
        nextEl: ".swiper-button-next",
        prevEl: ".swiper-button-prev",
    },
});

new Swiper(".photo-swiper", {
    touchRatio: 0,
    observer: true,
    observeParents: true,

    navigation: {
        nextEl: ".swiper-button-next",
        prevEl: ".swiper-button-prev",
    },
});