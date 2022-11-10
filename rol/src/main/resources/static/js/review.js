// follow
const followBtn = document.querySelector('.detail-review__author-follow-button');
if(followBtn !== null) {
    followBtn.addEventListener('click', () => {

        if(followBtn.classList.contains('anonymous')) {
            alert('팔로우는 로그인 후 이용가능합니다.');
        } else {
            const id = followBtn.getAttribute("data-index"); // review id

            const xhr = new XMLHttpRequest();
            xhr.open("GET", `/member/follow/${id}`, true);
            xhr.send();
            xhr.onload = (data) => {
                const followImg = followBtn.querySelector('.follow-btn');

                followImg.classList.toggle('active');

                if (followImg.classList.contains('active')) {
                    followImg.innerHTML = '<svg xmlns="http://www.w3.org/2000/svg" viewBox="0 0 512 512" style="width:10px; height:10px;"><!--! Font Awesome Pro 6.2.0 by @fontawesome - https://fontawesome.com License - https://fontawesome.com/license (Commercial License) Copyright 2022 Fonticons, Inc. --><path d="M470.6 105.4c12.5 12.5 12.5 32.8 0 45.3l-256 256c-12.5 12.5-32.8 12.5-45.3 0l-128-128c-12.5-12.5-12.5-32.8 0-45.3s32.8-12.5 45.3 0L192 338.7 425.4 105.4c12.5-12.5 32.8-12.5 45.3 0z"/></svg> 팔로잉';
                } else {
                    followImg.innerHTML = '+ 팔로우';
                }
            }
        }
    })
}

// like
const likeBtn = document.querySelector('.detail-review__like-button');
likeBtn.addEventListener('click', () => {
    if(likeBtn.classList.contains('anonymous')) {
        alert('좋아요는 로그인 후에 이용가능합니다.');
    } else {
        const id = likeBtn.getAttribute("data-id");

        const xhr = new XMLHttpRequest();
        xhr.open("GET", `/api/review/like/${id}`, true);
        xhr.send();
        xhr.onload = (data) => {
            const likeImg = likeBtn.querySelector('.like-btn');
            likeImg.classList.toggle('active');

            const count = data.target.response; // Like Api response
            const likeCount = document.querySelector('.detail-review__like-count');
            likeCount.innerHTML = count;
        }
    }
});