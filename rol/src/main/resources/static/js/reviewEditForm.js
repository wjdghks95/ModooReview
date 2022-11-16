const categorySelect = document.querySelector('#review-category');
const value = categorySelect.getAttribute('data-type');

categorySelect.querySelectorAll('option').forEach(option => {
    if(value.toLowerCase() === option.value) {
        option.setAttribute('selected', 'selected');
    }
})

const tagRemoveBtns = document.querySelectorAll('.remove-btn');
tagRemoveBtns.forEach(tagRemoveBtn => {
    tagRemoveBtn.addEventListener('click', () => {
        tagRemoveBtn.parentElement.remove();
    })
})
