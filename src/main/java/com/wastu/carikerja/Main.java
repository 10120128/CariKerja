package com.wastu.carikerja;

import com.mysql.cj.jdbc.exceptions.CommunicationsException;
import com.wastu.carikerja.Controllers.KategoriController;
import com.wastu.carikerja.Controllers.LowonganController;
import com.wastu.carikerja.Controllers.UserController;
import com.wastu.carikerja.Helpers.DatabaseHelper;
import com.wastu.carikerja.Views.MainMenuView;
import com.wastu.carikerja.Views.ViewUtils;
import org.beryx.textio.TextIO;
import org.beryx.textio.TextIoFactory;
import org.beryx.textio.swing.SwingTextTerminal;

import javax.naming.CommunicationException;
import java.awt.*;
import java.sql.SQLException;

public class Main {

    public static void main(String[] args) {

        TextIO mainTextIO = ViewUtils.getInstance().getTextIO();

        mainTextIO.getTextTerminal().setBookmark("main-class");
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
        } catch (CommunicationsException | CommunicationException e) {
            Utils.showMessageConfirmation("Gagal terhubung ke database, silahkan cek koneksi internet anda", mainTextIO);
        } catch (SQLException e) {
            Utils.showMessageConfirmation("Terjadi kesalahan di database.\n" + e.getMessage(), mainTextIO);
        } catch (Exception e) {
            Utils.showMessageConfirmation("Terjadi kesalahan.\n" + e.getMessage(), mainTextIO);
        }
    }
}