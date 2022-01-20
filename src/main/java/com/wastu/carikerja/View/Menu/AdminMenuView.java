package com.wastu.carikerja.View.Menu;

import com.wastu.carikerja.Handlers.SessionHandler;
import com.wastu.carikerja.Utils;
import org.beryx.textio.TextIO;

public class AdminMenuView extends BaseMenuView {
    private static final String TITLE = "Admin";

    public AdminMenuView(TextIO textIO) {
        super(textIO, TITLE);
    }

    @Override
    protected int getMenuSelection() {

        while (true) {
            viewHeader("Menu Admin");
            textIO.getTextTerminal().println("Selamat Datang " + SessionHandler.getInstance().getUser().getNama() + "!\n");
            textIO.getTextTerminal().println("1. Kelola User");
            textIO.getTextTerminal().println("2. Kelola Pekerjaan");
            textIO.getTextTerminal().println("3. Kelola Lowongan");
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
     * Pilihan menu:<br>
     * 1. Kelola User<br>
     * 2. Kelola Lowongan<br>
     * 3. Kelola Kategori<br>
     * 4. Logout<br>
     */
    @Override
    public void viewMenu() throws Exception {
        int selection = getMenuSelection();
        switch (selection) {
            case 1:
                // TODO: Kelola user
                throw new Exception("Kelola User belum tersedia");
            case 2:
                // TODO: Kelola pekerjaan
                throw new Exception("Kelola Pekerjaan belum tersedia");
            case 3:
                // TODO: Kelola lowongan
                throw new Exception("Kelola Lowongan belum tersedia");
            case 4:
                SessionHandler.getInstance().logout();
                break;
            default:
                throw new Exception("Menu tidak tersedia");
        }
    }
}
