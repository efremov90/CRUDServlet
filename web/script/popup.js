class PopupReport {
    constructor() {

        this.reportId = null;

        let modalForm = (new DOMParser()).parseFromString(
            '<div class="popup" id="popup" data-display="none">' +
            '<div class="popup-content">' +
            '<div class="popupTopBar">' +
            '<div class="button"><button id="close" title="Закрыть">×</button></div>' +
            '</div>' +
            '<div class="container">' +
            '<button id="inBackground">В фоне</button>' +
            '<button id="load">Загрузить</button>' +
            '</div>' +
            '<div class="status"><span id="status">...</span><br><span id="time"></span></div>' +
            '</div>' +
            '</div>', 'text/html')
            .querySelector('div');
        let z = document.querySelectorAll('.modal').length;
        if (z) {
            z = z + 1;
        } else {
            z = 1;
        }
        modalForm.style.zIndex = z;
        modalForm.style.paddingTop = (z * 25) + 'px';
        this.form = modalForm;

        // this.form.querySelector('.popup-content').style.width = '80px';

        this.btnInBackground = this.form.querySelector('#inBackground');
        this.btnClose = this.form.querySelector("#close");
        this.btnLoad = this.form.querySelector('#load');
        this.btnLoad.setAttribute('data-display', 'none');

        this.timer = null;

        //return this.form;
    }

    init() {

        // let btnInBackground = this.btnInBackground;
        // let btnLoad = this.btnLoad;
        // let form = this.form;
        // let timer = this.timer;


    }

    setReportId(reportId) {
        this.reportId = reportId;
    }

    setTimer(timer) {
        this.timer = timer;
    }

    stopTimer() {
        clearTimeout(this.timer);
    }

    show(parentForm) {
        let btnInBackground = this.btnInBackground;
        let btnClose = this.btnClose;
        let btnLoad = this.btnLoad;
        let form = this.form;
        parentForm.appendChild(form);
        let start_dt = new Date().getTime();
        let reportId = this.reportId;
        let checkReport = {
            reportId: reportId
        }
        let status = form.querySelector('.popup .status #status');
        status.innerHTML = 'Идет формирование';
        let time = form.querySelector('.popup .status #time');
        time.innerHTML = '00:00';
        let json = JSON.stringify(checkReport);
        let req = new HttpRequestCRUD();
        req.setFetch(url + '/checkReport', json);
        req.setForm(form);
        let request = null;
        let timer = setInterval(
            function repeat() {
                let cur_dt = new Date().getTime();
                time.innerHTML = formatTime(cur_dt - start_dt);
            }
            , 1000
        );
        let check_timer = setInterval(
            function repeat() {
                request = req.fetchJSON();
                request.then(
                    () => {
                        if (req.getStatus()) {
                            if (req.getData().status == 'FINISH') {
                                btnInBackground.remove();
                                status.innerHTML = 'Сформирован';
                                btnLoad.setAttribute('data-display', 'block');
                                // alert(timer);
                                stop(check_timer);
                                stop(timer);
                            } else {
                                // this.timer = setTimeout(repeat,3000);
                                // alert('new:'+this.timer);
                            }
                        } else {
                            stop(check_timer);
                            stop(timer);
                        }
                    }
                );
            }
            , 3000
        );
        // alert('new first:'+timer);
        let stop = (t) => {
            // alert('btnInBackground:'+t);
            // this.form.remove();
            clearInterval(t);
        }
        //Инициализация кнопок
        this.btnInBackground.addEventListener(
            'click',
            () => {
                stop(check_timer);
                stop(timer);
                form.remove();
            },
            false
        );
        this.btnClose.addEventListener(
            'click',
            () => {
                stop(check_timer);
                stop(timer);
                form.remove();
            },
            false
        );
        this.btnLoad.addEventListener(
            'click',
            function () {
                // alert("btnLoad");
                let getReport = {
                    reportId: reportId
                }
                let json = JSON.stringify(getReport);
                let req = new HttpRequestCRUD();
                req.setFetch(url + '/getReport', json);
                // req.setContentType("application/json;charset=UTF-8")
                req.setContentType("application/vnd.oasis.opendocument.text;charset=UTF-8")
                req.setForm(form);
                let request = req.fetchBlob();
                request.then(
                    () => {
                        if (req.getStatus()) {
                            let link = document.createElement('a');
                            document.body.appendChild(link);
                            link.download = (decodeURI(req.getResponse()
                                .headers.get('Content-Disposition')
                                .match("(?<=(filename=\")).*?(?=(\"))")[0])).replace(/\+/g, " ");
                            link.href = window.URL.createObjectURL(req.getData());
                            link.click();
                            document.body.removeChild(link); //remove the link when done
                        }
                    }
                );
                form.remove();
            },
            false
        );
        this.form.setAttribute('data-display', 'block');
    }
}

