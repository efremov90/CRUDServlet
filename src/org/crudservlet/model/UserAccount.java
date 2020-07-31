package org.crudservlet.model;

public class UserAccount {
    private int Id;
    private String FirstName;
    private String LastName;
    private String Account;
    private String Password;

    public UserAccount() {
    }

    public UserAccount(int id, String firstName, String lastName, String account, String password) {
        Id = id;
        FirstName = firstName;
        LastName = lastName;
        Account = account;
        Password = password;
    }

    public int getId() {
        return Id;
    }

    public void setId(int id) {
        Id = id;
    }

    public String getFirstName() {
        return FirstName;
    }

    public void setFirstName(String firstName) {
        FirstName = firstName;
    }

    public String getLastName() {
        return LastName;
    }

    public void setLastName(String lastName) {
        LastName = lastName;
    }

    public String getAccount() {
        return Account;
    }

    public void setAccount(String account) {
        Account = account;
    }

    public String getPassword() {
        return Password;
    }

    public void setPassword(String password) {
        Password = password;
    }

    @Override
    public String toString() {
        return "UserAccount{" +
                "Id=" + Id +
                ", FirstName='" + FirstName + '\'' +
                ", LastName='" + LastName + '\'' +
                ", Account='" + Account + '\'' +
                ", Password='" + Password +
                '}';
    }
}
