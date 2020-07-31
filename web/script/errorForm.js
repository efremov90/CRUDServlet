class ModalError {
    constructor() {
        let modalForm = (new DOMParser()).parseFromString(
            '<div class="modal" id="error" data-display="none">' +
            '<div class="modal-content">' +
            '<div class="modalFormTopBar">' +
            '<div class="title" style="width:250px;">Ошибка</div>' +
            '<div class="button"><button id="close" title="Закрыть">×</button></div>' +
            '</div>' +
            '<div style="height: 10px;"></div>' +
            '<div class="container">' +
            '<div class="errorMessage" style="padding-left:10px; padding-right:10px;"></div>' +
            '<div class="showStackTrace" style="width:50px; padding-left:10px; padding-right:10px; color:blue;' +
            ' cursor:pointer;">Стектрейс</div>' +
            '<div class="stackTrace" style="border-top: 1px solid #ddd; height: 100px; padding-left:10px;' +
            ' padding-right:10px;' +
            ' overflow:auto;"></div>' +
            '</div>' +
            '<div class="modalFormBottomButtonBar"><button id="ok">ОK</button></div>' +
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

        this.form.querySelector('.modal-content').style.width = '350px';

        let btnClose = this.form.querySelector('#close');
        let btnOk = this.form.querySelector('#ok');
        let btnShowStackTrace = this.form.querySelector('.showStackTrace');

        this.errorMessage = this.form.querySelector('.errorMessage');
        this.stackTrace = this.form.querySelector('.stackTrace');
        this.showStackTrace = this.form.querySelector('.showStackTrace');
        let stackTraceLet = this.stackTrace;
        this.stackTrace.setAttribute('data-display', 'none');
        this.showStackTrace.setAttribute('data-display', 'none');

        //Инициализация кнопок
        btnShowStackTrace.addEventListener(
            'click',
            function () {
                if (stackTraceLet.getAttribute('data-display') == 'none') {
                    stackTraceLet.setAttribute('data-display', 'block');
                } else if (stackTraceLet.getAttribute('data-display') == 'block') {
                    stackTraceLet.setAttribute('data-display', 'none');
                }
            },
            false
        );
        btnClose.addEventListener(
            'click',
            function () {
                formLet.remove();
            },
            false
        );
        btnOk.addEventListener(
            'click',
            function () {
                formLet.remove();
            },
            false
        );
        //return this.form;
    }

    setErrorMessage(text) {
        this.errorMessage.innerHTML = text;
    }

    setStackTrace(text) {
        this.stackTrace.innerHTML = text;
        this.showStackTrace.setAttribute('data-display', 'block');
    }

    show(parentForm) {
        parentForm.appendChild(this.form);
        this.form.setAttribute('data-display', 'block');
    }
}

function showModalError(parentForm, text/*, initForm*/) {
    alert('showModalError');

    parentForm.appendChild(createModalError());
    //initForm();
    initFormError(parentForm, text);

}

function createModalError() {
    let modalForm = (new DOMParser()).parseFromString('<div class="modal" id="error" data-display="none"><div' +
        ' class="modal-content"><div class="modalFormTopBar"><div class="title" style="width:250px;">Ошибка</div><div' +
        ' class="button"><button id="close" title="Закрыть">×</button></div></div><div style="height: 10px;"></div><div class="container" style="padding-left:10px; padding-right:10px;"></div><div class="modalFormBottomButtonBar"><button id="ok">ОK</button></div></div></div></div>', 'text/html').querySelector('div');
    let z = document.querySelectorAll('.modal').length;
    if (z) {
        z = z + 1;
    } else {
        z = 1;
    }
    modalForm.style.zIndex = z;
    modalForm.style.paddingTop = (z * 25) + 'px';
    return modalForm;
}

function initFormError(parentForm, errorMessage, stackTrace) {
    alert('initFormError');
    let form = parentForm.querySelector('.modal#error');
    if (form) {

        form.querySelector('.modal-content').style.width = '350px';

        btnClose = form.querySelector('#close');
        btnOk = form.querySelector('#ok');

        form.querySelector('.errorMessage').innerHTML = text;
        form.querySelector('.stackTrace').innerHTML = text;

        form.setAttribute('data-display', 'block');

        //Инициализация кнопок
        btnClose.addEventListener(
            'click',
            function () {
                form.parentNode.removeChild(form);
            },
            false
        );
        btnOk.addEventListener(
            'click',
            function () {
                form.parentNode.removeChild(form);
            },
            false
        );
    }
}
