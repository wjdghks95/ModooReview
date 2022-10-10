// 핸드폰 인증
const sendBtn = document.querySelector(".sign-up-model__send-button");
let code = "";
sendBtn.addEventListener("click", () => {

    const phone = document.querySelector("#user-phone");
    const phoneNumber = phone.value;

    if (telValidator(phoneNumber)) {
        alert("인증번호가 발송되었습니다.");

        const xhr = new XMLHttpRequest();
        xhr.open("GET", "/check/sendSMS?phone=" + phoneNumber, true);
        xhr.send();
        xhr.onload = (data) => {
            phone.setAttribute("readonly", true);
            sendBtn.querySelector("button").setAttribute("disabled", true);
            const phoneCheck = document.querySelector("#phone-check");
            phoneCheck.style.display = "flex";
            code = data.currentTarget.response;
        }
    }
})

const phoneCheckBtn = document.querySelector(".sign-up-model__phone-check-button");
const resendBtn = document.querySelector(".sign-up-model__resend-button");
phoneCheckBtn.addEventListener("click", () => {
    const phoneCheckNumber = document.querySelector("#phone-check-number");
    
    if (phoneCheckNumber.value == code) {
        alert("인증이 완료되었습니다.");
        phoneCheckNumber.setAttribute("disabled", true);
        resendBtn.querySelector("button").setAttribute("disabled", true);
        document.querySelector("#phone-double-check").value = "true";
    } else {
        alert("인증번호가 올바르지 않습니다. 다시 확인해 주세요.");
        phoneCheckNumber.focus();
    }
})

resendBtn.addEventListener("click", () => {
    sendBtn.click();
})


function telValidator(phoneNumber) {

    let patternPhone = new RegExp("01[016789][^0][0-9]{2,3}[0-9]{4}$");

    if(!patternPhone.test(phoneNumber)) {
        alert("유효하지 않는 전화번호입니다.");
        return false;
    }
    return true;
}

// 이메일 주소 선택
const select = document.querySelector("select");
const email = document.querySelector('.email-hidden');

select.addEventListener('change', () => {
    if (select.options[select.selectedIndex].value == "input") {
        email.style.display = 'block';
    } else {
        email.style.display = 'none';
    }
    
})