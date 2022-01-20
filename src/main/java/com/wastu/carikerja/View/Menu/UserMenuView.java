package com.wastu.carikerja.View.Menu;

import com.wastu.carikerja.Helpers.SessionHelper;
import com.wastu.carikerja.Utils;

public class UserMenuView extends BaseMenuView {
    private static final String TITLE = "User";

    public UserMenuView() {
        super(TITLE);
    }

    @Override
    protected int getMenuSelection() {
        textIO.getTextTerminal().setBookmark("user-menu");
        while (true) {
            textIO.getTextTerminal().resetToBookmark("user-menu");
            super.viewHeader("Menu User");
            textIO.getTextTerminal().println("Selamat Datang " + SessionHelper.getInstance().getUser().getNama() + "!\n");
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
                SessionHelper.getInstance().logout();
                break;
            default:
                throw new Exception("Menu tidak tersedia");
        }

    }

}
