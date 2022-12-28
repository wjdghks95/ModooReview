// scroll paging
let currentPage = 0;

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

    if(isBottom) {
        currentPage++;
        getList(currentPage);
    }
})

function getList(currentPage) {
    const xhr = new XMLHttpRequest();
    const tagName = new URL(window.location.href).searchParams.get('tagName');
    xhr.open("GET", `/api/contents/hashTag?tagName=${tagName}&page=${currentPage}`, true);
    xhr.send();
    xhr.onload = (data) => {
        const contentList = document.querySelector('.content__list');
        const response = data.target.response;

        const parser = new DOMParser();
        const contentItems = parser.parseFromString(response, 'text/html').body.childNodes;

        contentItems.forEach(contentItem => {
            contentList.append(contentItem);
        });
    }
}