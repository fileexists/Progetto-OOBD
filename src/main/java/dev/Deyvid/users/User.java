package dev.Deyvid.users;

import dev.Deyvid.application.App;

public class User {

    private String username;
    private String password;
    private UserType userType;
    private LoginErrors error;
    public User(String username, String password, UserType userType){
        this.username = username;
        this.password = password;
        this.userType = userType;
        this.error = null;
    }

    public User(LoginErrors error){
        this.error = error;
    }

    public LoginErrors getError() {
        return error;
    }

    public User register(){
        return App.getDatabase().register(this);
    }

    @Override
    public String toString(){
        return String.format("%s %s %s", username, password, userType.toString());
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public UserType getUserType() { return userType; }
}
