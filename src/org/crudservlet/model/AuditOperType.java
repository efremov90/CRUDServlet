package org.crudservlet.model;

public enum AuditOperType {

    LOGIN("Вход пользователя в систему",
            "Id пользователя {0}"),
    //"Пользователь {0}, время {1}, сессия {2}"),
    LOGOUT("Выход пользователя из системы",
            "Id пользователя {0}"),
    CREATE_CLIENT("Создание клиента",
            "Код клиента {0}"),
    EDIT_CLIENT("Редактирование клиента",
            "Код клиента {0}"),
    CREATE_REQUEST("Создание заявки",
            "Id заявки {0}"),
    CANCEL_REQUEST("Отмена заявки",
            "Id заявки {0}"),
    CLOSE_REQUEST("Закрытие заявки",
            "Id заявки {0}"),
    RUN_REPORT("Формирование отчета",
            "Id отчета {0}"),
    CREATE_TASK("Создание задания",
            "Id задания {0}"),
    START_TASK("Начало выполнения задания",
            "Id задания {0}"),
    ERROR_TASK("Ошибка выполнения задания",
            "Id задания {0}"),
    FINISH_TASK("Завершение выполнения задания",
            "Id задания {0}");

    private final String Name;
    private final String Description;

    AuditOperType(String name, String description) {
        Name = name;
        Description = description;
    }

    public String getName() {
        return Name;
    }

    public String getDescription() {
        return Description;
    }
}
