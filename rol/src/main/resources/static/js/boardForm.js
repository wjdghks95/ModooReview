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

    // 이미지 파일의 갯수를 체크
    if (count === 0) {
        return;
    }
    if (count > 5) {
        alert('이미지는 최대 5개까지 업로드가 가능합니다.');
        $("input[type='file']").val("");
        return;
    }

    // 이미지 파일인지 체크
    [...files].forEach(file => {
        if (!file.type.match("image/.*")) {
            alert('이미지 파일만 업로드가 가능합니다.');
            return;
        }
    });

    [...files].forEach(file => {
        uploadFiles.push(file);
        const reader = new FileReader();
        reader.onload = (e) => { // 읽기 동작이 성공적으로 완료되었을 경우
            $('.write__photo-button').hide();
            $('.write__photo-container').show();
            const photo = createPhoto(e, file); // 이미지 생성
            $('.write__photo-list').append(photo);
            $('.slides').css('width', $('.slide').length * 460 + 'px');
        }
        reader.readAsDataURL(file);  // 바이너리 파일을 Base64 Encode 문자열로 반환 Ex) data:image/jpeg; base64, GDYG….
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

// Rating
let totalStar = 0; // 서버에 넘겨줄 최종 별점
$('.rating__star').each(function(index) {
    $(this).data('rating', index + 1); // 각각의 별 요소에 rating data로 index 를 설정
})

$('.rating__star').on({
    mouseover: function(e) {
        onMouseOver(e); // 마우스가 영역에 들어올 경우 별점 채우기
    },
    mouseleave: function() {
        onMouseLeave(); // 마우스가 영역을 벗어날 경우 별점 빼기
    },
    click: function(e) {
        onClick(e); // 마우스를 클릭한 경우 별점 채우기
    }
})

function onMouseOver(e) {
    const ratingVal = $(e.currentTarget).data('rating');
    if (!ratingVal) {
        return;
    } else {
        fill(ratingVal);
    }
}

function onMouseLeave() {
    fill(totalStar);
}

function onClick(e) {
    const ratingVal = $(e.currentTarget).data('rating');
    totalStar = ratingVal;
    $('.rating__score').val(totalStar);
}

function fill(ratingVal) {
    $('.rating__star').each(function(index) {
        if(index < ratingVal) { // 전달받은 rating data 에서 -1 에 해당하는 index 만큼 색상 변경
            $('.rating__star > img').eq(index).css('filter', 'invert(82%) sepia(68%) saturate(5238%) hue-rotate(358deg) brightness(104%) contrast(102%)');
        } else {
            $('.rating__star > img').eq(index).css('filter', 'invert(100%) sepia(0%) saturate(4078%) hue-rotate(293deg) brightness(81%) contrast(117%)');
        }
    })
}

// 태그
let tag = {}; // 서버로 전송한 태그를 배열로 생성
let counter = 0; // 각 태그에 대한 id

$('.write__tag-add-button').on('click', function() {
    createTag();
});

$('#board-tag').on('keypress', function(e) {
    if (e.key === "Enter" || e.keyCode == 32) {
        $('.write__tag-add-button').click();
        e.preventDefault();
    }
})

function createTag() {
    // 태그 갯수 확인
    if ($('.write__tags').children().length >= 5) {
        alert('태그는 5개까지만 추가할 수 있습니다.');
    } else {
        let tagVal = $('#board-tag').val();

        // 값이 비어있는지 확인
        if (tagVal !== "") {
            let result = Object.values(tag).filter(word => { // 중복 검사
                return word === tagVal;
            });

            if (result.length === 0) {
                const tag = $(`<li class="write__tag">` + 
                                `<span class="write__tag-text">#${tagVal}</span>` +
                                `<button type='button' class='remove-btn' data-index='${counter}'>x</button>` + 
                            `</li>`);
                $('.write__tags').append(tag);
                $('#board-tag').val("");
                $('#board-tag').focus();

                addTag(tagVal);
            } else {
                alert("태그값이 중복됩니다.");
            }
        }
    }
}

// 배열에 태그값 추가
function addTag(value) {
    tag[counter] = value;
    counter++;
}

// 태그 삭제
$(document).on('click', '.remove-btn', function() { // document 에 이벤트를 적용하고 선택자 지정
    const index = $(this).data('index');
    tag[index] = "";
    $(this).parent().remove();
})

// 배열에 있는 공백 제거
function marginTag () {
    return Object.values(tag).filter(word => {
        return word !== "";
    });
}

// 태그 값 전송
function sendTagVal() {
    let tagArr = marginTag();
    $('#board-tags').val(tagArr);
}

// 기존에 태그가 존재하는 경우
$('.write__tag').each(function(index, obj) {
    const text = $(obj).children('.write__tag-text').text();
    const tagVal = text.substring(text.indexOf('#')+1);
    $(obj).children('button').attr('data-index', counter);
    addTag(tagVal);
})

// Submit
$('.write__submit-button').on('click', function() {
    $('input[name=thumbnailIdx]').val(currentIdx); // thumbnailIdx 값 설정
    sendTagVal(); // 태그 값 설정

    $('.write__form').submit(); // form 전송
})

