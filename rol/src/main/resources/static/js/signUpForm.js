// 핸드폰 인증
const sendSMSBtn = document.querySelector(".sign-up-form__send-sms-button");
let code = "";
sendSMSBtn.addEventListener("click", () => {

    const phone = document.querySelector("#user-phone");
    const phoneNumber = phone.value;

    if (telValidator(phoneNumber)) {
        alert("인증번호가 발송되었습니다.");

        const xhr = new XMLHttpRequest();
        xhr.open("GET", "/check/sendSMS?phone=" + phoneNumber, true);
        xhr.send();
        xhr.onload = (data) => {
            phone.setAttribute("readonly", true);
            sendSMSBtn.querySelector("button").setAttribute("disabled", true);

            const phoneCheckArea = document.querySelector(".sign-up-form__phone-check-area");
            phoneCheckArea.style.display = "flex";

            code = data.currentTarget.response;
        }
    }
})

function telValidator(phoneNumber) {

    let patternPhone = new RegExp("01[016789][^0][0-9]{2,3}[0-9]{4}$");

    if(!patternPhone.test(phoneNumber)) {
        alert("유효하지 않는 전화번호입니다.");
        return false;
    }
    return true;
}

const phoneCheckBtn = document.querySelector(".sign-up-form__phone-check-button");
const resendSMSBtn = document.querySelector(".sign-up-form__resend-sms-button");
let phoneCheck = document.querySelector("#user-phone-check");

phoneCheckBtn.addEventListener("click", () => {
    const phoneCheckNumber = document.querySelector("#phone-check-number");

    if (phoneCheckNumber.value === code) {
        alert("인증이 완료되었습니다.");
        phoneCheckNumber.setAttribute("disabled", true);
        resendSMSBtn.querySelector("button").setAttribute("disabled", true);
        phoneCheck.value = "true";
    } else {
        alert("인증번호가 올바르지 않습니다. 다시 확인해 주세요.");
        phoneCheckNumber.focus();
    }
})

resendSMSBtn.addEventListener("click", () => {
    sendSMSBtn.click();
})

// 이메일 주소 선택
const email = document.querySelector("#user-email");
const emailAddress = document.querySelector("#user-email-address");
const emailAddressList = document.querySelector("#user-email-address-list");
const totalEmail = document.querySelector("#user-total-email");

email.addEventListener("blur", () => addUpEmail());
emailAddress.addEventListener("blur", () => addUpEmail());

emailAddressList.addEventListener('change', () => {
    const selectedValue = emailAddressList.options[emailAddressList.selectedIndex].value;

    if (selectedValue === "type") {
        emailAddress.style.display = 'block';
        emailAddress.value = "";
        emailAddress.focus();
    } else {
        emailAddress.style.display = 'none';
        emailAddress.value = selectedValue;
        addUpEmail();
    }
})

function addUpEmail() {
    if(email.value !== "" && emailAddress.value !== "") {
        totalEmail.value = email.value + "@" + emailAddress.value;
        localStorage.setItem("email", email.value);
        localStorage.setItem("emailAddress", emailAddress.value);
    }
};

(function loadEmail() {

    if (totalEmail.value === "") {
        localStorage.clear();
    }

    const savedEmail = localStorage.getItem("email");
    const savedEmailAddress = localStorage.getItem("emailAddress");

    if(savedEmail !== null && savedEmailAddress !== null) {
        email.value = savedEmail;
        for(let i = 0; i < emailAddressList.options.length; i++) {
            if (emailAddressList.options[i].value !== savedEmailAddress) {
                emailAddress.style.display = "block";
                emailAddress.value = savedEmailAddress;
            } else {
                emailAddress.style.display = "none";
                emailAddress.value = savedEmailAddress;
                emailAddressList.value = savedEmailAddress;
                break;
            }
        }
    }
})();

// 비밀번호 확인
const password = document.querySelector("#user-password");
const passwordConfirm = document.querySelector("#user-password-confirm");
let passwordCheck = document.querySelector("#user-password-check");

password.addEventListener("blur", () => passConfirm());
passwordConfirm.addEventListener("blur", () => passConfirm());

function passConfirm() {
    if(password.value !== "" && passwordConfirm.value !== "") {
        if(password.value === passwordConfirm.value){
            passwordCheck.value = "true";
        }else{
            passwordCheck.value = "false";
        }
    } else {
        passwordCheck.value = "false";
    }
}

// 주소 검색
const searchAddressBtn = document.querySelector(".sign-up-form__search-address-button");
searchAddressBtn.addEventListener("click", () => execDaumPostcode());

var themeObj = {
    searchBgColor: "#0C9EE8", //검색창 배경색
    queryTextColor: "#FFFFFF", //검색창 글자색
 };

function execDaumPostcode() {
    new daum.Postcode({
        theme: themeObj,
        oncomplete: function(data) {
            // 팝업에서 검색결과 항목을 클릭했을때 실행할 코드를 작성하는 부분.

            // 각 주소의 노출 규칙에 따라 주소를 조합한다.
            // 내려오는 변수가 값이 없는 경우엔 공백('')값을 가지므로, 이를 참고하여 분기 한다.
            var addr = ''; // 주소 변수
            var extraAddr = ''; // 참고항목 변수

            //사용자가 선택한 주소 타입에 따라 해당 주소 값을 가져온다.
            if (data.userSelectedType === 'R') { // 사용자가 도로명 주소를 선택했을 경우
                addr = data.roadAddress;
            } else { // 사용자가 지번 주소를 선택했을 경우(J)
                addr = data.jibunAddress;
            }

            // 우편번호와 주소 정보를 해당 필드에 넣는다.
            document.getElementById("user-zipcode").value = data.zonecode;
            document.getElementById("user-address").value = addr;
            // 커서를 상세주소 필드로 이동한다.
            document.getElementById("user-detail-address").focus();
        }
    }).open();
}

const submitBtn = document.querySelector(".sign-up-form__submit-button");
submitBtn.addEventListener("click", () => {
    document.querySelector(".sign-up-form__form").submit();
})