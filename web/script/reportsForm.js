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
        /*let btnCreateClient = form.querySelector('.buttonBar #createClient');
        if (!permissions.has("CLIENTS_CREATE")) {
            btnCreateClient.remove();
        }
        let btnEditClient = form.querySelector('.buttonBar #editClient');
        if (!permissions.has("CLIENTS_EDIT")) {
            btnEditClient.remove();
        }*/
        let btnSelectReportType = form.querySelector(
            '.buttonBar select#reportType'
        );
        btnSelectReportType.value = reportType;

        let REPORT_REUESTS_DETAILED_Table = form.querySelector('table#REPORT_REQUESTS_DETAILED');
        let REPORT_REUESTS_CONSOLIDATED_Table = form.querySelector('table#REPORT_REQUESTS_CONSOLIDATED');

        REPORT_REUESTS_DETAILED_Table.setAttribute('data-display', 'none');
        REPORT_REUESTS_CONSOLIDATED_Table.setAttribute('data-display', 'none');

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
                // cross_download('file/file.pdf','1.pdf');
                //location.href = "file/file.pdf";
            },
            false
        )
    }
}

