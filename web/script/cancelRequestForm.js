function initFormCancelRequest(parentForm, requestId, initForm) {
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
        form.querySelector('[data-field="comment"]').value = "Причина по умолчанию";

        form.setAttribute('data-display', 'block');
        modal.setAttribute('data-display', 'block');

        //Инициализация кнопок
        btnClose.addEventListener(
            'click',
            function () {
                modal.remove();
            },
            false
        );
        btnOK.addEventListener(
            'click',
            function () {
                // alert('btnOK');
                let cancelRequest = {
                    requestId: requestId,
                    comment: form.querySelector('[data-field="comment"]').value
                }
                let json = JSON.stringify(cancelRequest);
                let req = new HttpRequestCRUD();
                req.setFetch(url + '/cancelRequest', json);
                req.setForm(form);
                let request = req.fetchJSON();
                request.then(
                    () => {
                        if (req.getStatus()) {
                            modal.remove();
                            initForm(parentForm);
                        }
                    }
                );
            },
            false
        );
        btnCancel.addEventListener(
            'click',
            function () {
                modal.remove();
            },
            false
        );
    }
}
