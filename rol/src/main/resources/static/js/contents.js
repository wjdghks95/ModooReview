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

    const xhr = new XMLHttpRequest();
    const url = `/contents?category=${categoryName}&page=${curPage}`;
    xhr.open("GET", "/api" + url, true);
    xhr.send();
    xhr.onload = (data) => {
        const content = document.querySelector("#content");
        content.innerHTML = data.target.response;
        history.pushState(null, null, url);

        viewerBtns = document.querySelectorAll('.viewer-btn');
        contents = document.querySelectorAll('.category__content');
        view(viewerBtns, contents);
    }
}