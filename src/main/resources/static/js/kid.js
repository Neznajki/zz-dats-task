$(() => {
    $('.familySelect').each(function () {
        let familyId = $(this).data('family-id');
        $(this).val(familyId ? familyId : '');
    });

    $('.confirmFamilyButton').on('click', function () {
        let $select = $(this).prev();

        let currentFamily = $select.data('family-id')?$select.data('family-id'):'-1';

        if ($select.val() == currentFamily) {
            alert('izvēlaties citu ģimeni');
            return;
        }

        $.ajax("kid/family/" + $select.data('id') + '/' + $select.val(), {method: "put"})
            .done(() => {
                $select.data('family-id', $select.val())
            });
    });
});