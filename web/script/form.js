let mainContainer = document.querySelector('body .layout .main-container-content');

function getPermissions() {
    return new Promise(function (resolve, reject) {
        let xhttp = new XMLHttpRequest();
        xhttp.onreadystatechange = function () {
            if (this.readyState == 4 && this.status == 200) {
                //alert("getPermissions");
                let json = JSON.parse(this.responseText);
                permissions = new Set(json.permissions);
                resolve();
            }
        };

        xhttp.open('GET', url + "/getPermissions", true);
        xhttp.send();
    });
}

function getHTMLFileNameByIdForm(id) {
    switch (id) {
        case 'home':
        case 'confirm':
        case 'requests':
        case 'createRequest':
        case 'clients':
        case 'editClient':
            return 'view/' + id + '.html';
        case 'createClient':
            return 'view/editClient.html';
        default:
            return null;
    }
}

function getJSFileNameByIdForm(id) {
    switch (id) {
        case 'confirm':
        case 'requests':
        case 'createRequest':
        case 'clients':
        case 'editClient':
            return 'script/' + id + 'Form' + '.js';
        case 'createClient':
            return 'script/editClientForm.js';
        default:
            return null;
    }
}

function showModalCreate(idForm, parentForm, initForm) {
    //alert('showModalCreate');
    let fileNameHTML = getHTMLFileNameByIdForm(idForm);
    let fileNameJS = getJSFileNameByIdForm(idForm);
    //alert(idForm+' '+' '+fileNameHTML+' '+fileNameJS);
    if (fileNameHTML) {
        let form = parentForm.querySelector('.modal');
        //alert(form);
        if (form) {
            //alert('form id=' + activeIdItemMenu + ' уже открывалась');
            form.setAttribute('data-display', 'block');
        } else {


            //let formHTML = loadHTML(fileNameHTML);

            /*formHTML.then(
                contentHTML => {
                    parentForm.appendChild(createModalForm(contentHTML));
                    //alert('createModal');
                }
            );*/

            let req = new HttpRequestCRUD();
            req.setURL(fileNameHTML);
            req.setContentType("text/html;charset=utf-8");
            req.setForm(parentForm);
            let formHTML = req.fetchHTML();

            formHTML.then(
                () => {
                    parentForm.appendChild(createModalForm(req.getData()));
                }
            );

            formHTML.then(
                () => {
                    req.setURL(fileNameJS);
                    req.setForm(parentForm);
                    let formScript = req.fetchJS();
                    formScript.then(
                        () => {
                            switch (idForm) {
                                case 'createRequest':
                                    initFormCreateRequest(parentForm);
                                    break;
                                case 'createClient':
                                    initFormCreateClient('create', parentForm, initForm);
                                    break;
                            }
                        });

                    /*                    let formScript = loadScript(fileNameJS);
                                        formScript.then(
                                            script => {
                                                switch (idForm) {
                                                    case 'createRequest': initFormCreateRequest(parentForm); break;
                                                    case 'createClient': initFormCreateClient('create',parentForm, initForm); break;
                                                }
                                            });*/
                }
            );
        }
    }
}

function showModalEdit(idForm, parentForm, contentJSON, initForm) {
    //alert('showModalEdit');
    let fileNameHTML = getHTMLFileNameByIdForm(idForm);
    let fileNameJS = getJSFileNameByIdForm(idForm);
    //alert(idForm+' '+' '+fileNameHTML+' '+fileNameJS);
    if (fileNameHTML) {
        let form = parentForm.querySelector('.modal');
        //alert(form);
        if (form) {
            //alert('form id=' + activeIdItemMenu + ' уже открывалась');
            form.setAttribute('data-display', 'block');
        } else {

            let req = new HttpRequestCRUD();
            req.setURL(fileNameHTML);
            req.setContentType("text/html;charset=utf-8");
            req.setForm(parentForm);
            let formHTML = req.fetchHTML();

            formHTML.then(
                () => {
                    parentForm.appendChild(createModalForm(req.getData()));
                }
            );

            formHTML.then(
                () => {
                    req.setURL(fileNameJS);
                    req.setForm(parentForm);
                    let formScript = req.fetchJS();
                    formScript.then(
                        () => {
                            switch (idForm) {
                                case 'editClient':
                                    initFormEditClient(parentForm, contentJSON, initForm);
                                    break;
                            }
                        });
                }
            );

        }
    }
}

function showModalConfirm(parentForm, initForm) {
    //alert('showModalConfirm');

    parentForm.appendChild(createModalConfirm());
    initForm();

}

