package com.azer.meetingmanager.data;

import com.azer.meetingmanager.App;
import com.azer.meetingmanager.data.models.Account;
import com.azer.meetingmanager.data.models.Admin;
import com.azer.meetingmanager.data.models.User;
import com.azer.meetingmanager.helpers.Utility;

public class LoggedUserResource {

    private static LoggedUserResource instance = null;

    private User loggedUser;

    public boolean isMember() {
        return !(loggedUser instanceof Admin);
    }

    public boolean isAdmin() {
        return !isMember();
    }

    public boolean isGuess() {
        return loggedUser == null;
    }

    public User getUser() {
        return loggedUser;
    }

    public Admin getAdmin() {
        return (Admin) loggedUser;
    }

    public static LoggedUserResource getInstance() {
        if (instance == null) {
            synchronized (LoggedUserResource.class) {
                if (instance == null) {
                    instance = new LoggedUserResource();
                }
            }
        }
        return instance;
    }

    public User login(String accountName, String password) {
        User user = App.getUnitOfWork().login(accountName, password);
        loggedUser = user;
        return loggedUser;
    }

    public boolean register(String userName, String userEmail, String accountName, String password) {
        byte[] salt = Utility.generateSalt(16);
        byte[] hashedPassword = Utility.generatePassword(password, salt);
        Account account = new Account(accountName, salt, hashedPassword);
        User user = new User(userName, userEmail, false, account);
        boolean result = App.getUnitOfWork().registerUser(user);
        if (result)
            this.loggedUser = user;
        return result;
    }

    public void logout() {
        loggedUser = null;
    }
}