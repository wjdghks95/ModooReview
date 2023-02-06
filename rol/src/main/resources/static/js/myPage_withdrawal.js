var header = $("meta[name='_csrf_header']").attr('content');
var token = $("meta[name='_csrf']").attr('content');

$("#withdrawalBtn").click(function() {
    if($("#checkbox1").is(":checked") && $("#checkbox2").is(":checked")) {
        const id = $("input[name='id']").val();
        const pwd = $("input[name='pwdChk']").val();

        if(pwd === "" || !pwd) {
            alert("비밀번호를 입력하세요");
            return;
        } else {
            $.ajax({
                url: "/myPage/" + id + "/withdrawal",
                beforeSend: function(xhr){
                    xhr.setRequestHeader(header, token);
                },
                method: "POST",
                data: {pwd: pwd},
                success: function(response) {
                    if(response) {
                        alert("회원탈퇴가 완료되었습니다.");
                        location.href = "/";
                    } else {
                        alert("비밀번호가 일치하지 않습니다.");
                        location.href =  "/myPage/" + id + "/withdrawal";
                    }
                }
            })
        }
    } else {
        alert("안내 사항에 모두 동의해야합니다.");
    }
})