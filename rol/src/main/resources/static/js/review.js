// follow
const followBtn = document.querySelector('.detail-review__author-follow-button');

followBtn.addEventListener('click', () => {
    const button = followBtn.querySelector('button');
    button.classList.toggle('default-btn--white');

    if (button.classList.contains('default-btn--white')) {
        button.innerHTML = '<svg xmlns="http://www.w3.org/2000/svg" viewBox="0 0 512 512" style="width:10px; height:10px;"><!--! Font Awesome Pro 6.2.0 by @fontawesome - https://fontawesome.com License - https://fontawesome.com/license (Commercial License) Copyright 2022 Fonticons, Inc. --><path d="M470.6 105.4c12.5 12.5 12.5 32.8 0 45.3l-256 256c-12.5 12.5-32.8 12.5-45.3 0l-128-128c-12.5-12.5-12.5-32.8 0-45.3s32.8-12.5 45.3 0L192 338.7 425.4 105.4c12.5-12.5 32.8-12.5 45.3 0z"/></svg> 팔로잉';
    } else {
        button.innerHTML = '+ 팔로우';
    }
})

// like
let likeVal = document.querySelector('.like-check').value;
let likeImg = document.querySelector('.like-btn');

if (likeVal > 0) {
    likeImg.classList.add('active');
} else {
    likeImg.classList.remove('active');
}

const likeBtn = document.querySelector('.detail-review__like-button');
likeBtn.addEventListener('click', () => {
    const xhr = new XMLHttpRequest();
    //xhr.open("GET", "/review/like", true);
});