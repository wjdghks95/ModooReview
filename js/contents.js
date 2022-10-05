// viewerButton
const viewerBtns = document.querySelectorAll('.viewer-btn');
const contents = document.querySelectorAll('.category__content');

viewerBtns.forEach(viewerBtn => {
    viewerBtn.addEventListener('click', () => {
        viewerBtns.forEach(viewerBtn => {
            viewerBtn.classList.remove('active');
        })
        viewerBtn.classList.add('active');
        
        if(viewerBtns[0].classList.contains('active')) {
            contents[0].style.display = 'flex';
        } else {
            contents[0].style.display = 'none';
        }
        
        if(viewerBtns[1].classList.contains('active')) {
            contents[1].style.display = 'table';
        } else {
            contents[1].style.display = 'none';
        }

        if(viewerBtns[2].classList.contains('active')) {
            contents[2].style.display = 'flex';
        } else {
            contents[2].style.display = 'none';
        }
    })
})

// tagButton
const tagBtn = document.querySelector('.tag-btn');
const tag = document.querySelector('.tag-swiper');
tagBtn.addEventListener('click', () => {
    tagBtn.classList.toggle('active');
    
    if (tagBtn.classList.contains('active')) {
        tag.style.display = "block";
        tagBtn.style.position = "absolute";
    } else {
        tag.style.display = "none";
        tagBtn.style.position = "static";
    }
})
