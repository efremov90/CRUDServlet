initFormLogin();

function initFormLogin() {
    //alert('initFormLogin');
    let form = document.querySelector('body');
    //alert(modal.className);

    if (form) {

        let btnOK = form.querySelector('#ok');
        let inputAccount = form.querySelector('input[name="account"]');
        let inputPassword = form.querySelector('input[name="password"]');

        btnOK.addEventListener(
            'click',
            async function a() {
                let text_error = null;
                if (!inputAccount.value) text_error = "Введите логин."
                else if (!/.{5,10}/.test(inputAccount.value)) text_error = "Логин должен быть не менее 5 и не" +
                    " более 10 символов."
                else if (/[^A-Za-z0-9]+/.test(inputAccount.value)) text_error = "Логин должен состоять из" +
                    " символов 'A-Za-z0-9'."
                else if (!inputPassword.value) text_error = "Введите пароль."
                else if (!/.{5,10}/.test(inputPassword.value)) text_error = "Пароль должен быть не менее 5 и не" +
                    " более 10 символов."
                else if (/[^A-Za-z0-9]+/.test(inputPassword.value)) text_error = "Пароль должен состоять из" +
                    " символов 'A-Za-z0-9'."
                if (text_error) {
                    let errorForm = new ModalError();
                    errorForm.setErrorMessage(text_error);
                    errorForm.show(form);
                    return;
                }
                let account = inputAccount.value;
                let password = md5(inputPassword.value);
                let basic = "Basic ";
                let AuthorizationHeader = basic + btoa(account + ":" + password);
                let response = await fetch(url + "/login", {
                    method: "POST",
                    headers: {
                        "Content-Type": "text/plain;charset=UTF-8",
                        Authorization: AuthorizationHeader
                    },
                    body: undefined
                });

                if (response.ok && getCookie("AUTH_STATUS_CODE") == "S00000") {
                    location.reload();
                } else {
                    if (response.status == 401 || getCookie("AUTH_STATUS_CODE") == "S00001") {
                        let errorForm = new ModalError();
                        errorForm.setErrorMessage("Неверный логин или пароль.");
                        errorForm.show(form);
                    } else {
                        let errorForm = new ModalError();
                        errorForm.setErrorMessage("Ошибка аутентификации.");
                        errorForm.show(form);
                    }
                }
            },
            false
        );
    }
}
