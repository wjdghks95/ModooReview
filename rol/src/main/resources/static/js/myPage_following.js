// follow
const followBtns = document.querySelectorAll('.following-content__following-button');
if(followBtns.length !== 0) {
    followBtns.forEach(followBtn => {
        followBtn.addEventListener('click', () => {
            const id = followBtn.getAttribute("data-index");

             const xhr = new XMLHttpRequest();
             xhr.open("GET", `/member/follow/${id}`, true); // follower id
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
        })
    })
}