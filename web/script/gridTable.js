//alert('gridTable');
/*Класс реализующий статичные методы работы с gridTable
https://learn.javascript.ru/class
*/
class GridTable {
    //https://learn.javascript.ru/static-properties-methods
    static cn_GRID_TABLE = 'gridTable'; //Наименование класса, которое определяет поведени table как gridTable <table class="gridTable">
    static cn_FILTER_TABLE = 'filterTable'; //Наименование класса, которое определяет фильтрацию в шапке таблицы <tr class="filterTable">
    static cn_HEADER_TABLE = 'headerTable'; //Наименование класса, которое определяет заголовок в шапке таблицы <tr class="headerTable">
    static d_FOCUS = 'data-focus'; //атрибут определяет, что таблица в фокусе и для неё будут работать клавишы (true, false)
    static d_CHECKED = 'data-checked'; //атрибут определяет, что запись выбрана (true, false)
    static d_CHECKED_CURRENT = 'data-checked-current'; //атрибут определяет, что запись является текущей - выделена пунктирной оккантовкой, относительно неё осуществляется переход по клавишам вверх/вниз, по ней будет отрабатывать редактирование и прочие однозаписные события (true, false)
    static d_SORT_DIRECTION = 'data-sort-direction'; //направление сортировки (asc, desc)
    static d_NTH_CHILD = 'data-nth-child'; //odd - нечетный, even - четные
    static ev_UPDATE_STATE_CHECKED = 'updatedStateChecked';

    //Инициализация таблицы
    static init(t) {
        //alert('init');

        //listener фильтра
        let filter = t.querySelector('.filterTable');
        if (filter) {
            let elmsFilter = filter.querySelectorAll('INPUT, SELECT');
            for (let i = 0; i < elmsFilter.length; i++) {
                switch (elmsFilter[i].tagName) {
                    case 'INPUT':
                        elmsFilter[i].addEventListener('keyup', changeFilter, false);
                        break;
                    case 'SELECT':
                        elmsFilter[i].addEventListener('change', changeFilter, false);
                        break;
                }
            }
        }

        function changeFilter() {
            //alert('changeFilter');
            GridTable.filterTable(t);
        }

        //listener заголовка (сортировка)
        let header = t.querySelector('.headerTable');
        if (header) {
            let tdsHeader = header.querySelectorAll('TD');
            for (let i = 0; i < tdsHeader.length; i++) {
                tdsHeader[i].addEventListener('click', sortByTD, false);
            }
        }

        function sortByTD() {
            //alert('sortByTD');
            let dir = '';
            //Установить направление сортировки
            if (this.getAttribute(GridTable.d_SORT_DIRECTION) == 'desc') {
                dir = 'asc';
            } else if (this.getAttribute(GridTable.d_SORT_DIRECTION) == 'asc') {
                dir = 'desc';
            } else {
                //если сортировка ещё не задана, то сначала asc
                dir = 'asc';
            }
            GridTable.sort(t, this, dir);
        }

        //listener списка
        let content = t.querySelector('TBODY');
        if (content) {
            content.addEventListener(
                'click',
                function (e) {
                    let tr = e.target.closest('TR');
                    if (tr) {
                        clickTRBody(e);
                    }
                },
                false
            );
        }

        function clickTRBody(e) {
            //alert('clickTRBody');
            let tr = e.target.parentNode;
            GridTable.clickItem(e, t, tr);
        }
    }

    //Загрузка данных
    static loadContent(t, data) {
        let content = t.querySelector('TBODY');
        content.innerHTML = data;
        let tdSort = t.querySelector(".headerTable TD[data-sort-direction]")
        if (tdSort) GridTable.sort(t, tdSort, tdSort.getAttribute("data-sort-direction"));
        GridTable.filterTable(t);
    }

    //Чередование строк - покраска
    static reloadBackgroundColorTR(t) {
        //alert('reloadBackgroundColorTR');
        let trs = t.querySelectorAll('tbody tr:not([data-display="none"])');
        for (let i = 0; i < trs.length; i++) {
            if ((i + 1) % 2 == 0) {
                trs[i].setAttribute(GridTable.d_NTH_CHILD, 'even');
            } else {
                trs[i].setAttribute(GridTable.d_NTH_CHILD, 'odd');
            }
        }
    }

    //Очистка фильтра
    static clearFilter(t) {
        //alert('clearFilter');
        let filters = t.querySelector('.filterTable');
        let elmsFilter = filters.querySelectorAll('INPUT, SELECT');
        GridTable.clearCheckedItem(t);
        for (let i = 0; i < elmsFilter.length; i++) {
            switch (elmsFilter[i].tagName) {
                case 'INPUT':
                    elmsFilter[i].value = '';
                    break;
                case 'SELECT':
                    elmsFilter[i].value = 'ALL';
                    break;
            }
        }
        GridTable.filterTable(t);
    }

