// rating
const ratingStars = document.querySelectorAll('.rating__star > img');
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
            ratingStars[i].setAttribute('src', '/icon/star-solid.svg');
        } else {
            ratingStars[i].setAttribute('src', '/icon/star-regular.svg');
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

const tagItems = document.querySelectorAll('.tag__item');
if (tagItems !== null) {
    tagItems.forEach(tagItem => {
        const text = tagItem.querySelector('span').innerText;
        const tagVal = text.substring(text.indexOf('#')+1);
        tagItem.querySelector('button').setAttribute('data-index', counter);;
        addTag(tagVal);
    })
}

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
    if (tagList.childElementCount >= 5) {
            alert('태그는 5개까지만 추가할 수 있습니다.');
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
                tagDelBtns = document.querySelectorAll('.remove-btn');

                deleteTag(tagDelBtns);
                addTag(tagVal);
            } else {
                alert("태그값이 중복됩니다.");
            }
        }
    }
}

let tagDelBtns = document.querySelectorAll('.remove-btn');
deleteTag(tagDelBtns);

function deleteTag(tagDelBtns) {
    tagDelBtns.forEach(tagDelBtn => {
        tagDelBtn.addEventListener('click', () => {
            let index = tagDelBtn.getAttribute("data-index");
            tag[index] = "";
            tagDelBtn.parentElement.remove();
        })
    })
}

function sendTagVal() {
    const tagsInput = document.querySelector("#review-tags");
    let tagArr = marginTag(); // return array
    tagsInput.value = tagArr;
}

// submit
const submitBtn = document.querySelector('.review__submit-button');
const reviewForm = document.querySelector('.review__form');

submitBtn.addEventListener('click', () => {
    sendTagVal(); // 태그 값 전송
    reviewForm.submit();
});

// imgUpload
const reviewPhoto = document.querySelector('.review__photo');
const filesInput = document.querySelector('input[type="file"]');
const wrapper = document.querySelector(".swiper-wrapper");
const nextBtn = document.querySelector(".swiper-button-next");
const prevBtn = document.querySelector(".swiper-button-prev");

reviewPhoto.addEventListener('click', (e) => {
    if (e.target === nextBtn || e.target === prevBtn) {
        return;
    }
    filesInput.click();
})
filesInput.addEventListener('change', (e) => getImageFile(e));

function getImageFile(e) {
    const uploadFiles = [];
    const files = e.currentTarget.files;
    const count = [...files].length;

    if (count == 0) {
        return;
    }

    if (count > 5) {
        alert('이미지는 최대 5개까지 업로드가 가능합니다.');
        filesInput.value = "";
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
    });

    const uploadInfo = document.createElement('p');
    uploadInfo.setAttribute('class', 'review__uploadInfo')
    let uploadInfoText;
    if (count == 1) {
        [...files].forEach(file => {
            uploadInfoText = `해당 사진이 썸네일로 등록됩니다. (${file.name})`;
        })
    } else {
            uploadInfoText = `첫번째 사진이 썸네일로 등록됩니다. (업로드할 사진 수 ${count}개)`;
    }
    uploadInfo.innerHTML = uploadInfoText;
    reviewPhoto.appendChild(uploadInfo);
}


function init(files) {
    const photoBtn = document.querySelector('.photo-btn');
    if (photoBtn != null) {
        photoBtn.remove();
    }

    nextBtn.style.display = 'block';
    prevBtn.style.display = 'block';

    if (wrapper.hasChildNodes()) {
        wrapper.querySelectorAll('.swiper-slide').forEach(slide => {
            slide.remove();
        })
    }

    const uploadInfo = document.querySelector('.review__uploadInfo');
    if(uploadInfo !== null) {
        uploadInfo.remove();
    }
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

// category
const categorySelect = document.querySelector('#review-category');
const value = categorySelect.getAttribute('data-type');

if (value !== null) {
    categorySelect.querySelectorAll('option').forEach(option => {
        if(value.toLowerCase() === option.value) {
            option.setAttribute('selected', 'selected');
        }
    })
}