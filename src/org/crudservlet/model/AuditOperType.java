package org.crudservlet.model;

public enum AuditOperType {

    LOGIN(10001, "Вход пользователя в систему",
            "Пользователь {0}"),
    //"Пользователь {0}, время {1}, сессия {2}"),
    LOGOUT(10002, "Выход пользователя из системы",
            "Пользователь {0}"),
    CREATE_CLIENT(11001, "Создание клиента",
            "Код клиента {0}"),
    EDIT_CLIENT(11002, "Редактирование клиента",
            "Код клиента {0}"),
    CREATE_REQUEST(12001, "Создание заявки",
            "Id заявки {0}"),
    CANCEL_REQUEST(12002, "Отмена заявки",
            "Id заявки {0}"),
    RUN_REPORT(13002, "Формирование отчета",
            "Тип отчета {0}");

    private final int Code;
    private final String Name;
    private final String Description;

    AuditOperType(int code, String name, String description) {
        Code = code;
        Name = name;
        Description = description;
    }

    public int getCode() {
        return Code;
    }

    public String getName() {
        return Name;
    }

    public String getDescription() {
        return Description;
    }
}
