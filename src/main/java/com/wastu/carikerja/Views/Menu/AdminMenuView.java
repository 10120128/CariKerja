package com.wastu.carikerja.Views.Menu;

import com.wastu.carikerja.Helpers.SessionHelper;
import com.wastu.carikerja.Utils;
import com.wastu.carikerja.Views.MainMenuView;
import com.wastu.carikerja.Views.Submenu.ManageKategoriSubmenu;
import com.wastu.carikerja.Views.Submenu.ManageLowonganSubmenu;
import com.wastu.carikerja.Views.Submenu.ManageUserSubmenu;
import com.wastu.carikerja.Views.View;
import com.wastu.carikerja.Views.ViewUtils;
import org.beryx.textio.TextIO;
import org.beryx.textio.TextIoFactory;

public class AdminMenuView implements View {
    private static AdminMenuView instance;
    private final TextIO textIO;
    private View previousView;

    private AdminMenuView(View previousView) {
        textIO = ViewUtils.getInstance().getTextIO();;
        this.previousView = previousView;
    }

    public static synchronized AdminMenuView getInstance(View previousView) {
        if (instance == null) {
            instance = new AdminMenuView(previousView);
        }else{
            instance.previousView = previousView;
        }
        return instance;
    }

    private int getSelection() {
        textIO.getTextTerminal().setBookmark("admin-menu");
        while (true) {

            View.showHeader("Menu Admin", "Selamat Datang " + SessionHelper.getInstance().getUser().getNama() + "!");
            textIO.getTextTerminal().println("1. Kelola User");
            textIO.getTextTerminal().println("2. Kelola Lowongan");
            textIO.getTextTerminal().println("3. Kelola Kategori");
            textIO.getTextTerminal().println("4. Logout");
            int menu = textIO.newIntInputReader().withDefaultValue(1).read("Pilih menu ");

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
                ManageUserSubmenu.getInstance(this).show();
                break;
            case 2:
                ManageLowonganSubmenu.getInstance(this).show();
            case 3:
                ManageKategoriSubmenu.getInstance(this).show();
            case 4:
                SessionHelper.getInstance().logout();
                MainMenuView.getInstance().show();
                break;
            default:
                throw new Exception("Menu tidak tersedia");
        }
    }
}
