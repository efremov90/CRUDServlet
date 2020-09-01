//loadScript('lib/createRequestForm.js');

function initFormMyReports(parentForm) {
    //alert('initFormMyReports');
    let form = parentForm.querySelector('.form#myReports');
    if (form) {
        let btnClearFilter = form.querySelector('.buttonBar #clearFilter');
        let btnSearch = form.querySelector('.buttonBar #search');
        let btnDownloadReport = form.querySelector('.buttonBar #downloadReport');
        //alert(permissions);
        if (!permissions.has("REPORTS_LOAD_REPORT")) {
            btnDownloadReport.remove();
        }
        let btnFromRunDate = form.querySelector(
            'input[name="fromRunDate"]'
        );
        let btnToRunDate = form.querySelector('input[name="toRunDate"]');

        //Инициализация периода поиска
        let curDate = Date.now();
        btnFromRunDate.value = formatDate(curDate);
        btnFromRunDate.min = '2020-05-01';
        btnToRunDate.value = formatDate(curDate);
        btnToRunDate.max = formatDate(curDate);

        let reportTable = form.querySelector('.gridTable#myReports');
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
            if (btnDownloadReport && countCheckedItems != 1) {
                btnDownloadReport.setAttribute('disabled', 'disabled');
            } else {
                btnDownloadReport.removeAttribute('disabled');
            }
        }

        updateStateButton();

        //Инициализация кнопок
        btnClearFilter.addEventListener(
            'click',
            function () {
                GridTable.clearFilter(reportTable);
            },
            false
        );
        btnSearch.addEventListener('click', function () {
            // alert('btnSearch');
            let search = {
                fromRunDate: btnFromRunDate.value,
                toRunDate: btnToRunDate.value
            };
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
        if (btnDownloadReport) btnDownloadReport.addEventListener(
            'click',
            function () {
                function getReport() {
                    let reportId = form.querySelector('.gridTable tr[data-checked-current="true"]' +
                        ' td[data-field="reportId"]').innerHTML;
                    return reportId;
                }

                let reportId = getReport();

                showModalView('viewReport', form, reportId)

            },
            false
        );
    }
}