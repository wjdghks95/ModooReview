// scroll paging
let currentPage = 0;
let isLoading = false;

function getScrollTop() {
    return (window.pageYOffset !== undefined) ? window.pageYOffset : (document.documentElement || document.body.parentNode || document.body).scrollTop;
};

function getDocumentHeight() {
    const body = document.body;
    const html = document.documentElement;

    return Math.max(
        body.scrollHeight, body.offsetHeight,
        html.clientHeight, html.scrollHeight, html.offsetHeight
    );
};

function getFooterHeight() {
    const footer = document.querySelector('footer');
    return footer.offsetHeight;
}

window.addEventListener('scroll', () => {
    let isBottom = getScrollTop() + window.innerHeight >= getDocumentHeight() - getFooterHeight();

    if (currentPage === getTotalPage() || isLoading) {
        return;
    }

    if(isBottom) {
        currentPage++;
        isLoading = true;
        getList(currentPage);
    }
})

function getList(currentPage) {
    const tagName = new URL(window.location.href).searchParams.get('tagName');
    $.ajax({
        url: "/contents/hashTag/loadBoardList",
        method: "GET",
        data: {
            page: currentPage,
            tagName: tagName
        },
        success: function(response) {
            $(".content__list").append(response);
            isLoading = false;
        },
        error: function() {
            console.log("ajax 통신 실패");
        }
    })
}