package com.wastu.carikerja;

import com.wastu.carikerja.Controllers.KategoriController;
import com.wastu.carikerja.Controllers.LowonganController;
import com.wastu.carikerja.Controllers.UserController;
import com.wastu.carikerja.Helpers.DatabaseHelper;
import com.wastu.carikerja.Views.MainMenuView;
import org.beryx.textio.TextIO;
import org.beryx.textio.TextIoFactory;

public class Main {

    public static void main(String[] args) {
        //TODO : Semua menu dan fitur bisa kembali
        try {
            TextIO mainTextIO = TextIoFactory.getTextIO();

            // Lazy loading
            mainTextIO.getTextTerminal().setBookmark("main");
            Utils.showLoading(mainTextIO);
            DatabaseHelper.getInstance();
            KategoriController.getInstance();
            LowonganController.getInstance();
            UserController.getInstance();
            mainTextIO.getTextTerminal().resetToBookmark("main");

            MainMenuView cariKerja = MainMenuView.getInstance();
            cariKerja.show();

            // Dispose komponen yang sudah tidak dipakai.
            DatabaseHelper.getInstance().closeConnectionSource();
            mainTextIO.dispose();

        } catch (Exception e) {
            // TODO: check connection exception
            e.printStackTrace();
        }
    }


}