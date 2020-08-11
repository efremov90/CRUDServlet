//loadScript('lib/createRequestForm.js');

function initFormRequests(parentForm) {
    //alert('initFormRequests');
    let form = parentForm.querySelector('.form#requests');
    if (form) {
        let btnClearFilter = form.querySelector('.buttonBar #clearFilter');
        let btnSearch = form.querySelector('.buttonBar #search');
        let btnViewRequest = form.querySelector('.buttonBar #viewRequest');
        let btnCreateRequest = form.querySelector('.buttonBar #createRequest');
        //alert(permissions);
        if (!permissions.has("REQUESTS_VIEW_REQUEST")) {
            btnViewRequest.remove();
        }
        if (!permissions.has("REQUESTS_CREATE")) {
            btnCreateRequest.remove();
        }
        let btnCancelRequest = form.querySelector('.buttonBar #cancelRequest');
        if (!permissions.has("REQUESTS_CANCEL")) {
            btnCancelRequest.remove();
        }
        let btnFromRequestDate = form.querySelector(
            'input[name="fromRequestDate"]'
        );
        let btnToRequestDate = form.querySelector('input[name="toRequestDate"]');

        //Инициализация периода поиска
        let curDate = Date.now();
        btnFromRequestDate.value = formatDate(curDate - 1000 * 60 * 60 * 24 * 31);
        btnFromRequestDate.min = '2020-05-01';
        btnToRequestDate.value = formatDate(curDate);
        btnToRequestDate.max = formatDate(curDate);

        let requestTable = form.querySelector('.gridTable#requests');
        requestTable.querySelector(".headerTable TD[data-field='requestId']").setAttribute('data-sort-direction', 'desc');

        form.setAttribute('data-display', 'block');

        //Инициализация таблицы
        GridTable.init(requestTable);

        requestTable.addEventListener(
            GridTable.ev_UPDATE_STATE_CHECKED,
            updateStateButton,
            false
        );

        function updateStateButton() {
            //alert('clickTRBodyRequestTable');
            let countCheckedItems = 0;
            let trs = requestTable.querySelectorAll('TR');
            let tr = null;
            for (let i = 0; i < trs.length; i++) {
                if (trs[i].getAttribute(GridTable.d_CHECKED) == 'true') {
                    countCheckedItems = countCheckedItems + 1;
                    tr = trs[i];
                }
            }
            //btnCancelRequest
            if (btnCancelRequest && countCheckedItems != 1) {
                btnCancelRequest.setAttribute('disabled', 'disabled');
            } else {
                let requestStatus = tr.querySelector('TD[data-field="requestStatus"]')
                    .innerHTML;
                //alert(requestStatus);
                if (requestStatus == 'CANCELED') {
                    btnCancelRequest.setAttribute('disabled', 'disabled');
                } else {
                    btnCancelRequest.removeAttribute('disabled');
                }
            }
        }

        updateStateButton();

        //Инициализация кнопок
        btnClearFilter.addEventListener(
            'click',
            function () {
                GridTable.clearFilter(requestTable);
            },
            false
        );
        btnSearch.addEventListener('click', function () {
            let search = {
                fromCreateDate: btnFromRequestDate.value,
                toCreateDate: btnToRequestDate.value
            };

            //alert('Сохранение ещё не реализовано');
            let json = JSON.stringify(search);
            //alert(json);
            let req = new HttpRequestCRUD();
            req.setFetch(url + "/getRequests", json);
            req.setForm(form);
            let request = req.fetchJSON();
            request.then(
                () => {
                    if (req.getStatus()) {
                        GridTable.loadGridTable(requestTable, req.getData());
                    }
                }
            );
        }, false);
        if (btnCreateRequest) btnCreateRequest.addEventListener(
            'click',
            function () {
                //alert('click btnCreateRequest');
                showModalCreate('createRequest', form, form => {
                    let btnSearch = form.querySelector('.buttonBar #search');
                    btnSearch.dispatchEvent(new Event('click'));
                });
            },
            false
        );
        if (btnCancelRequest) btnCancelRequest.addEventListener(
            'click',
            function () {
                // alert('btnCancelRequest');
                function getRequest() {
                    let requestId = form.querySelector('.gridTable tr[data-checked-current="true"]' +
                        ' td[data-field="requestId"]').innerHTML;
                    return requestId;
                }

                let requestId = getRequest();

                showModalEdit('cancelRequest', form, requestId, form => {
                    let btnSearch = form.querySelector('.buttonBar #search');
                    btnSearch.dispatchEvent(new Event('click'));
                });

            },
            false
        );
    }
}