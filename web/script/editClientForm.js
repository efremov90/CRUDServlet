function initFormCreateClient(mode, parentForm, initForm) {
    //alert('initFormCreateClient');
    let modal = parentForm.querySelector('.modal');
    let form = parentForm.querySelector('.form#editClient');
    let isChange = false;

    function close() {
        if (isChange) {
            showModalConfirm(modal, function () {
                //alert();
                initFormConfirm(
                    modal,
                    'Изменения не будут сохранены. Продолжить?',
                    function () {
                        modal.parentNode.removeChild(modal);
                    }
                );
            });
        } else {
            modal.parentNode.removeChild(modal);
        }
    }

    if (form) {
        modal.querySelector('.modal-content').style.width = '400px';

        let SELFSERVICETable = form.querySelector('table#SELFSERVICE');
        let DOPOFFICETable = form.querySelector('table#DOPOFFICE');

        SELFSERVICETable.setAttribute('data-display', 'none');
        DOPOFFICETable.setAttribute('data-display', 'none');

        //alert(SELFSERVICETable);

        let btnClose = modal.querySelector('#close');
        let btnOK = modal.querySelector('#ok');
        let btnCancel = modal.querySelector('#cancel');
        let btnSelectClientType = form.querySelector(
            '.buttonBar select#clientType'
        );

        //alert(btnSelectClientType.value);

        form.querySelector('table#' + btnSelectClientType.value).setAttribute(
            'data-display',
            'block'
        );

        form.setAttribute('data-display', 'block');
        modal.setAttribute('data-display', 'block');

        function getCurrentTable() {
            //alert('getCurrentTable');
            let table = form.querySelector('table[data-display="block"]');
            if (table) {
                return table;
            } else {
                return null;
            }
        }

        getCurrentTable();

        form.addEventListener(
            'change',
            function (e) {
                isChange = true;
            },
            false
        );

        //Инициализация кнопок
        btnSelectClientType.addEventListener(
            'change',
            function () {
                //alert('change btnSelectClientType');
                let table = getCurrentTable();
                if (table) {
                    table.setAttribute('data-display', 'none');
                }
                table = form.querySelector('table#' + this.value);
                if (table) {
                    table.setAttribute('data-display', 'block');
                }
            },
            false
        );
        btnClose.addEventListener('click', close, false);
        btnOK.addEventListener(
            'click',
            function () {
                //alert('btnOK');
                let client = null;
                switch (btnSelectClientType.value) {
                    case 'DOPOFFICE':
                        client = {
                            dopoffice: {
                                id: 0,
                                clientCode: DOPOFFICETable.querySelector('[data-field="clientCode"]').value,
                                clientName: DOPOFFICETable.querySelector('[data-field="clientName"]').value,
                                address: DOPOFFICETable.querySelector('[data-field="address"]').value,
                                closeDate: DOPOFFICETable.querySelector('[data-field="closeDate"]').value
                            }
                        }
                        break;
                    case 'SELFSERVICE':
                        client = {
                            selfservice: {
                                id: 0,
                                clientCode: SELFSERVICETable.querySelector('[data-field="clientCode"]').value,
                                clientName: SELFSERVICETable.querySelector('[data-field="clientName"]').value,
                                atmtype: SELFSERVICETable.querySelector('[data-field="atmtype"]').value,
                                address: SELFSERVICETable.querySelector('[data-field="address"]').value,
                                closeDate: SELFSERVICETable.querySelector('[data-field="closeDate"]').value
                            }
                        }
                        break;
                }
                //alert('JSON');
                let json = JSON.stringify(client);
                let path = '';
                switch (mode) {
                    case 'create':
                        path = '/createClient';
                        break;
                    case 'edit':
                        path = '/editClient';
                        break;
                }
                let req = new HttpRequestCRUD();
                req.setFetch(url + path, json);
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
        btnCancel.addEventListener('click', close, false);
    }
}

function initFormEditClient(parentForm, r, initForm) {
    //alert('initFormEditClient');
    let client;
    let clientType = null;
    /*alert(r.selfservice);
    alert(r.selfservice.id);*/
    if (r.dopoffice != null) {
        clientType = 'DOPOFFICE';
        client = r.dopoffice;
    } else if (r.selfservice != null) {
        clientType = 'SELFSERVICE';
        client = r.selfservice;
    }
    let modal = parentForm.querySelector('.modal');
    let form = parentForm.querySelector('.form#editClient');

    let btnSelectClientType = form.querySelector(
        '.buttonBar select#clientType'
    );

    btnSelectClientType.setAttribute(
        'disabled',
        'disabled'
    );

    switch (clientType) {
        case 'SELFSERVICE': {
            btnSelectClientType.value = 'SELFSERVICE';
            let elms = form.querySelectorAll('#SELFSERVICE TD [data-field]');
            for (let i = 0; i < elms.length; i++) {
                switch (elms[i].tagName) {
                    case 'INPUT':
                    case 'SELECT':
                        //alert(elms[i].getAttribute('data-field')+': '+elms[i].value+' '+client[elms[i].getAttribute('data-field')]);
                        elms[i].value =
                            client[elms[i].getAttribute('data-field')];
                        break;
                    case 'TEXTAREA':
                        elms[i].innerHTML =
                            client[elms[i].getAttribute('data-field')];
                        break;
                }
                switch (elms[i].getAttribute('data-field')) {
                    case 'clientCode':
                        elms[i].setAttribute(
                            'disabled',
                            'disabled'
                        );
                        break;
                }
            }
        }
            break;
        case 'DOPOFFICE': {
            btnSelectClientType.value = 'DOPOFFICE';
            elms = form.querySelectorAll('#DOPOFFICE TD [data-field]');
            for (let i = 0; i < elms.length; i++) {
                switch (elms[i].tagName) {
                    case 'INPUT':
                    case 'SELECT':
                        //alert(elms[i].getAttribute('data-field')+': '+elms[i].value+' '+client[elms[i].getAttribute('data-field')]);
                        elms[i].value =
                            client[elms[i].getAttribute('data-field')];
                        break;
                    case 'TEXTAREA':
                        elms[i].innerHTML =
                            client[elms[i].getAttribute('data-field')];
                        break;
                }
                switch (elms[i].getAttribute('data-field')) {
                    case 'clientCode':
                        elms[i].setAttribute(
                            'disabled',
                            'disabled'
                        );
                        break;
                }
            }
        }
            break;
    }

    initFormCreateClient('edit', parentForm, initForm);

    /*form.querySelector('table#' + btnSelectClientType.value).setAttribute(
        'data-display',
        'block'
    );*/
}
