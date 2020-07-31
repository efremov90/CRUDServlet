let permissions = null;

let sidebar = document.querySelector('body .sidebar');
let content = document.querySelector('body .main-container-content');
window.addEventListener(
    "resize",
    function () {
        content.style.width = document.body.clientWidth - sidebar.offsetWidth - 5 + "px";
    },
    false
);

window.addEventListener(
    "load",
    function () {
        content.style.width = document.body.clientWidth - sidebar.offsetWidth - 5 + "px";
    },
    false
);
