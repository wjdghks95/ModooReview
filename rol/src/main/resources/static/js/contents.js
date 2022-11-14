// viewerButton
let viewerBtns = document.querySelectorAll('.viewer-btn');
let contents = document.querySelectorAll('.category__content');

viewerBtns.forEach(viewerBtn => {
    viewerBtn.addEventListener('click', () => {
        viewerBtns.forEach(viewerBtn => {
            viewerBtn.classList.remove('active');
        })
        viewerBtn.classList.add('active');

        view(viewerBtns, contents);
    })
})

// category
const category = document.querySelector(".category__list");

category.addEventListener("click", (e) => {
    const target = e.target.tagName === "BUTTON" ? e.target : null;

    if(target !== null) {
        category.querySelectorAll('.category-btn').forEach(categoryBtn => {
            categoryBtn.classList.remove("active");
        })
        target.classList.add("active");
    }
})

// content view
function view(viewerBtns, contents) {
    for(let i=0; i<viewerBtns.length; i++) {

        if(viewerBtns[i].classList.contains('active')) {
            switch(i) {
                case 0: contents[i].style.display = 'flex';
                break;
                case 1: contents[i].style.display = 'table';
                break;
                case 2: contents[i].style.display = 'flex';
                break;
                default: break;
            }
        } else {
            contents[i].style.display = 'none';
        }
    }
}

// pagination
function paging(curPage, categoryVal) {
    const getCategory = new URL(window.location.href).searchParams.get("category");
    const categoryParam = getCategory !== null ? getCategory : "all";

    const categoryName = !categoryVal ? categoryParam : categoryVal;

    const getKeyword = new URL(window.location.href).searchParams.get("keyword");
    let keyword = getKeyword !== null ? getKeyword : "";
    
    const url = `/contents?category=${categoryName}&page=${curPage}&keyword=${keyword}`;

    drawContent(url);
    history.pushState(null, null, url);
}

function drawContent(url) {
    const xhr = new XMLHttpRequest();
    xhr.open("GET", "/api" + url, true);
    xhr.send();
    xhr.onload = (data) => {
        const content = document.querySelector("#content");
        content.innerHTML = data.target.response;

        viewerBtns = document.querySelectorAll('.viewer-btn');
        contents = document.querySelectorAll('.category__content');
        view(viewerBtns, contents);
    }
}

// popstate
window.addEventListener('popstate', () => {
    const href = location.href;
    const beginIndex = href.indexOf("/contents");
    const url = href.substring(beginIndex);
    
    drawContent(url);

    const categoryVal = new URL(href).searchParams.get("category");

    category.querySelectorAll('.category-btn').forEach(categoryBtn => {
        categoryBtn.classList.remove("active");

        if (categoryBtn.value === categoryVal) {
            categoryBtn.classList.add("active");
        }

        if (categoryVal === null) {
            categoryBtn.value === 'all' ? categoryBtn.classList.add("active") : categoryBtn.classList.remove("active");
        }
    })
})


