function choiceObjectFormCreateRequest(elm) {
    elm.value = getClientFormChoiceClient();
}

function initFormCreateRequest(parentForm) {
    //alert('initFormCreateRequest');
    let modal = parentForm.querySelector('.modal');
    let form = parentForm.querySelector('.form#createRequest');
    //alert(modal.className);
    let isChange = false;

    function close() {
        if (isChange) {
            showModalConfirm(modal, function () {
                //alert();
                initFormConfirm(modal, "Изменения не будут сохранены. Продолжить?", function () {
                    modal.parentNode.removeChild(modal);
                });
            });
        } else {
            modal.parentNode.removeChild(modal);
        }
    }

    if (form) {

        modal.querySelector('.modal-content').style.width = '400px';

        let btnClose = modal.querySelector('#close');
        let btnOK = modal.querySelector('#ok');
        let btnCancel = modal.querySelector('#cancel');
        let btnChoiceObject = form.querySelector('#choiceObject');

        form.setAttribute('data-display', 'block');
        modal.setAttribute('data-display', 'block');


        form.addEventListener(
            'change',
            function (e) {
                isChange = true;
            },
            false
        );


        //Инициализация кнопок
        btnClose.addEventListener(
            'click',
            close,
            false
        );
        btnOK.addEventListener(
            'click',
            function () {
                //alert('btnOK');
                let createRequest = {
                    request: {
                        requestId: 0,
                        clientCode: form.querySelector('[data-field="clientCode"]').value,
                        comment: form.querySelector('[data-field="comment"]').value,
                        lastUserAccountIdChangeRequestStatus: 0
                    }
                }
                //alert('JSON');
                let json = JSON.stringify(createRequest);
                let req = new HttpRequestCRUD();
                req.setFetch(url + '/createRequest', json);
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
            close,
            false
        );
        btnChoiceObject.addEventListener(
            'click',
            function () {
                showModalChoice('clients', modal, (form, chosenObject) => {
                    form.querySelector('input[data-field="clientCode"]').value = chosenObject;
                    }
                );
            },
            false
        );
    }
}
