function initFormRequestView(parentForm, requestId, initForm) {
    //alert('initFormRequestView');
    let modal = parentForm.querySelector('.modal');
    let form = parentForm.querySelector('.form#requestView');

    function close() {
        modal.remove();
    }

    if (form) {
        modal.querySelector('.modal-content').style.width = '800px';

        let requestTab = form.querySelector('.tab#request');
        let historyTab = form.querySelector('.tab#history');
        let auditTab = form.querySelector('.tab#audit');

        requestTab.setAttribute('data-display', 'none');
        historyTab.setAttribute('data-display', 'none');
        auditTab.setAttribute('data-display', 'none');

        //alert(SELFSERVICETable);

        let btnClose = modal.querySelector('#close');
        let btnRequest = modal.querySelector('.tabs#request');
        let btnHistory = modal.querySelector('.tabs#history');
        let btnAudit = modal.querySelector('.tabs#audit');

        //Инициализация таблицы
        GridTable.init(historyTab);
        GridTable.init(auditTab);

        btnRequest.addEventListener('click', function () {
            let request_ = {
                requestId: requestId
            };

            let json = JSON.stringify(request_);
            //alert(json);
            let req = new HttpRequestCRUD();
            req.setFetch(url + "/getRequest", json);
            req.setForm(form);
            let request = req.fetchJSON();
            request.then(
                () => {
                    let data = req.getData();
                    if (req.getStatus()) {
                        let elms = requestTab.querySelectorAll('TD [data-field]');
                        for (let i = 0; i < elms.length; i++) {
                            switch (elms[i].tagName) {
                                case 'INPUT':
                                    elms[i].value =
                                        data[elms[i].getAttribute('data-field')];
                                    break;
                                case 'TEXTAREA':
                                    elms[i].innerHTML =
                                        data[elms[i].getAttribute('data-field')];
                                    break;
                            }
                            elms[i].setAttribute(
                                'disabled',
                                'disabled'
                            );
                        }
                    }
                }
            );
        }, false);

        btnHistory.addEventListener('click', function () {
            let history_ = {
                requestId: requestId
            };

            let json = JSON.stringify(history_);
            //alert(json);
            let req = new HttpRequestCRUD();
            req.setFetch(url + "/getRequestHistory", json);
            req.setForm(form);
            let request = req.fetchJSON();
            request.then(
                () => {
                    if (req.getStatus()) {
                        loadItemsGridTable(req.getData(), historyTab)
                    }
                }
            );
        }, false);

        btnAudit.addEventListener('click', function () {
            let history_ = {
                requestId: requestId
            };

            let json = JSON.stringify(history_);
            //alert(json);
            let req = new HttpRequestCRUD();
            req.setFetch(url + "/getRequestAudit", json);
            req.setForm(form);
            let request = req.fetchJSON();
            request.then(
                () => {
                    if (req.getStatus()) {
                        loadItemsGridTable(req.getData(), auditTab)
                    }
                }
            );
        }, false);

        function getCurrentTab() {
            //alert('getCurrentTable');
            let table = form.querySelector('.tab table[data-display="block"]');
            if (table) {
                return table;
            } else {
                return null;
            }
        }

        function showTab(tab) {
            let curTab = getCurrentTab();
            if (curTab) {
                curTab.setAttribute('data-display', 'none');
            }
            if (tab) {
                tab.setAttribute('data-display', 'block');
            }
        }

        btnClose.addEventListener('click', close, false);

        btnRequest.dispatchEvent(new Event('click'));

        form.setAttribute('data-display', 'block');
        modal.setAttribute('data-display', 'block');
    }
}


