class PopupReport {
    constructor() {

        this.reportId = null;

        let modalForm = (new DOMParser()).parseFromString(
            '<div class="popup" id="popup" data-display="none">' +
            '<div class="popup-content">' +
            '<div class="container">' +
            '<button id="inBackground">В фоне</button>' +
            '<button id="load">Загрузить</button>' +
            '</div>' +
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
        let btnLoad = this.btnLoad;
        let form = this.form;
        parentForm.appendChild(form);
        this.form.setAttribute('data-display', 'block');
        let checkReport = {
            reportId: this.reportId
        }
        let json = JSON.stringify(checkReport);
        let req = new HttpRequestCRUD();
        req.setFetch(url + '/checkReport', json);
        req.setForm(form);
        let request = null;
        let timer = setInterval(
            function repeat() {
                request = req.fetchJSON();
                request.then(
                    () => {
                        if (req.getStatus()) {
                            if (req.getData().status == 'FINISH') {
                                btnInBackground.remove();
                                btnLoad.setAttribute('data-display', 'block');
                                // alert(timer);
                                stop(timer);
                            } else {
                                // this.timer = setTimeout(repeat,3000);
                                // alert('new:'+this.timer);
                            }
                        } else {
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
                stop(timer);
            },
            false
        );
        this.btnLoad.addEventListener(
            'click',
            function () {
                form.remove();
            },
            false
        );
    }
}

