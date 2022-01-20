package com.wastu.carikerja.Helpers;

import com.wastu.carikerja.Enums.UserRole;
import com.wastu.carikerja.Models.User;

public class SessionHelper {
    private static SessionHelper instance;
    private User user;

    private SessionHelper() {
    }

    public static synchronized SessionHelper getInstance() {
        if (instance == null) {
            instance = new SessionHelper();
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

    public void logout() {
        user = null;
    }

    public boolean isAdmin() {
        return getUser().getRole() == UserRole.ADMIN;
    }
}
