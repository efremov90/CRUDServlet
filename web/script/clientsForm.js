function initFormClients(parentForm) {
    let form = parentForm.querySelector('.form#clients');
    if (form) {
        let btnClearFilter = form.querySelector('.buttonBar #clearFilter');
        let btnSearch = form.querySelector('.buttonBar #search');
        let btnCreateClient = form.querySelector('.buttonBar #createClient');
        if (!permissions.has("CLIENTS_CREATE")) {
            btnCreateClient.remove();
        }
        let btnEditClient = form.querySelector('.buttonBar #editClient');
        if (!permissions.has("CLIENTS_EDIT")) {
            btnEditClient.remove();
        }
        let btnSelectClientType = form.querySelector(
            '.buttonBar select#clientType'
        );

        let ALLTable = form.querySelector('.gridTable#ALL');
        let SELFSERVICETable = form.querySelector('.gridTable#SELFSERVICE');
        let DOPOFFICETable = form.querySelector('.gridTable#DOPOFFICE');

        ALLTable.setAttribute('data-display', 'none');
        ALLTable.querySelector(".headerTable TD[data-field='clientName']").setAttribute('data-sort-direction', 'asc');
        SELFSERVICETable.setAttribute('data-display', 'none');
        SELFSERVICETable.querySelector(".headerTable TD[data-field='clientName']").setAttribute('data-sort-direction', 'asc');
        DOPOFFICETable.setAttribute('data-display', 'none');
        DOPOFFICETable.querySelector(".headerTable TD[data-field='clientName']").setAttribute('data-sort-direction', 'asc');

        form.querySelector('.gridTable#' + btnSelectClientType.value).setAttribute('data-display', 'block');

        form.setAttribute('data-display', 'block');

        //Инициализация таблицы
        GridTable.init(ALLTable);
        GridTable.init(SELFSERVICETable);
        GridTable.init(DOPOFFICETable);

        ALLTable.addEventListener(
            GridTable.ev_UPDATE_STATE_CHECKED,
            function () {
                updateStateButton(this);
            },
            false
        );
        SELFSERVICETable.addEventListener(
            GridTable.ev_UPDATE_STATE_CHECKED,
            function () {
                updateStateButton(this);
            },
            false
        );
        DOPOFFICETable.addEventListener(
            GridTable.ev_UPDATE_STATE_CHECKED,
            function () {
                updateStateButton(this);
            },
            false
        );

        function getCurrentTable() {
            let table = form.querySelector('.gridTable[data-display="block"]');
            if (table) {
                return table;
            } else {
                return null;
            }
        }

        function updateStateButton(table) {
            let countCheckedItems = 0;
            countCheckedItems = table.querySelectorAll('TR[data-checked="true"]').length;

            //btnEditClient
            if (btnEditClient != null && countCheckedItems != 1) {
                btnEditClient.setAttribute('disabled', 'disabled');
            } else {
                btnEditClient.removeAttribute('disabled');
            }
        }

        updateStateButton(getCurrentTable());

        //Инициализация кнопок
        btnSelectClientType.addEventListener(
            'change',
            function () {
                let table = getCurrentTable();
                if (table) {
                    table.setAttribute('data-display', 'none');
                    GridTable.clearFilter(table);
                    table.querySelector('tbody').innerHTML = "";
                }
                table = form.querySelector('.gridTable#' + this.value);
                if (table) {
                    table.setAttribute('data-display', 'block');
                }
            },
            false
        );
        btnClearFilter.addEventListener(
            'click',
            function () {
                //alert('btnClearFilter');
                let table = getCurrentTable();
                if (table) {
                    GridTable.clearFilter(table);
                }
            },
            false
        );
        btnSearch.addEventListener('click', function () {

            let search = {
                clientType: btnSelectClientType.value
            };

            //alert('Сохранение ещё не реализовано');
            let json = JSON.stringify(search);
            //alert(json);
            let req = new HttpRequestCRUD();
            req.setFetch(url + "/getClients", json);
            req.setForm(form);
            let request = req.fetchJSON();
            request.then(
                () => {
                    if (req.getStatus()) {
                        loadGridTable(req.getData(), btnSelectClientType.value);
                    }
                }
            );
            /*fetch(url + "/getClients", {
                method: "POST",
                headers: {
                    "Content-Type": "application/json;charset=utf-8"
                },
                body: json
            }).then(response => {
                if (response.ok) {
                    return response.json();
                } else {
                    showModalError(form, function () {
                        initFormError(form, "Ошибка сервера.");
                    });
                }
            })
                .then(clients => {
                    loadGridTable(clients, btnSelectClientType.value);
                })*/
        }, false);
        if (btnCreateClient) btnCreateClient.addEventListener(
            'click',
            function () {
                showModalCreate('createClient', form, form => {
                    let btnSearch = form.querySelector('.buttonBar #search');
                    btnSearch.dispatchEvent(new Event('click'));
                });
            },
            false
        );
        if (btnEditClient) btnEditClient.addEventListener(
            'click',
            function () {
                function getClient() {
                    let clientCode = form.querySelector('.gridTable[data-display="block"]' +
                        ' tr[data-checked-current="true"]' +
                        ' td[data-field="clientCode"]').innerHTML;
                    return clientCode;
                }

                let clientCode = getClient();

                let client = {
                    clientCode: clientCode
                };

                //alert('Сохранение ещё не реализовано');
                let json = JSON.stringify(client);
                //alert(json);
                fetch(url + "/getClient", {
                    method: "POST",
                    headers: {
                        "Content-Type": "application/json;charset=utf-8"
                    },
                    body: json
                }).then(response => {
                    if (response.ok) {
                        let json = response.json();
                        return json;
                    } else {
                        showModalError(form, function () {
                            initFormError(form, "Ошибка сервера.");
                        });
                    }
                })
                    .then(client => {
                        /*alert(client.selfservice);
                        alert(client.selfservice.id);*/
                        showModalEdit('editClient', form, client, form => {
                            let btnSearch = form.querySelector('.buttonBar #search');
                            btnSearch.dispatchEvent(new Event('click'));
                        });
                    })

            },
            false
        );

        function loadGridTable(r, nameTable) {
            //alert('loadSELFSERVICEGridTable');
            //alert(r.clients.client[1].clientCode);
            let table = form.querySelector('.gridTable#' + nameTable);
            let header = table.querySelector(
                'thead .headerTable'
            );
            let columns = header.querySelectorAll('td');
            if (r && table && header && columns) {
                let tbody = document.createElement('tbody');
                for (let i in r.clients) {
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
                            if (r.clients[i][columns[j].getAttribute('data-field')]) {
                                if (columns[j].getAttribute('data-type')) {
                                    switch (columns[j].getAttribute('data-type')) {
                                        case 'date':
                                            td.innerHTML = dateTimeJSONToView(
                                                r.clients[i][columns[j].getAttribute('data-field')], 'dd'
                                            );
                                            break;
                                        case 'dateTime':
                                            td.innerHTML = dateTimeJSONToView(
                                                r.clients[i][columns[j].getAttribute('data-field')], 'mm'
                                            );
                                            break;
                                    }
                                    td.setAttribute(
                                        'data-value',
                                        r.clients[i][columns[j].getAttribute('data-field')]
                                    );
                                } else {
                                    td.innerHTML =
                                        r.clients[i][
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
}

function initFormClientsChoice(parentForm, ok) {
    //alert('initFormClientsChoice');
    let modal = parentForm.querySelector('.modal');
    let form = parentForm.querySelector('.form#clients');

    function close() {
        modal.parentNode.removeChild(modal);
    }

    if (modal) {

        modal.querySelector('.modal-content').style.width = '500px';
        modal.querySelector('.modal-content').style.maxHeight = '500px';

        let btnClose = modal.querySelector('#close');
        let btnOK = modal.querySelector('#ok');
        let btnCancel = modal.querySelector('#cancel');

        initFormClients(modal);

        let btnCreateClient = form.querySelector('.buttonBar #createClient');
        let btnEditClient = form.querySelector('.buttonBar #editClient');
        //btnCreateClient.parentNode.removeChild(btnCreateClient);
        //btnEditClient.parentNode.removeChild(btnEditClient);
        if (btnCreateClient) btnCreateClient.remove();
        if (btnEditClient) btnEditClient.remove();

        let SELFSERVICETable = form.querySelector('.gridTable#SELFSERVICE');
        let DOPOFFICETable = form.querySelector('.gridTable#DOPOFFICE');

        SELFSERVICETable.addEventListener(
            GridTable.ev_UPDATE_STATE_CHECKED,
            function () {
                updateStateButton(this);
            },
            false
        );
        DOPOFFICETable.addEventListener(
            GridTable.ev_UPDATE_STATE_CHECKED,
            function () {
                updateStateButton(this);
            },
            false
        );

        function getCurrentTable() {
            let table = form.querySelector('.gridTable[data-display="block"]');
            if (table) {
                return table;
            } else {
                return null;
            }
        }

        function updateStateButton(table) {
            let countCheckedItems = 0;
            countCheckedItems = table.querySelectorAll('TR[data-checked="true"]').length;

            //btnOK
            if (countCheckedItems != 1) {
                btnOK.setAttribute('disabled', 'disabled');
            } else {
                btnOK.removeAttribute('disabled');
            }
        }

        function getClient() {
            return form.querySelector('.gridTable[data-display="block"] tr[data-checked-current="true"] td[data-field="clientCode"]').innerHTML;
        }

        updateStateButton(getCurrentTable());


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
                ok(parentForm, getClient());
                close();
            },
            false
        );
        btnCancel.addEventListener(
            'click',
            close,
            false
        );
    }
}