function showModalChoice(idForm, parentForm, ok) {
    //alert('showModalChoice');
    let fileNameHTML = getHTMLFileNameByIdForm(idForm);
    let fileNameJS = getJSFileNameByIdForm(idForm);
    //alert(idForm+' '+' '+fileNameHTML+' '+fileNameJS);
    if (fileNameHTML) {
        let form = parentForm.querySelector('.modal');
        //alert(form);
        if (form) {
            //alert('form id=' + activeIdItemMenu + ' уже открывалась');
            form.setAttribute('data-display', 'block');
        } else {

            let req = new HttpRequestCRUD();
            req.setURL(fileNameHTML);
            req.setContentType("text/html;charset=utf-8");
            req.setForm(parentForm);
            let formHTML = req.fetchHTML();

            formHTML.then(
                () => {
                    parentForm.appendChild(createModalChoice(req.getData()));
                }
            );

            formHTML.then(
                () => {
                    req.setURL(fileNameJS);
                    req.setForm(parentForm);
                    let formScript = req.fetchJS();
                    formScript.then(
                        () => {
                            switch (idForm) {
                                case 'clients':
                                    initFormClientsChoice(parentForm, ok);
                                    break;
                            }
                        });
                }
            );
        }
    }
}

/*
idForm - формы,
modeForm: modalForm, mainForm
parentForm - форма (элемент), в которой открывается новая форма
initForm - script инициализации формы
*/
function showForm(idForm, parentForm, initForm) {
    // alert('showForm');
    let fileNameHTML = getHTMLFileNameByIdForm(idForm);
    let fileNameJS = getJSFileNameByIdForm(idForm);
    // alert(idForm+' '+' '+fileNameHTML+' '+fileNameJS);
    if (fileNameHTML) {
        let form = parentForm.querySelector('.form' + '#' + idForm);
        //alert(form);
        if (form) {
            //alert('form id=' + activeIdItemMenu + ' уже открывалась');
            form.setAttribute('data-display', 'block');
        } else {

            let req = new HttpRequestCRUD();
            req.setURL(fileNameHTML);
            req.setContentType("text/html;charset=utf-8");
            req.setForm(parentForm);
            let formHTML = req.fetchHTML();

            formHTML.then(
                () => {
                    parentForm.appendChild(req.getData());
                }
            );

            formHTML.then(
                () => {
                    req.setURL(fileNameJS);
                    req.setForm(parentForm);
                    let formScript = req.fetchJS();
                    formScript.then(
                        () => {
                            switch (idForm) {
                                case 'home':
                                    break;
                                case 'requests':
                                    initFormRequests(parentForm);
                                    break;
                                case 'clients':
                                    initFormClients(parentForm);
                                    break;
                            }
                        }
                    );
                }
            );

            formHTML.then(
                () => {
                    initForm();
                }
            );

        }
    }
}

function hideAllForms(parentForm) {
    let forms = parentForm.querySelectorAll('.form');
    for (let i = 0; i < forms.length; i++) {
        forms[i].setAttribute('data-display', 'none');
    }
}

function closeModalForm(form) {
    form.remove();
}

function initSideBar() {

    let body = document.body;
    let btnLogout = document.querySelector('.sidebar div#logout');
    if (btnLogout) btnLogout.addEventListener(
        'click',
        function () {
            showModalConfirm(body, function () {
                //alert();
                initFormConfirm(body, "Вы действительно хотите выйти?", function () {


                    fetch(url + "/logout", {
                        method: "POST",
                        headers: {
                            "Content-Type": "text/plain;charset=UTF-8"
                        },
                        body: undefined
                    }).then(
                        response => {
                            if (response.ok) {
                                location.reload();
                            } else {
                                showModalError(form, function () {
                                    initFormError(form, "Ошибка сервера.");
                                });
                            }
                        }
                    )
                });
            });
        },
        false
    );

    let itemsMenu = document.querySelectorAll('.sidebar div:not(#logout)');
    let sidebarEventPhase = 2;
    for (let i = 0; i < itemsMenu.length; i++) {
        itemsMenu[i].addEventListener(
            'click',
            function (e) {
                if (
                    !this.classList.contains('sidebar-li-symbol-close') &&
                    !this.classList.contains('sidebar-ul-nested')
                ) {
                    //alert('1:'+e.eventPhase);
                    for (let j = 0; j < itemsMenu.length; j++) {
                        itemsMenu[j].classList.remove('sidebar-li-checked');
                    }
                    this.classList.add('sidebar-li-checked');
                    sidebarEventPhase = e.eventPhase;
                    hideAllForms(mainContainer);
                    showForm(
                        this.id,
                        mainContainer
                    );
                } else {
                    if (
                        this.firstElementChild.classList.contains('sidebar-ul-nested') &&
                        sidebarEventPhase >= e.eventPhase
                    ) {
                        //alert('2:'+e.eventPhase);
                        this.classList.toggle('sidebar-li-symbol-open');
                        this.firstElementChild.classList.toggle('sidebar-ul-open');
                    }
                }
            },
            false
        );
    }
}

