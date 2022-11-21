const latestContent = document.querySelector('.latest__content');
const hotContent = document.querySelector('.hot__content');

function check(button) {
    if(button.checked) {
        if(button.value === "latest") {
            latestContent.style.display = "block";
            hotContent.style.display = "none";
        }

        if(button.value === "popularity") {
            hotContent.style.display = "block";
            latestContent.style.display = "none";
        }
    }
}
