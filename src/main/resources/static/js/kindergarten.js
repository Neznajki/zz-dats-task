$(() => {
    $('.removeFromQueue').on('click', function () {
        $.ajax('', {method: "delete", data: {'kidId': $(this).data('kidid')}}).done(() => {
            $(this).parent().remove();
        })
    });
});