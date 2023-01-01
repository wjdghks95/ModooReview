// 좋아요
$('.board__like-button').on('click', function() {

    if($(this).hasClass('anonymous')) {
        alert('좋아요 서비스는 로그인 후 이용가능합니다.');
    } else {
        const likeBtn = $(this).children();
        const href = location.href;
        const id = href.slice(-1);

        $.ajax({
            url: "/like.do",
            method: "GET",
            data: {"id" : id},
            success: function(result) {
                likeBtn.toggleClass('active');
                $('.board__like-count').text(result);
            }, 
            error: function() {
                console.log("ajax 통신 실패");
            }
        })
    }

})

// 팔로우
$('.board__follow-button').on('click', function() {

    if($(this).hasClass('anonymous')) {
        alert('팔로우 서비스는 로그인 후 이용가능합니다.');
    } else {
        const followBtn = $(this).children();
        const href = location.href;
        const id = href.slice(-1);

        $.ajax({
            url: "/follow.do",
            method: "GET",
            data: {"id" : id},
            success: function() {
                follow(followBtn);
            }, 
            error: function() {
                console.log("ajax 통신 실패");
            }
        })
    }
})

function follow(followBtn) {
    $(followBtn).toggleClass('active');

    if ($(followBtn).hasClass('active')) {
        $(followBtn).html('<svg xmlns="http://www.w3.org/2000/svg" viewBox="0 0 512 512" style="width:10px; height:10px;"><!--! Font Awesome Pro 6.2.0 by @fontawesome - https://fontawesome.com License - https://fontawesome.com/license (Commercial License) Copyright 2022 Fonticons, Inc. --><path d="M470.6 105.4c12.5 12.5 12.5 32.8 0 45.3l-256 256c-12.5 12.5-32.8 12.5-45.3 0l-128-128c-12.5-12.5-12.5-32.8 0-45.3s32.8-12.5 45.3 0L192 338.7 425.4 105.4c12.5-12.5 32.8-12.5 45.3 0z"/></svg> 팔로잉');
    } else {
        $(followBtn).html('+ 팔로우');
    }
}

// 댓글 작성
$('.comments__submit-button').on('click', function() {
    addComment();
})

$('.comments__form').on('keydown', function(e) {
    if(e.code === "Enter") {
        e.preventDefault();
        addComment();
    }
})

function addComment() {
    var token = $("meta[name='_csrf']").attr("content");
    var header = $("meta[name='_csrf_header']").attr("content");

    const href = location.href;
    const id = href.slice(-1);
    const content = $('.comments__input').val();

    if(content === "" || content === null) {
        $(".error-msg").text('댓글을 입력해주세요.');
    } else {
        $(".error-msg").text('');
        $.ajax({
            url: "/comment.do",
            method: "POST",
            data: {"id" : id,
                    "content" : content},
            beforeSend : function(xhr){
                    xhr.setRequestHeader(header, token);
            },
            success: function(result) {
                $('#commentList').replaceWith(result);
            },
            error: function() {
                console.log("ajax 통신 실패");
            }
        })
        $('.comments__input').val("");
        $('.comments__input').focus();
    }
}

// 댓글 삭제
$(document).on('click', '.comments__del-button', function() {
    if(confirm('댓글을 삭제하시겠습니까?')) {
        const href = location.href;
        const id = href.slice(-1);
        const index = $(this).parents('.comments__item').attr('data-idx');

        $.ajax({
            url: "/comment.de",
            method: "GET",
            data: {"id" : id,
                    "index" : index},
            success: function(result) {
                $('#commentList').replaceWith(result);
            },
            error: function() {
                console.log("ajax 통신 실패");
            }
        })
    }
})