    static filterTable(t) {
        //alert('filterTable');
        let stringSearch;
        let trs = t.querySelectorAll('TBODY TR');
        //alert(trs.length);
        let tdsFilter = t.querySelectorAll('THEAD .filterTable TD');
        //alert(tdsFilter.length);
        //alert(tdsFilter[4].firstElementChild.value.toUpperCase())
        GridTable.clearCheckedItem(t);
        for (let i = 0; i < trs.length; i++) {
            trs[i].removeAttribute('data-display');
            let tdsTR = trs[i].querySelectorAll('TD');
            //alert(tdsTR.length);
            for (let j = 0; j < tdsFilter.length; j++) {
                if (tdsFilter[j].firstElementChild) {
                    stringSearch = tdsFilter[j].firstElementChild.value.toUpperCase();
                } else {
                    stringSearch = "";
                }
                if (tdsTR[j]) {
                    //alert(tdsTR[j].innerHTML+' '+stringSearch+' '+tdsTR[j].innerHTML.indexOf(stringSearch));
                    if (stringSearch == 'ALL') {
                        trs[i].removeAttribute('data-display');
                    } else if (
                        tdsTR[j].innerHTML.toUpperCase().indexOf(stringSearch) > -1
                    ) {
                        trs[i].removeAttribute('data-display');
                    } else {
                        trs[i].setAttribute('data-display', 'none');
                        break;
                    }
                }
            }
        }
        GridTable.reloadBackgroundColorTR(t);
    }

    //Выбор записи
    static checkedItem(t, tr) {
        //alert('checkedItem');
        let trs;
        let newCurrentTR = null;
        //Если запись, по которой выполнено событие - это выбранная и текущая
        if (
            tr.getAttribute(GridTable.d_CHECKED) == 'true' &&
            tr.getAttribute(GridTable.d_CHECKED_CURRENT) == 'true'
        ) {
            let tr_i = 0;
            trs = t.getElementsByTagName('TBODY')[0].getElementsByTagName('tr');
            //Определяем выбранную запись, предшествующую записи, по которой выполнено событие
            for (let i = 0; i < trs.length; i++) {
                if (
                    trs[i].getAttribute(GridTable.d_CHECKED) == 'true' &&
                    trs[i].getAttribute(GridTable.d_CHECKED_CURRENT) != 'true'
                ) {
                    //alert(i);
                    newCurrentTR = trs[i];
                }
                //Если newCurrentTR определен и дошли до записи, по которой выполнено событие, сразу выходим из цикла
                if (
                    /*trs[i].getAttribute(GridTable.d_CHECKED) == 'true' &&
                    trs[i].getAttribute(GridTable.d_CHECKED_CURRENT) == 'true' &&
                    newCurrentTR !=null*/
                    trs[i] == tr &&
                    newCurrentTR != null
                ) {
                    //alert(i);
                    tr_i = i;
                    break;
                }
                //Если newCurrentTR определен после того как прошли запись, по которой выполнено событие, сразу выходим из цикла
                if (newCurrentTR != null && i > tr_i) {
                    //alert(newCurrentTR.innerHTML);
                    break;
                }
            }
            GridTable.clearCheckedCurrentItem(t);
            //Сбрасываем выбор
            tr.removeAttribute(GridTable.d_CHECKED);
            tr.removeAttribute(GridTable.d_CHECKED_CURRENT);
            //Текущей делаем предыдущую - просто такое правило, чтобы среди выбранных всегда была одна текущая, т.к. переход по клавишам работает относительно неё
            if (newCurrentTR != null)
                newCurrentTR.setAttribute(GridTable.d_CHECKED_CURRENT, 'true');
        } else if (
            //Если запись уже выбрана, но не текущая, то сбрасываем выбор
            tr.getAttribute(GridTable.d_CHECKED) == 'true' &&
            tr.getAttribute(GridTable.d_CHECKED_CURRENT) != 'true'
        ) {
            //tr.setAttribute(GridTable.d_CHECKED, 'false');
            tr.removeAttribute(GridTable.d_CHECKED);
        } else {
            //Иначе если запись не выбрана, то выбираем и делаем текущей
            GridTable.clearCheckedCurrentItem(t);
            tr.setAttribute(GridTable.d_CHECKED, 'true');
            tr.setAttribute(GridTable.d_CHECKED_CURRENT, 'true');
        }
        t.dispatchEvent(new Event(GridTable.ev_UPDATE_STATE_CHECKED));
        /*} else {
          GridTable.clearCheckedItem(t);
          GridTable.clearCheckedCurrentItem(t);
        }*/
    }

