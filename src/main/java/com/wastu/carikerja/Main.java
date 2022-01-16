package com.wastu.carikerja;

import com.j256.ormlite.jdbc.JdbcConnectionSource;
import com.j256.ormlite.support.ConnectionSource;
import com.wastu.carikerja.View.UserView;
import org.beryx.textio.TextIO;
import org.beryx.textio.TextIoFactory;
import org.beryx.textio.swing.SwingTextTerminal;

import java.awt.*;

public class Main {
    public static void main(String[] args) throws Exception {
        ConnectionSource connectionSource = new JdbcConnectionSource(Config.DB_URL, Config.DB_USERNAME, Config.DB_PASSWORD);
        UserView userView = new UserView(connectionSource);

        SwingTextTerminal mainTerm = new SwingTextTerminal();
        mainTerm.init();
        TextIO mainTextIO = new TextIO(mainTerm);
        userView.tampilkanLogin(mainTextIO);


        connectionSource.close();
    }

}
