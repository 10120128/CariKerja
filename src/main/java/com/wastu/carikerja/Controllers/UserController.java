package com.wastu.carikerja.Controllers;

import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.dao.DaoManager;
import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.table.TableUtils;
import com.wastu.carikerja.Enums.UserRole;
import com.wastu.carikerja.Helpers.DatabaseHelper;
import com.wastu.carikerja.Helpers.SessionHelper;
import com.wastu.carikerja.Models.User;
import com.wastu.carikerja.Utils;

import java.sql.SQLException;
import java.util.List;
import java.util.Objects;

public class UserController {
    private final Dao<User, Long> userDao;
    private static UserController instance;

    public static synchronized UserController getInstance() throws SQLException {
        if (instance == null) {
            instance = new UserController();
        }
        return instance;
    }

    private UserController() throws SQLException {
        ConnectionSource connectionSource = DatabaseHelper.getInstance().getConnectionSource();
        this.userDao = DaoManager.createDao(connectionSource, User.class);
        TableUtils.createTableIfNotExists(connectionSource, User.class);
    }

    public User get(long id) throws Exception {
        User user = userDao.queryForId(id);
        if (user == null) {
            throw new Exception("User tidak ditemukan");
        }
        return user;
    }

    public List<User> list() throws SQLException {
        return userDao.queryForAll();
    }

    public void update(long id, String nama, String email, UserRole role) throws SQLException {
        User user = userDao.queryForId(id);
        user.setNama(nama);
        user.setEmail(email);
        user.setRole(role);
        userDao.update(user);
    }

    public void create(User user) throws Exception {

        // Jika ada maka throw exception
        if (userDao.queryForEq("email", user.getEmail()).stream().findFirst().orElse(null) != null) {
            throw new Exception("Email sudah terdaftar");
        }

        user.setPassword(Utils.encryptSha256(user.getPassword()));
        userDao.create(user);

        // Save ke session
        SessionHelper.getInstance().setUser(user);
    }

    public void login(String email, String password) throws SQLException, Exception {
        User user = userDao.queryForEq("email", email).stream().findFirst().orElse(null);
        if (user == null) {
            throw new Exception("Email tidak terdaftar");
        }

        if (!Objects.equals(user.getPassword(), Utils.encryptSha256(password))) {
            throw new Exception("Password salah");
        }

        SessionHelper.getInstance().setUser(user);
    }

    public boolean isUserExists(long id) throws SQLException {
        return userDao.queryForId(id) != null;
    }
}
