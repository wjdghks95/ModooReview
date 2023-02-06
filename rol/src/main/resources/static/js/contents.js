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

// 카테고리 선택
$('.category-btn').on('click', function() {
    $('.category-btn').removeClass('active');
    $(this).addClass('active');

    const category = $(this).val();
    const keyword = new URL(location.href).searchParams.get("keyword") !== null ?
            new URL(location.href).searchParams.get("keyword") : "";

    ajaxContents(category, keyword, 0);
})

// pagination
function paging(curPage) {

    const category = new URL(location.href).searchParams.get("category") !== null ?
        new URL(location.href).searchParams.get("category") : "";
    const keyword = new URL(location.href).searchParams.get("keyword") !== null ?
        new URL(location.href).searchParams.get("keyword") : "";

    ajaxContents(category, keyword, curPage);
}

$(".search-form__submit").on("click", function () {
    searchContents();
})

$(".search-form__input").on("keyup", function (e) {
    if (e.keyCode === 13) {
        searchContents();
    }
})

function searchContents() {
    const keyword = $('.search-form__input').val();
    ajaxContents("", keyword, 0);

    $('.search-form__input').val("");
    $('.search-form__input').focus();

    $('.category-btn').removeClass('active');
    $('.category-btn').eq(0).addClass("active");
}

function ajaxContents(category, keyword, curPage) {
    $.ajax({
        url: "/contents/loadContents",
        method: "GET",
        data: {
            category: category,
            keyword: keyword,
            page: curPage
        },
        success: function (result) {
            history.pushState(null, null, `/contents?category=${category}&keyword=${keyword}&page=${curPage}`);
            $('#contents').replaceWith(result);

            $(".view-btn").each(function(index, item) {
                if($(item).hasClass("active")) {
                    $(".content__boards").eq(index).show();
                    $(".content__boards:not(:eq(" + index + "))").hide();
                }
            })
        },
        error: function () {
            console.log("ajax 통신 실패");
        }
    });
}

// popstate
$(window).on('popstate', () => {
    const params = new URL(location.href).searchParams;

    $.ajax({
        url: "/contents/loadContents",
        method: "GET",
        data: {
            category: params.get("category"),
            keyword: params.get("keyword"),
            page: params.get("page")
        },
        success: function (result) {
            $('#contents').replaceWith(result);

            $('.category-btn').removeClass('active');
            const category = new URL(location.href).searchParams.get("category");
            $('.category-btn').each(function(index, item) {
                if ($(item).val() === category) {
                    $(item).addClass("active");
                }

                if (category === null) {
                    $('.category-btn').eq(0).addClass("active");
                }
            })

            $(".view-btn").each(function(index, item) {
                if($(item).hasClass("active")) {
                    $(".content__boards").eq(index).show();
                    $(".content__boards:not(:eq(" + index + "))").hide();
                }
            })
        },
        error: function () {
            console.log("ajax 통신 실패");
        }
    });
})