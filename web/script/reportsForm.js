function initFormReportsSelectType(reportType, parentForm) {
    // alert('initFormReportsSelectType');
    let form = parentForm.querySelector('.form#reports');
    let btnSelectReportType = form.querySelector(
        '.buttonBar select#reportType'
    );
    btnSelectReportType.value = reportType;
    form.setAttribute('data-display', 'block');
    btnSelectReportType.dispatchEvent(new Event('change'));
}

function initFormReports(reportType, parentForm) {
    // alert('initFormReports');
    let form = parentForm.querySelector('.form#reports');
    if (form) {
        let btnComplete = form.querySelector('.buttonBar #complete');
        let btnSelectReportType = form.querySelector(
            '.buttonBar select#reportType'
        );
        btnSelectReportType.value = reportType;

        let REPORT_REUESTS_DETAILED_Table = form.querySelector('table#REPORT_REQUESTS_DETAILED');
        let REPORT_REUESTS_CONSOLIDATED_Table = form.querySelector('table#REPORT_REQUESTS_CONSOLIDATED');

        REPORT_REUESTS_DETAILED_Table.setAttribute('data-display', 'none');
        REPORT_REUESTS_CONSOLIDATED_Table.setAttribute('data-display', 'none');

        if (!permissions.has("REPORT_GENERATE_REQUESTS_DETAILED")) {
            REPORT_REUESTS_DETAILED_Table.remove();
            btnSelectReportType.querySelector('[value="REPORT_REQUESTS_DETAILED"]').remove();
        }
        if (!permissions.has("REPORT_GENERATE_REPORT_REQUESTS_CONSOLIDATED")) {
            REPORT_REUESTS_CONSOLIDATED_Table.remove();
            btnSelectReportType.querySelector('[value="REPORT_GENERATE_REPORT_REQUESTS_CONSOLIDATED"]').remove();
        }

        form.querySelector('table#' + btnSelectReportType.value).setAttribute('data-display', 'block');

        form.setAttribute('data-display', 'block');

        function getCurrentTable() {
            //alert('getCurrentTable');
            let table = form.querySelector('table[data-display="block"]');
            if (table) {
                return table;
            } else {
                return null;
            }
        }

        //Инициализация кнопок
        btnSelectReportType.addEventListener(
            'change',
            function () {
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

        btnComplete.addEventListener(
            'click',
            function () {
                //alert('btnOK');
                let text_error = null;
                switch (btnSelectReportType.value) {
                    case 'REPORT_REQUESTS_DETAILED':
                        if (!(REPORT_REUESTS_DETAILED_Table.querySelector('[data-field="fromCreateDate"]').value)) text_error = "Введите дату создания."
                        else if (!(REPORT_REUESTS_DETAILED_Table.querySelector('[data-field="toCreateDate"]').value)) text_error = "Введите дату создания."
                        else if (!(REPORT_REUESTS_DETAILED_Table.querySelector('[data-field="clientCode"]').value)) text_error = "Введите код клиента."
                        break;
                    case 'REPORT_REQUESTS_CONSOLIDATED':
                        if (!REPORT_REUESTS_CONSOLIDATED_Table.querySelector('[data-field="fromCreateDate"]').value) text_error = "Введите дату" +
                            " создания."
                        else if (!REPORT_REUESTS_CONSOLIDATED_Table.querySelector('[data-field="toCreateDate"]').value) text_error = "Введите дату создания."
                        break;
                }
                if (text_error) {
                    // alert('text_error');
                    let errorForm = new ModalError();
                    errorForm.setErrorMessage(text_error);
                    errorForm.show(form);
                    return;
                }
                let generateReport = null;
                switch (btnSelectReportType.value) {
                    case 'REPORT_REQUESTS_DETAILED':
                        generateReport = {
                            reportRequestsDetailed: {
                                fromCreateDate: REPORT_REUESTS_DETAILED_Table.querySelector('[data-field="fromCreateDate"]').value,
                                toCreateDate: REPORT_REUESTS_DETAILED_Table.querySelector('[data-field="toCreateDate"]').value,
                                clientCode: REPORT_REUESTS_DETAILED_Table.querySelector('[data-field="clientCode"]').value
                            }
                        }
                        break;
                    case 'REPORT_REQUESTS_CONSOLIDATED':
                        generateReport = {
                            reportRequestsConsolidated: {
                                fromCreateDate: REPORT_REUESTS_CONSOLIDATED_Table.querySelector('[data-field="fromCreateDate"]').value,
                                toCreateDate: REPORT_REUESTS_CONSOLIDATED_Table.querySelector('[data-field="toCreateDate"]').value
                            }
                        }
                        break;
                }
                //alert('JSON');
                let json = JSON.stringify(generateReport);
                let req = new HttpRequestCRUD();
                req.setFetch(url + '/generateReport', json);
                req.setForm(form);
                let request = req.fetchJSON();
                request.then(
                    () => {
                        if (req.getStatus()) {
                            let popup = new Popup();
                            popup.show(form);
                            // modal.remove();
                            // initForm(parentForm);
                        }
                    }
                );
            },
            false
        )
    }
}