    //Обработка клика записи
    //TODO: учет свойства gridTable data-multiple не реализован
    static clickItem(e, t, tr) {
        //alert('clickItem');
        //alert(e.target.tagName);
        //if (e.target.tagName)
        //Выбор через Ctrl+клик (несколько)
        if (e.ctrlKey) {
            GridTable.checkedItem(t, tr);
        } else {
            //Выбор просто через клик (одной)
            GridTable.clearCheckedItem(t);
            GridTable.checkedItem(t, tr);
        }
        GridTable.updateStateFocus(t);
        t.dispatchEvent(new Event(GridTable.ev_UPDATE_STATE_CHECKED));
    }

    //Обработка нажатия клавиши на записи
    static keyUpItem(e, t) {
        //alert('keyUpItem');
        let trs, checkedCurrentItem;
        trs = t.querySelectorAll('TBODY TR');
        //alert(table.getAttribute('data-focus'));
        checkedCurrentItem = -1;
        //alert('1');
        //Определение текущей записи
        for (let i = 0; i < trs.length; i++) {
            //alert(checkedCurrentItem);
            if (trs[i].getAttribute(GridTable.d_CHECKED_CURRENT) == 'true') {
                checkedCurrentItem = i;
                //alert(checkedCurrentItem);
                break;
            }
        }
        //alert(checkedCurrentItem);
        if (t.getAttribute(GridTable.d_FOCUS) == 'true') {
            let keyUpTrue = false; //Флаг, что нажата нужная клавиша
            if (e.keyCode == 40) {
                //alert('down');
                //Можно было бы реализовать не через индексы, а методы nextElementSibling, previousElementSibling
                checkedCurrentItem = checkedCurrentItem + 1;
                keyUpTrue = true;
                //alert(checkedCurrentItem);
            } else if (e.keyCode == 38) {
                //alert('up');
                checkedCurrentItem = checkedCurrentItem - 1;
                keyUpTrue = true;
            }
            //Если нажата нужная клавиша и переход не выходит заграницы, то перемещаемся
            if (
                keyUpTrue &&
                checkedCurrentItem >= 0 &&
                checkedCurrentItem < trs.length
            ) {
                //alert(checkedCurrentItem);
                GridTable.clearCheckedItem(t);
                GridTable.checkedItem(t, trs[checkedCurrentItem]);
                //alert(checkedCurrentItem);
            }
        }
        GridTable.updateStateFocus(t);
        t.dispatchEvent(new Event(GridTable.ev_UPDATE_STATE_CHECKED));
    }

    //Сброс выбора записей
    static clearCheckedItem(t) {
        //alert('clearCheckedItem');
        let trs = t.querySelectorAll('TBODY TR');
        for (let i = 0; i < trs.length; i++) {
            /*alert(
              i +
                ':' +
                trs[i].getAttribute(GridTable.d_CHECKED) +
                ', ' +
                trs[i].getAttribute(GridTable.d_CHECKED_CURRENT)
            );*/
            trs[i].removeAttribute(GridTable.d_CHECKED);
            trs[i].removeAttribute(GridTable.d_CHECKED_CURRENT);
            /*alert(
              i +
                ':' +
                trs[i].getAttribute(GridTable.d_CHECKED) +
                ', ' +
                trs[i].getAttribute(GridTable.d_CHECKED_CURRENT)
            );*/
        }
        //t.setAttribute(GridTable.d_FOCUS, 'false');
        t.removeAttribute(GridTable.d_FOCUS);
        //alert('clearCheckedItem:'+t);
        t.dispatchEvent(new Event(GridTable.ev_UPDATE_STATE_CHECKED));
    }

    //Сброс выбора текущей записи
    static clearCheckedCurrentItem(t) {
        //alert('clearCheckedCurrentItem:'+t);
        let trs;
        trs = t.querySelectorAll('TBODY TR');
        for (let i = 0; i < trs.length; i++) {
            trs[i].setAttribute(GridTable.d_CHECKED_CURRENT, 'false');
        }
        t.dispatchEvent(new Event(GridTable.ev_UPDATE_STATE_CHECKED));
    }

    //Обновление списка
    static reload() {
    }

    //Обновление состояния фокуса
    static updateStateFocus(t) {
        let trs;
        trs = t.querySelectorAll('TBODY TR');
        t.setAttribute(GridTable.d_FOCUS, 'false');
        for (let i = 0; i < trs.length; i++) {
            //alert(i+':'+tr[i].getAttribute('data-checked'));
            if (trs[i].getAttribute(GridTable.d_CHECKED) == 'true') {
                t.setAttribute(GridTable.d_FOCUS, 'true');
                break;
            }
        }
        //у остальных таблиц нужно сбрасывать
    }

    //Очистка сортировки
    static clearSortDirection(td) {
        //alert('clearSortDirection');
        let tds = td.parentNode.querySelectorAll('TD');
        for (let i = 0; i < tds.length; i++) {
            //alert(i+':'+tds[i].getAttribute(GridTable.d_SORT_DIRECTION));
            if (
                !((tds[i].getAttribute(GridTable.d_SORT_DIRECTION) || 'no') == 'no')
            ) {
                tds[i].removeAttribute(GridTable.d_SORT_DIRECTION);
            }
        }
    }


