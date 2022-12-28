const modifyBtn = document.querySelector('.modify-btn');
modifyBtn.addEventListener('click', () => {
    const nickname = document.querySelector('.profile-info__nickname');
    const nicknameForm = document.querySelector('.profile-info__nickname-form');
    const updateBtn = document.querySelector('.update-btn');
    const cancelBtn = document.querySelector('.cancel-btn');

    nickname.style.display = 'none';
    nicknameForm.style.display = 'block';
    modifyBtn.style.display = 'none';
    updateBtn.style.display = 'block';
    cancelBtn.style.display = 'block';

    cancelBtn.addEventListener('click', () => {
        nickname.style.display = 'block';
        nicknameForm.style.display = 'none';
        modifyBtn.style.display = 'block';
        updateBtn.style.display = 'none';
        cancelBtn.style.display = 'none';
    })
})

const imgBtn = document.querySelector('.profile-info__field-image');
const fileInput = imgBtn.querySelector('input[type=file]');
imgBtn.addEventListener('click', () => {
    fileInput.click();
})

fileInput.addEventListener('change', (e) => {
    const imgForm = document.querySelector('.profile-info__profileImg-form');
    imgForm.submit();
});