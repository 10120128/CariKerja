package com.wastu.carikerja.Handlers;

import com.wastu.carikerja.Enums.UserRole;
import com.wastu.carikerja.Models.User;

public class SessionHandler {
    private static SessionHandler instance;
    private User user;

    public static SessionHandler getInstance() {
        if (instance == null) {
            instance = new SessionHandler();
        }
        return instance;
    }
    public boolean isLogin() {
        return user != null;
    }

    public void setUser(User u) {
        user = u;
    }

    public User getUser() {
        if (user == null) {
            throw new Error("User tidak ditemukan");
        }
        return user;
    }

    public void logout(){
        user = null;
    }

    public boolean isAdmin(){
        return getUser().getRole() == UserRole.ADMIN;
    }
}