    //Сортировка пузырьком
    //t - таблица, td - заголовок столбца
    static sort(t, td, dir) {
        let c, tds, trs, tdX, tdY, x, y, shouldSwitch, switch_i;
        let switching = true;
        GridTable.clearCheckedItem(t);
        //alert('sort');
        tds = td.parentNode.querySelectorAll('TD');
        //alert(tds);
        for (let i = 0; i < tds.length; i++) {
            if (tds[i] == td) {
                c = i;
                break;
            }
        }
        //alert(c);
        //Установить направление сортировки
        /*if (td.getAttribute(GridTable.d_SORT_DIRECTION) == 'desc') {
            dir = 'asc';
        } else if (td.getAttribute(GridTable.d_SORT_DIRECTION) == 'asc') {
            dir = 'desc';
        } else {
            //если сортировка ещё не задана, то сначала asc
            dir = 'asc';
        }*/
        GridTable.clearSortDirection(td);
        td.setAttribute(GridTable.d_SORT_DIRECTION, dir);
        /*Цикл, который будет продолжаться до тех пор, пока
     никакой перестановки не сделано:*/
        while (switching) {
            //начать с того, что никакой перестановки не сделано:
            switching = false;
            trs = t.querySelectorAll('TBODY TR');
            //alert(rows);
            /*Цикл по всем строкам таблицы (за исключением
       первых, которые содержат заголовки таблиц):*/
            for (let i = 0; i < trs.length - 1; i++) {
                switch_i = i;
                //начнить с того, что не должно быть никакого переключения:
                shouldSwitch = false;
                /*Получить два элемента, которые нужно сравнить,
         один из текущей строки и один из следующей:*/
                tdX = trs[i].querySelectorAll('TD')[c];
                tdY = trs[i + 1].querySelectorAll('TD')[c];
                if (td.getAttribute('data-type') && tdX.getAttribute('data-value') && tdY.getAttribute('data-value')) {
                    x = tdX.getAttribute('data-value');
                    y = tdY.getAttribute('data-value');
                } else {
                    x = tdX.innerHTML.toLowerCase();
                    y = tdY.innerHTML.toLowerCase();
                }
                //alert(i+' '+x.innerHTML+' '+y.innerHTML);
                /*нужно ли менять местами элемент в зависимости от направления сортировки*/
                if (dir == 'asc') {
                    if (x > y) {
                        //если да, то фиксируем это флагом shouldSwitch и выходим из цикла:
                        shouldSwitch = true;
                        break;
                    }
                } else if (dir == 'desc') {
                    if (x < y) {
                        //если да, то фиксируем это флагом shouldSwitch и выходим из цикла:
                        shouldSwitch = true;
                        break;
                    }
                }
            }
            //если нужно менять местами
            if (shouldSwitch) {
                //alert(shouldSwitch);
                /*Меняем элементы местами и помечаем, что была*/
                trs[switch_i].parentNode.insertBefore(trs[switch_i + 1], trs[switch_i]);
                switching = true;
                //Увеличиваем каждый раз счетчик перемещения:
                //switchcount++;
            } else {
                /*Если не было никаких перемещений и текущая сортировка по возрастанию, значит список уже отсортирован и пользователь меняет порядок сортировки на обратный. Алгоритм пойдет заново. Если не было никаких перемещений и отработала и сортировка по обыванию, значит все элемент одинаковые и алгоритм прекратит работу, т.к. при первоначальном заходе в while switching = false*/
                /*if (switchcount == 0 && dir == 'asc') {
                  dir = 'desc';
                  switching = true;
                }*/
            }
        }
        GridTable.reloadBackgroundColorTR(t);
    }
}

/*-----------LISTENER-----------*/

//Код, не описывающий функции и классы, исполняется всегда

//listener страницы
document.querySelector('BODY').addEventListener(
    'keyup',
    function (e) {
        keyUpBody(e);
    },
    false
);

function keyUpBody(e) {
    //alert('keyUpBody');
    let currentForms = document.querySelectorAll('.form[data-display="block"]');
    let lastCurrentForm = currentForms[currentForms.length - 1];
    let tables = lastCurrentForm.getElementsByClassName(GridTable.cn_GRID_TABLE);
    let table = null;
    for (let i = 0; i < tables.length; i++) {
        //Если есть таблица в фокусе, то отрабатываем нажатия клавиш по ней
        if (tables[i].getAttribute(GridTable.d_FOCUS) == 'true') {
            table = tables[i];
            break;
        }
    }
    if (table != null) {
        GridTable.keyUpItem(e, table);
    }
}
