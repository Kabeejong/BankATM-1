package org.example;

public class User {
    //attributes
    private int userid;
    private String username;
    private String password;
    private String name;

    public User(int userid, String username, String password, String name) {
        this.userid = userid;
        this.username = username;
        this.password = password;
        this.name = name;
    }

    public int getUserid() {
        return userid;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public String getName() {
        return name;
    }
}
