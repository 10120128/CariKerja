package com.wastu.carikerja;

import com.mysql.cj.jdbc.exceptions.CommunicationsException;
import com.wastu.carikerja.Controllers.KategoriController;
import com.wastu.carikerja.Controllers.LowonganController;
import com.wastu.carikerja.Controllers.UserController;
import com.wastu.carikerja.Helpers.DatabaseHelper;
import com.wastu.carikerja.Views.MainMenuView;
import org.beryx.textio.TextIO;
import org.beryx.textio.TextIoFactory;

import javax.naming.CommunicationException;
import java.sql.SQLException;

public class Main {

    public static void main(String[] args) {
        TextIO mainTextIO = TextIoFactory.getTextIO();
        mainTextIO.getTextTerminal().setBookmark("main-class");
        while (true) {
            try {
                mainTextIO.getTextTerminal().resetToBookmark("main-class");

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

                break;
            } catch (CommunicationsException | CommunicationException e) {
                Utils.showMessageConfirmation("Gagal terhubung ke database, silahkan cek koneksi internet anda", mainTextIO);
            } catch (SQLException e) {
                Utils.showMessageConfirmation("Terjadi kesalahan di database.\n" + e.getMessage(), mainTextIO);
            } catch (Exception e) {
                Utils.showMessageConfirmation("Terjadi kesalahan.\n" + e.getMessage(), mainTextIO);
            }
        }

        // Dispose komponen yang sudah tidak dipakai.
        try {
            DatabaseHelper.getInstance().closeConnectionSource();
        } catch (Exception e) {
            e.printStackTrace();
        }
        mainTextIO.dispose();
    }
}