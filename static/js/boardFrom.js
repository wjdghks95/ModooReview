// 이미지 등록
$('.write__photo-button, .write__photo-container').on('click', function(e) {
    if ($(e.target).get(0) === $('.arrow-btn--prev').get(0) ||
    $(e.target).get(0) === $('.arrow-btn--next').get(0)) {
        return;
    } else {
        $("input[type='file']").click();
    }
})

$("input[type='file']").on('change', (e) => {
    init();
    getImageFile(e);
})

function getImageFile(e) {
    const uploadFiles = [];
    const files = e.currentTarget.files;
    const count = [...files].length;

    if (count === 0) {
        return;
    }

    if (count > 5) {
        alert('이미지는 최대 5개까지 업로드가 가능합니다.');
        $("input[type='file']").val("");
        return;
    }

    [...files].forEach(file => {
        if (!file.type.match("image/.*")) {
            alert('이미지 파일만 업로드가 가능합니다.');
            return;
        }
    });

    [...files].forEach(file => {
        uploadFiles.push(file);
        const reader = new FileReader();
        reader.onload = (e) => {
            $('.write__photo-button').hide();
            $('.write__photo-container').show();
            const photo = createPhoto(e, file);
            $('.write__photo-list').append(photo);
            $('.slides').css('width', $('.slide').length * 460 + 'px');
        }
        reader.readAsDataURL(file);
    });

    $('.write__photo-container').after(createMsg());
}

function createPhoto(e, file) {
    return $(`<li class="write__photo slide"><img src="${e.target.result}" 
        data-file="${file.name}" alt="썸네일"></li>`);
}

function createMsg() {
    return $(`<p class="write__uploadMsg">슬라이드를 넘겨 썸네일을 선택하세요.</p>`)
}

function init() {
    $('.write__photo-button').show();
    $('.write__photo-container').hide();
    $('.write__photo').remove();
    $('.write__uploadMsg').remove();
    $('.write__photo-error-msg').remove();

    currentIdx = 0;
    $('.slides').css('left', 0 + 'px');
}

// 이미지 슬라이드
let currentIdx = 0;

function moveSlide(num) {
    $('.slides').css('left', -num * 460 + 'px');
    currentIdx = num;
}

$('.arrow-btn--next').on('click', function() {
    let slideCount = $('.slide').length;
    if(currentIdx < slideCount - 1) {
        moveSlide(currentIdx + 1);
    } else {
        return;
    }
})

$('.arrow-btn--prev').on('click', function() {
    if(currentIdx > 0) {
        moveSlide(currentIdx - 1);
    } else {
        return;
    }
})

// Submit
$('.write__submit-button').on('click', function() {
    $('input[name=thumbnailIdx]').val(currentIdx); // thumbnailIdx 값 설정

    $('.write__form').submit(); // form 전송
})