initSideBar();

window.addEventListener(
    'load',
    function () {
        hideAllForms(document.querySelector('body .layout .main-container-content'));
        let perm = getPermissions();
        perm.then(
            () => {
                let itemsMenu = document.querySelectorAll('.sidebar div.item-mode');
                for (let i = 0; i < itemsMenu.length; i++) {
                    if (itemsMenu[i].id != null) {
                        let none = false;
                        //if (itemsMenu[i].id=="home" && !permissions.has("HOME_VIEW")) none=true;
                        if (itemsMenu[i].id == "requests" && !permissions.has("REQUESTS_VIEW")) none = true;
                        if (itemsMenu[i].id == "clients" && !permissions.has("CLIENTS_VIEW")) none = true;
                        if (none) itemsMenu[i].remove();
                    }
                }
                let notItemsMode = document.querySelectorAll('.sidebar div.item-group-mode');
                //alert('notItemsMode:'+notItemsMode.length);
                for (let i = 0; i < notItemsMode.length; i++) {
                    let countAllChildItemMenu = notItemsMode[i].querySelectorAll('.item-mode').length;
                    //let countNoneChildItemMenu =
                    // notItemsMode[i].querySelectorAll('.item-mode[data-permission-display="none"]').length;
                    //alert('countAllChildItemMenu:'+countAllChildItemMenu+','+'countNoneChildItemMenu:'+countNoneChildItemMenu);
                    if (countAllChildItemMenu == 0) {
                        notItemsMode[i].remove();
                    }
                }
                if (document.querySelector('.sidebar div.item-mode#home')) showForm(
                    'home',
                    mainContainer
                );
                document.body.removeAttribute('hidden');
            }
        );
    },
    false
);

function createModalForm(form) {
    let modalForm = (new DOMParser()).parseFromString('<div class="modal" data-display="none"><div class="modal-content"><div class="modalFormTopBar"><div class="title" style="width:250px;"></div><div class="button"><button id="close" title="Закрыть">×</button></div></div><div style="height: 10px;"></div><div class="container"></div><div class="modalFormBottomButtonBar"><button id="ok">ОK</button><button id="cancel">Отмена</button></div></div></div></div>', 'text/html').querySelector('div');
    let z = document.querySelectorAll('.modal').length;
    if (z) {
        z = z + 1;
    } else {
        z = 1;
    }
    modalForm.style.zIndex = z;
    modalForm.style.paddingTop = (z * 25) + 'px';
    modalForm.querySelector('.container').appendChild(form);
    return modalForm;
}

function createModalConfirm() {
    let modalForm = (new DOMParser()).parseFromString('<div class="modal" id="confirm" data-display="none"><div class="modal-content"><div class="modalFormTopBar"><div class="title" style="width:250px;"></div><div class="button"><button id="close" title="Закрыть">×</button></div></div><div style="height: 10px;"></div><div class="container" style="padding-left:10px; padding-right:10px;"></div><div class="modalFormBottomButtonBar"><button id="ok">ОK</button><button id="cancel">Отмена</button></div></div></div></div>', 'text/html').querySelector('div');
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

function createModalChoice(form) {
    let modalForm = (new DOMParser()).parseFromString('<div class="modal" data-display="none"><div class="modal-content"><div class="modalFormTopBar"><div class="title" style="width:250px;"></div><div class="button"><button id="close" title="Закрыть">×</button></div></div><div class="container"></div><div class="modalFormBottomButtonBar"><button id="ok">ОK</button><button id="cancel">Отмена</button></div></div></div></div>', 'text/html').querySelector('div');
    let z = document.querySelectorAll('.modal').length;
    if (z) {
        z = z + 1;
    } else {
        z = 1;
    }
    modalForm.style.zIndex = z;
    modalForm.style.paddingTop = (z * 25) + 'px';
    modalForm.querySelector('.container').appendChild(form);
    //alert(modalForm.innerHTML);
    return modalForm;
}