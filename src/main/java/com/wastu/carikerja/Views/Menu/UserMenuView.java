package com.wastu.carikerja.Views.Menu;

import com.wastu.carikerja.Helpers.SessionHelper;
import com.wastu.carikerja.Utils;
import com.wastu.carikerja.Views.Item.Lowongan.FilterLowonganByKategoriView;
import com.wastu.carikerja.Views.Item.Lowongan.ListLowonganView;
import com.wastu.carikerja.Views.Item.Lowongan.SearchLowonganView;
import com.wastu.carikerja.Views.MainMenuView;
import com.wastu.carikerja.Views.View;
import com.wastu.carikerja.Views.ViewUtils;
import org.beryx.textio.TextIO;
import org.beryx.textio.TextIoFactory;

public class UserMenuView implements View {
    private static UserMenuView instance;
    private final TextIO textIO;
    private View previousView;

    private UserMenuView(View previousView) {
        textIO = ViewUtils.getInstance().getTextIO();;
        this.previousView = previousView;
    }

    public static synchronized UserMenuView getInstance(View previousView) {
        if (instance == null) {
            instance = new UserMenuView(previousView);
        }else{
            instance.previousView = previousView;
        }
        return instance;
    }

    private int getSelection() {
        textIO.getTextTerminal().setBookmark("user-menu");
        while (true) {
            View.showHeader("Menu User", "Selamat Datang " + SessionHelper.getInstance().getUser().getNama() + "!\n");
            textIO.getTextTerminal().println("1. lihat Daftar Lowongan");
            textIO.getTextTerminal().println("2. Cari Lowongan");
            textIO.getTextTerminal().println("3. Filter Lowongan (Kategori)");
            textIO.getTextTerminal().println("4. Logout");
            int menu = textIO.newIntInputReader().withDefaultValue(1).read("Pilih menu ");

            if (menu > 4) {
                Utils.showMessageConfirmation("Menu tidak tersedia", textIO);
                textIO.getTextTerminal().resetToBookmark("user-menu");
                continue;
            }

            textIO.getTextTerminal().resetToBookmark("user-menu");
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
    public void show() throws Exception {
        int selection = getSelection();
        switch (selection) {
            case 1:
                ListLowonganView.getInstance(this).show();
            case 2:
                SearchLowonganView.getInstance(this).show();
            case 3:
                FilterLowonganByKategoriView.getInstance(this).show();
            case 4:
                SessionHelper.getInstance().logout();
                MainMenuView.getInstance().show();
                break;
            default:
                throw new Exception("Menu tidak tersedia");
        }

    }

}
