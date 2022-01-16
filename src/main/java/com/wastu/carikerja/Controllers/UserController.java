package com.wastu.carikerja.Controllers;

import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.dao.DaoManager;
import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.table.TableUtils;
import com.wastu.carikerja.CustomException.AuthenticationException;
import com.wastu.carikerja.Models.User;
import com.wastu.carikerja.Utils;

import java.sql.SQLException;
import java.util.Objects;

public class UserController {
    private final ConnectionSource connectionSource;
    private final Dao<User, Long> userDao;

    public UserController(ConnectionSource connectionSource) throws SQLException {
        this.connectionSource = connectionSource;
        this.userDao = DaoManager.createDao(connectionSource, User.class);
        TableUtils.createTableIfNotExists(connectionSource, User.class);
    }

    public void daftar(User user) throws SQLException, AuthenticationException {

        // Jika ada maka throw exception
        if (userDao.queryForEq("email", user.getEmail()).stream().findFirst().orElse(null) != null) {
            throw new AuthenticationException("Email sudah terdaftar");
        }

        user.setPassword(Utils.encryptSha256(user.getPassword()));
        userDao.create(user);
    }

    public void login(String email, String password) throws SQLException, AuthenticationException {
        User user = userDao.queryForEq("email", email).stream().findFirst().orElse(null);
        if (user == null) {
            throw new AuthenticationException("Email tidak terdaftar");
        }

        if (!Objects.equals(user.getPassword(), Utils.encryptSha256(password))) {
            throw new AuthenticationException("Password salah");
        }
    }
}
