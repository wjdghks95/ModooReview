// 최신순, 인기순
$('.switch-input').on('click', function() {
    if(this.value === "latest") {
        $('.latest').show();
        $('.hot').hide();
        $('.switch-selection').css("left", "0");
    } else {
        $('.hot').show();
        $('.latest').hide();
        $('.switch-selection').css("left", "85px");
    }
    
    $('.switch-label').removeClass("checked");
    $('.switch-input:checked + .switch-label').addClass("checked");
})