package com.example.user.myanotherapp.Mysql;

public class UserOnline {
    private int uId;
    private String username;
    private String password;
    private String email;

    public UserOnline()
    {
        this.uId = 0;
        this.username = "";
        this.password = "";
        this.email = "";

    }
    public UserOnline(int uId,String username,String password,String email)
    {
        this.uId = uId;
        this.username = username;
        this.password = password;
        this.email = email;

    }

    public int getuId() {
        return uId;
    }

    public void setuId(int uId) {
        this.uId = uId;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @Override
    public String toString() {
        return "user{" +
                "uId=" + uId +
                ", username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", email='" + email + '\'' +
                '}';
    }
}
