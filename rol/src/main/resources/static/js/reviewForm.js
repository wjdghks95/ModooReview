// rating
const ratingStars = document.querySelectorAll('.rating__star');
const ratingScore = document.querySelector(".rating__score");
let totalStar = 0;

ratingStars.forEach((star, index) => {
    star.dataset.rating = index + 1;
    star.addEventListener('mouseover', e => {
        onMouseOver(e);
    });
    star.addEventListener('click', e => {
        onClick(e);
    });
    star.addEventListener('mouseleave', e => {
        onMouseLeave(e);
    });
});

function fill(ratingVal) {
    for (let i = 0; i < 5; i++) {
        if (i < ratingVal) {
            ratingStars[i].querySelector('img').setAttribute('src', '../icon/star-solid.svg');
        } else {
            ratingStars[i].querySelector('img').setAttribute('src', '../icon/star-regular.svg');
        }
    }
}

function onMouseOver(e) {
    const ratingVal = e.currentTarget.dataset.rating;
    if (!ratingVal) {
        return;
    } else {
        fill(ratingVal);
    }
}

function onMouseLeave(e) {
    fill(totalStar);
}

function onClick(e) {
    const ratingVal = e.currentTarget.dataset.rating;
    totalStar = ratingVal;
    ratingScore.value = totalStar;
}

// tag
let tag = {};
let counter = 0;

// 입력한 값을 태그로 생성
function addTag (value) {
    tag[counter] = value;
    counter++; // remove-btn 의 고유 id
}

// tag 안에 있는 값을 array type 으로 만들어서 넘김
function marginTag () {
    return Object.values(tag).filter(word => {
        return word !== "";
    });
}

const tagInput = document.querySelector("#review-tag");
const tagList = document.querySelector(".tag__list");
const tagAddBtn = document.querySelector(".tag-in__add-button");

tagAddBtn.addEventListener('click', () => {
    createTag();
})

tagInput.addEventListener("keypress", (e) => {
    //엔터나 스페이스바 눌렀을때 실행
    if (e.key === "Enter" || e.keyCode == 32) {
        tagAddBtn.click();
        e.preventDefault(); // SpaceBar 시 빈공간이 생기지 않도록 방지
    }
})

function createTag() {
    if (tagList.childElementCount >= 10) {
            alert('태그는 10개까지만 추가할 수 있습니다.');
    } else {
        let tagVal = tagInput.value; // 값 가져오기

         // 해시태그 값 없으면 실행X
        if (tagVal !== "") {
            // 같은 태그가 있는지 검사, 있다면 해당값이 array 로 return
            let result = Object.values(tag).filter(word => {
                return word === tagVal;
            });

            // 해시태그가 중복되었는지 확인
            if (result.length == 0) {
                const tagItem = document.createElement("li");
                tagItem.setAttribute("class", "tag__item");
                tagItem.innerHTML = "<span>#" + tagVal + "</span><button type='button' class='remove-btn' data-index='" + counter + "'>x</button>";
                tagList.appendChild(tagItem);
                tagInput.value = "";
                tagInput.focus();

                const tagRemoveBtn = document.querySelector(`.remove-btn[data-index="${counter}"]`);
                tagRemoveBtn.addEventListener('click', () => {
                    let index = tagRemoveBtn.getAttribute("data-index");
                    tag[index] = "";
                    tagRemoveBtn.parentElement.remove();
                })
                addTag(tagVal);
            } else {
                alert("태그값이 중복됩니다.");
            }
        }
    }
}

// submitBtn
const submitBtn = document.querySelector('.review__submit-button');
const reviewForm = document.querySelector('.review__form');

submitBtn.addEventListener('click', () => {
    let tagsInput = document.querySelector("#review-tags");
    let tagArr = marginTag(); // return array
    tagsInput.value = tagArr;
    reviewForm.submit();
});

// imgUpload
const uploadBtn = document.querySelector('.review__photo');
const fileInput = document.querySelector('.review__photo input');
const wrapper = document.querySelector(".review__photo .swiper-wrapper");
const nextBtn = document.querySelector(".review__photo .swiper-button-next");
const prevBtn = document.querySelector(".review__photo .swiper-button-prev");

uploadBtn.addEventListener('click', (e) => {
    if (e.target == nextBtn || e.target == prevBtn) {
        return;
    }
    fileInput.click();
    fileInput.addEventListener('change', (e) => getImageFile(e));
})

function getImageFile(e) {
    const uploadFiles = [];
    const files = e.currentTarget.files;
    const count = [...files].length;

    if (count == 0) {
        return;
    }

    if (count > 10) {
        alert('이미지는 최대 10개까지 업로드가 가능합니다.');
        return;
    }

    [...files].forEach(file => {
        if (!file.type.match("image/.*")) {
            alert('이미지 파일만 업로드가 가능합니다.');
            return;
        }
    });

    init();

    [...files].forEach(file => {
        uploadFiles.push(file);
        const reader = new FileReader();
        reader.onload = (e) => {
            const photo = createPhoto(e, file);
            wrapper.appendChild(photo);
        }
        reader.readAsDataURL(file);
    })

    const uploadInfo = document.createElement('p');
    let fileName;
    if (count == 1) {
        [...files].forEach(file => {
            fileName = `해당 사진을 썸네일로 등록합니다. (${file.name})`;
        })
    } else {
            fileName = `해당 사진을 썸네일로 등록합니다. (업로드할 사진 수 ${count}개)`;
    }
    uploadInfo.innerHTML = fileName;
    uploadBtn.appendChild(uploadInfo);
}


function init() {
    const photoBtn = document.querySelector('.photo-btn');
    if (photoBtn != null) {
        photoBtn.remove();
    }

    nextBtn.style.display = 'block';
    prevBtn.style.display = 'block';

    if (wrapper.hasChildNodes) {
        wrapper.querySelectorAll('.swiper-slide').forEach(slide => {
            slide.remove();
        })
    }

    uploadBtn.childNodes.forEach(child  => {
        if (child.tagName == 'P') {
            child.remove();
        }
    })
}

function createPhoto(e, file) {
    const photo = createElement('div', 'class', 'swiper-slide');
    const img = createElement('img', 'src', e.target.result);
    img.setAttribute('data-file', file.name);

    photo.appendChild(img);

    return photo;
}

function createElement(e, attr, attrName) {
    const element = document.createElement(e);
    element.setAttribute(attr, attrName);
    return element;
}