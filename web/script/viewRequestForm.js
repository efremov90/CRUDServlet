function initFormViewRequest(parentForm, requestId) {
    //alert('initFormRequestView');
    let modal = parentForm.querySelector('.modal');
    let form = parentForm.querySelector('.form#requestView');

    if (form) {
        modal.querySelector('.modal-content').style.width = '800px';

        let requestTab = form.querySelector('.tab#request');
        let historyTab = form.querySelector('.tab#history');
        let auditTab = form.querySelector('.tab#audit');

        requestTab.setAttribute('data-display', 'none');
        historyTab.setAttribute('data-display', 'none');
        auditTab.setAttribute('data-display', 'none');

        let btnClose = modal.querySelector('#close');
        let btnRequest = modal.querySelector('.tabs #request');
        let btnHistory = modal.querySelector('.tabs #history');
        let btnAudit = modal.querySelector('.tabs #audit');

        //Инициализация таблицы
        GridTable.init(historyTab);
        GridTable.init(auditTab);

        btnRequest.dispatchEvent(new Event('click'));

        btnRequest.addEventListener('click', function () {
            let dataReq = {
                requestId: requestId
            };

            let json = JSON.stringify(dataReq);
            //alert(json);
            let req = new HttpRequestCRUD();
            req.setFetch(url + "/getRequest", json);
            req.setForm(form);
            let request = req.fetchJSON();
            request.then(
                () => {
                    let data = req.getData().request;
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
                    showTab(requestTab);
                }
            );
        }, false);

        btnHistory.addEventListener('click', function () {
            let dataReq = {
                requestId: requestId
            };

            let json = JSON.stringify(dataReq);
            //alert(json);
            let req = new HttpRequestCRUD();
            req.setFetch(url + "/getRequestHistory", json);
            req.setForm(form);
            let request = req.fetchJSON();
            request.then(
                () => {
                    if (req.getStatus()) {
                        GridTable.loadGridTable(historyTab, req.getData());
                    }
                    showTab(historyTab);
                }
            );
        }, false);

        btnAudit.addEventListener('click', function () {
            let dataReq = {
                requestId: requestId
            };

            let json = JSON.stringify(dataReq);
            //alert(json);
            let req = new HttpRequestCRUD();
            req.setFetch(url + "/getRequestAudits", json);
            req.setForm(form);
            let request = req.fetchJSON();
            request.then(
                () => {
                    if (req.getStatus()) {
                        GridTable.loadGridTable(auditTab, req.getData());
                    }
                    showTab(auditTab);
                }
            );
        }, false);

        function getCurrentTab() {
            //alert('getCurrentTable');
            let table = form.querySelector('table.tab[data-display="block"]');
            if (table) {
                return table;
            } else {
                return null;
            }
        }

        function showTab(tab) {
            let curTab = getCurrentTab();
            if (curTab) {
                //alert('curTab');
                curTab.setAttribute('data-display', 'none');
                // alert(form.querySelector('.tabs #'+curTab.id));
                form.querySelector('.tabs #' + curTab.id).removeAttribute('data-checked');
            }
            if (tab) {
                // alert('tab');
                tab.setAttribute('data-display', 'block');
                // alert(form.querySelector('.tabs #'+tab.id))
                form.querySelector('.tabs #' + tab.id).setAttribute('data-checked', 'true');
            }
        }

        btnClose.addEventListener('click', function () {
            modal.remove();
        }, false);

        btnRequest.dispatchEvent(new Event('click'));

        form.setAttribute('data-display', 'block');
        modal.setAttribute('data-display', 'block');
    }
}


