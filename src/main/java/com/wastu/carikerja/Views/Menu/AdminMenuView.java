package com.wastu.carikerja.Views.Menu;

import com.wastu.carikerja.Helpers.SessionHelper;
import com.wastu.carikerja.Utils;
import com.wastu.carikerja.Views.Submenu.ManageUserSubmenu;
import com.wastu.carikerja.Views.View;
import org.beryx.textio.TextIO;
import org.beryx.textio.TextIoFactory;

public class AdminMenuView implements View {
    private static AdminMenuView instance;
    private final TextIO textIO;

    private AdminMenuView() {
        textIO = TextIoFactory.getTextIO();
    }

    public static synchronized AdminMenuView getInstance() {
        if (instance == null) {
            instance = new AdminMenuView();
        }
        return instance;
    }

    private int getSelection() {
        textIO.getTextTerminal().setBookmark("admin-menu");
        while (true) {

            View.viewHeader("Menu Admin", "Selamat Datang " + SessionHelper.getInstance().getUser().getNama() + "!");
            textIO.getTextTerminal().println("1. Kelola User");
            textIO.getTextTerminal().println("2. Kelola Pekerjaan");
            textIO.getTextTerminal().println("3. Kelola Lowongan");
            textIO.getTextTerminal().println("4. Logout");
            int menu = textIO.newIntInputReader().withDefaultValue(1).read("Pilih menu: ");

            if (menu > 4) {
                Utils.showMessageConfirmation("Menu tidak tersedia", textIO);
                textIO.getTextTerminal().resetToBookmark("admin-menu");
                continue;
            }

            textIO.getTextTerminal().resetToBookmark("admin-menu");

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
    public void show() throws Exception {
        int selection = getSelection();
        switch (selection) {
            case 1:
                // TODO: Kelola user
                ManageUserSubmenu.getInstance(this).show();
                break;
            case 2:
                // TODO: Kelola pekerjaan
                throw new Exception("Kelola Pekerjaan belum tersedia");
            case 3:
                // TODO: Kelola lowongan
                throw new Exception("Kelola Lowongan belum tersedia");
            case 4:
                SessionHelper.getInstance().logout();
                break;
            default:
                throw new Exception("Menu tidak tersedia");
        }
    }
}