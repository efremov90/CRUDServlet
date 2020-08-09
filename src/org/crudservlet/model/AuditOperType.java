package org.crudservlet.model;

public enum AuditOperType {

    LOGIN("Вход пользователя в систему",
            "Пользователь {0}"),
    //"Пользователь {0}, время {1}, сессия {2}"),
    LOGOUT("Выход пользователя из системы",
            "Пользователь {0}"),
    CREATE_CLIENT("Создание клиента",
            "Код клиента {0}"),
    EDIT_CLIENT("Редактирование клиента",
            "Код клиента {0}"),
    CREATE_REQUEST("Создание заявки",
            "Id заявки {0}"),
    CANCEL_REQUEST("Отмена заявки",
            "Id заявки {0}"),
    RUN_REPORT("Формирование отчета",
            "Тип отчета {0}");

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
