package com.wastu.carikerja.Helpers;

import com.j256.ormlite.db.DatabaseType;
import com.j256.ormlite.jdbc.JdbcConnectionSource;
import com.j256.ormlite.support.ConnectionSource;
import com.wastu.carikerja.Config;

import java.sql.SQLException;

final public class DatabaseHelper {
    private static DatabaseHelper instance;
    private ConnectionSource connectionSource;

    private DatabaseHelper() {
    }

    public static synchronized DatabaseHelper getInstance() {
        if (instance == null)
            instance = new DatabaseHelper();
        return instance;
    }

    public ConnectionSource getConnectionSource() throws SQLException {
        if (connectionSource == null) {
            connectionSource = new JdbcConnectionSource(Config.DB_URL, Config.DB_USERNAME, Config.DB_PASSWORD);
        }
        return connectionSource;
    }

    public void closeConnectionSource() throws Exception {
        if (connectionSource != null) {
            connectionSource.close();
            connectionSource = null;
        }
    }
}
