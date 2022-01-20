package com.wastu.carikerja;

import com.j256.ormlite.jdbc.JdbcConnectionSource;
import com.j256.ormlite.support.ConnectionSource;
import com.wastu.carikerja.Controllers.UserController;
import com.wastu.carikerja.Helpers.DatabaseHelper;
import com.wastu.carikerja.Models.User;
import com.wastu.carikerja.View.Menu.AdminMenuView;
import com.wastu.carikerja.View.Menu.UserMenuView;
import org.beryx.textio.TextIO;
import org.beryx.textio.TextIoFactory;

public class Main {

    public static void main(String[] args) {

        try {
            TextIO mainTextIO = TextIoFactory.getTextIO();

            // Inisialisasi class
            UserMenuView userMenuView = new UserMenuView();
            AdminMenuView adminMenuView = new AdminMenuView();
            UserController userController = new UserController();

            CariKerja cariKerja = new CariKerja(userMenuView, adminMenuView, userController);
            cariKerja.run();

            // Dispose komponen yang sudah tidak dipakai.
            DatabaseHelper.getInstance().closeConnectionSource();
            mainTextIO.dispose();

        } catch (Exception e) {
            // TODO: check connection exception
            e.printStackTrace();
        }
    }


}