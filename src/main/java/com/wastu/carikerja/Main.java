package com.wastu.carikerja;

import com.wastu.carikerja.Controllers.UserController;
import com.wastu.carikerja.Helpers.DatabaseHelper;
import com.wastu.carikerja.Views.Menu.AdminMenuView;
import com.wastu.carikerja.Views.Menu.UserMenuView;
import org.beryx.textio.TextIO;
import org.beryx.textio.TextIoFactory;

public class Main {

    public static void main(String[] args) {

        try {
            TextIO mainTextIO = TextIoFactory.getTextIO();

            // Inisialisasi class

            CariKerja cariKerja = new CariKerja();
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