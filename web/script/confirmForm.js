function initFormConfirm(parentForm, text, ok) {
    //alert('initFormConfirm');
    let form = parentForm.querySelector('.modal#confirm');
    if (form) {

        form.querySelector('.modal-content').style.width = '350px';

        btnClose = form.querySelector('#close');
        btnOk = form.querySelector('#ok');
        btnCancel = form.querySelector('#cancel');

        form.querySelector('.container').innerHTML = text;

        form.setAttribute('data-display', 'block');

        //Инициализация кнопок
        btnClose.addEventListener(
            'click',
            function () {
                form.parentNode.removeChild(form);
            },
            false
        );
        btnOk.addEventListener(
            'click',
            function () {
                form.parentNode.removeChild(form);
                ok();
            },
            false
        );
        btnCancel.addEventListener(
            'click',
            function () {
                form.parentNode.removeChild(form);
            },
            false
        );
    }
}