function initFormCancelRequest(parentForm) {
    //alert('initFormCancelRequest');
    let modal = parentForm.querySelector('.modal');
    let form = parentForm.querySelector('.form#cancelRequest');
    //alert(modal.className);
    let isChange = false;

    if (form) {

        modal.querySelector('.modal-content').style.width = '400px';

        let btnClose = modal.querySelector('#close');
        let btnOK = modal.querySelector('#ok');
        let btnCancel = modal.querySelector('#cancel');

        form.setAttribute('data-display', 'block');
        modal.setAttribute('data-display', 'block');

        //Инициализация кнопок
        btnClose.addEventListener(
            'click',
            close,
            false
        );
        btnOK.addEventListener(
            'click',
            function () {
                alert('Сохранение ещё не реализовано');
            },
            false
        );
        btnCancel.addEventListener(
            'click',
            function () {
                form.remove();
            },
            false
        );
    }
}
