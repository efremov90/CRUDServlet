class Popup {
    constructor() {
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
        let formLet = this.form;

        // this.form.querySelector('.popup-content').style.width = '80px';

        let btnInBackground = this.form.querySelector('#inBackground');
        let btnLoad = this.form.querySelector('#load');
        btnLoad.setAttribute('data-display', 'none')

        //Инициализация кнопок
        btnInBackground.addEventListener(
            'click',
            function () {
                formLet.remove();
            },
            false
        );
        btnLoad.addEventListener(
            'click',
            function () {
                formLet.remove();
            },
            false
        );
        //return this.form;
    }

    show(parentForm) {
        parentForm.appendChild(this.form);
        this.form.setAttribute('data-display', 'block');
    }
}

