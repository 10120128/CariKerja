package com.wastu.carikerja.View.Menu;

import com.j256.ormlite.support.ConnectionSource;
import com.wastu.carikerja.Handlers.SessionHandler;
import com.wastu.carikerja.Controllers.UserController;
import com.wastu.carikerja.Utils;
import org.beryx.textio.TextIO;

import java.sql.SQLException;

public class UserMenuView extends BaseMenuView {
    private static final String TITLE = "User";
    private final UserController controller;

    public UserMenuView(ConnectionSource source, TextIO textIO) throws SQLException {
        super(textIO, TITLE);
        controller = new UserController(source);
    }

    @Override
    protected int getMenuSelection() {
        while (true) {
            super.viewHeader("Menu User");
            textIO.getTextTerminal().println("Selamat Datang " + SessionHandler.getInstance().getUser().getNama() + "!\n");
            textIO.getTextTerminal().println("1. Daftar Lowongan");
            textIO.getTextTerminal().println("2. Cari Lowongan");
            textIO.getTextTerminal().println("3. Filter Lowongan (Kategori)");
            textIO.getTextTerminal().println("4. Logout");
            int menu = textIO.newIntInputReader().withDefaultValue(1).read("Pilih menu: ");

            if (menu > 4) {
                Utils.showMessageConfirmation("Menu tidak tersedia", textIO);
                continue;
            }

            return menu;
        }
    }

    /**
     * {@inheritDoc}
     * Pilihan Menu:<br>
     * 1. Daftar Lowongan<br>
     * 2. Cari Lowongan<br>
     * 3. Filter Lowongan (Kategori)<br>
     * 4. Logout
     */
    @Override
    public void viewMenu() throws Exception {
        int selection = getMenuSelection();
        switch (selection) {
            case 1:
                // TODO: Daftar lowongan
                throw new Exception("Daftar Lowongan belum tersedia");
            case 2:
                // TODO: Cari lowongan
                throw new Exception("Cari lowongan belum tersedia");
            case 3:
                // TODO: Filter lowongan
                throw new Exception("Filter lowongan belum tersedia");
            case 4:
                SessionHandler.getInstance().logout();
                break;
            default:
                throw new Exception("Menu tidak tersedia");
        }

    }

}
