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

        form.setAttribute('data-display', 'block');

        let requestTable = form.querySelector('.gridTable#requests');

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
        btnSearch.addEventListener('click', loadRequests, false);
        if (btnCreateRequest) btnCreateRequest.addEventListener(
            'click',
            function () {
                //alert('click btnCreateRequest');
                showModalCreate('createRequest', form);
            },
            false
        );
        if (btnCancelRequest) btnCancelRequest.addEventListener(
            'click',
            function () {
                alert('Отмена ещё не реализована.');
            },
            false
        );
    }

    function loadRequests() {
        let promise = loadJSON('file/requests.txt');
        promise.then(
            contentJSON => {
                loadRequestsGridTable(contentJSON);
            }
        );
    }

    function loadRequestsGridTable(r) {
        //alert('loadRequestsGrid');
        //alert(r.requests.request[1].requestId);
        let table = form.querySelector('.gridTable#requests');
        let header = table.querySelector(
            'thead .headerTable'
        );
        let columns = header.querySelectorAll('td');
        if (r && table && header && columns) {
            let tbody = document.createElement('tbody');
            for (let i in r.requests.request) {
                //alert(i);
                let tr = document.createElement('tr');
                tbody.appendChild(tr);
                for (let j = 0; j < columns.length; j++) {
                    //alert(j);
                    if (
                        columns[j].getAttribute('data-field') &&
                        columns[j].getAttribute('data-field') != 'lastColumn'
                    ) {
                        let td = document.createElement('td');
                        td.setAttribute('data-field', columns[j].getAttribute('data-field'));
                        if (r.requests.request[i][columns[j].getAttribute('data-field')]) {
                            if (columns[j].getAttribute('data-type')) {
                                switch (columns[j].getAttribute('data-type')) {
                                    case 'date':
                                        td.innerHTML = dateTimeJSONToView(
                                            r.requests.request[i][columns[j].getAttribute('data-field')], 'dd'
                                        );
                                        break;
                                    case 'dateTime':
                                        td.innerHTML = dateTimeJSONToView(
                                            r.requests.request[i][columns[j].getAttribute('data-field')], 'mm'
                                        );
                                        break;
                                }
                                td.setAttribute(
                                    'data-value',
                                    r.requests.request[i][columns[j].getAttribute('data-field')]
                                );
                            } else {
                                td.innerHTML =
                                    r.requests.request[i][
                                        columns[j].getAttribute('data-field')
                                        ];
                            }
                        }
                        if (columns[j].getAttribute('data-display') == 'none') {
                            td.setAttribute('data-display', 'none');
                        }
                        tr.appendChild(td);
                    }
                }
            }
            GridTable.loadContent(table, tbody.innerHTML);
        }
    }
}