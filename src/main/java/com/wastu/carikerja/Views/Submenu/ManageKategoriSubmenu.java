package com.wastu.carikerja.Views.Submenu;

import com.wastu.carikerja.Utils;
import com.wastu.carikerja.Views.Item.Kategori.CreateKategoriView;
import com.wastu.carikerja.Views.Item.Kategori.DeleteKategoriView;
import com.wastu.carikerja.Views.Item.Kategori.ListKategoriView;
import com.wastu.carikerja.Views.Item.Kategori.UpdateKategoriView;
import com.wastu.carikerja.Views.View;
import org.beryx.textio.TextIO;
import org.beryx.textio.TextIoFactory;

public class ManageKategoriSubmenu implements View {
    private static ManageKategoriSubmenu instance;
    private final TextIO textIO;
    private final View previousView;

    private ManageKategoriSubmenu(View previousView) {
        textIO = TextIoFactory.getTextIO();
        this.previousView = previousView;
    }

    public static synchronized ManageKategoriSubmenu getInstance(View previousView) {
        if (instance == null) {
            instance = new ManageKategoriSubmenu(previousView);
        }
        return instance;
    }

    private int getSelection() {
        textIO.getTextTerminal().setBookmark("manage-kategori-menu");
        while (true) {
            View.showHeader("Kelola Kategori", "");
            textIO.getTextTerminal().println("1. Lihat daftar kategori");
            textIO.getTextTerminal().println("2. Buat kategori");
            textIO.getTextTerminal().println("3. Update kategori");
            textIO.getTextTerminal().println("4. Hapus kategori");
            textIO.getTextTerminal().println("5. Kembali");
            int menu = textIO.newIntInputReader().withDefaultValue(1).read("Pilih menu ");

            if (menu > 5) {
                Utils.showMessageConfirmation("Menu tidak tersedia", textIO);
                textIO.getTextTerminal().resetToBookmark("manage-kategori-menu");
                continue;
            }

            textIO.getTextTerminal().resetToBookmark("manage-kategori-menu");

            return menu;
        }
    }

    /**
     * {@inheritDoc}
     * Pilihan menu:<br>
     * 1. Lihat daftar kategori<br>
     * 2. Buat kategori<br>
     * 3. Update kategori<br>
     * 4. Hapus kategori<br>
     * 5. Kembali
     */
    @Override
    public void show() throws Exception {
        int selection = getSelection();
        switch (selection) {
            case 1:
                ListKategoriView.getInstance(this).show();
            case 2:
                CreateKategoriView.getInstance(this).show();
            case 3:
                UpdateKategoriView.getInstance(this).show();
            case 4:
                DeleteKategoriView.getInstance(this).show();
            case 5:
                previousView.show();
                break;
            default:
                throw new Exception("Menu tidak tersedia");
        }
    }
}
