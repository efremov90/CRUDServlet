//loadScript('lib/createRequestForm.js');

function initFormMyReports(parentForm) {
    //alert('initFormMyReports');
    let form = parentForm.querySelector('.form#my_reports');
    if (form) {
        let btnClearFilter = form.querySelector('.buttonBar #clearFilter');
        let btnSearch = form.querySelector('.buttonBar #search');
        let btnLoadReport = form.querySelector('.buttonBar #loadReports');
        //alert(permissions);
        if (!permissions.has("REPORTS_LOAD_REPORT")) {
            btnLoadReport.remove();
        }
        let btnFromReportDate = form.querySelector(
            'input[name="fromRequestDate"]'
        );
        let btnToReportDate = form.querySelector('input[name="toRequestDate"]');

        //Инициализация периода поиска
        let curDate = Date.now();
        btnFromReportDate.value = formatDate(curDate);
        btnFromReportDate.min = '2020-05-01';
        btnToReportDate.value = formatDate(curDate);
        btnToReportDate.max = formatDate(curDate);

        let reportTable = form.querySelector('.gridTable#my_reports');
        reportTable.querySelector(".headerTable TD[data-field='reportId']").setAttribute('data-sort-direction', 'desc');

        form.setAttribute('data-display', 'block');

        //Инициализация таблицы
        GridTable.init(reportTable);

        reportTable.addEventListener(
            GridTable.ev_UPDATE_STATE_CHECKED,
            updateStateButton,
            false
        );

        function updateStateButton() {
            //alert('clickTRBodyRequestTable');
            let countCheckedItems = 0;
            let trs = reportTable.querySelectorAll('TR');
            let tr = null;
            for (let i = 0; i < trs.length; i++) {
                if (trs[i].getAttribute(GridTable.d_CHECKED) == 'true') {
                    countCheckedItems = countCheckedItems + 1;
                    tr = trs[i];
                }
            }
            //
            //btnViewRequest
            if (btnLoadReport && countCheckedItems != 1) {
                btnLoadReport.setAttribute('disabled', 'disabled');
            } else {
                btnLoadReport.removeAttribute('disabled');
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
                fromReportDatetime: btnFromReportDate.value,
                toReportDatetime: btnToReportDate.value
            };

            //alert('Сохранение ещё не реализовано');
            let json = JSON.stringify(search);
            //alert(json);
            let req = new HttpRequestCRUD();
            req.setFetch(url + "/getReports", json);
            req.setForm(form);
            let request = req.fetchJSON();
            request.then(
                () => {
                    if (req.getStatus()) {
                        GridTable.loadGridTable(reportTable, req.getData());
                    }
                }
            );
        }, false);
        if (btnLoadReport) btnLoadReport.addEventListener(
            'click',
            function () {
                function getRequest() {
                    let requestId = form.querySelector('.gridTable tr[data-checked-current="true"]' +
                        ' td[data-field="requestId"]').innerHTML;
                    return requestId;
                }

                let requestId = getRequest();

                showModalView('viewRequest', form, requestId)

            },
            false
        );
    }
}