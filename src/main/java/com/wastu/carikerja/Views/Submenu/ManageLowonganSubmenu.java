package com.wastu.carikerja.Views.Submenu;

import com.wastu.carikerja.Utils;
import com.wastu.carikerja.Views.Item.Lowongan.CreateLowonganView;
import com.wastu.carikerja.Views.Item.Lowongan.ListLowonganView;
import com.wastu.carikerja.Views.Item.Lowongan.UpdateLowonganView;
import com.wastu.carikerja.Views.View;
import org.beryx.textio.TextIO;
import org.beryx.textio.TextIoFactory;

public class ManageLowonganSubmenu implements View {
    private static ManageLowonganSubmenu instance;
    private final TextIO textIO;
    private final View previousView;

    private ManageLowonganSubmenu(View previousView) {
        textIO = TextIoFactory.getTextIO();
        this.previousView = previousView;
    }

    public static synchronized ManageLowonganSubmenu getInstance(View previousView) {
        if (instance == null) {
            instance = new ManageLowonganSubmenu(previousView);
        }
        return instance;
    }

    private int getSelection() {
        textIO.getTextTerminal().setBookmark("manage-lowongan-menu");
        while (true) {
            View.showHeader("Kelola Lowongan", "");
            textIO.getTextTerminal().println("1. Lihat daftar lowongan");
            textIO.getTextTerminal().println("2. Buat lowongan");
            textIO.getTextTerminal().println("3. Edit lowongan");
            textIO.getTextTerminal().println("4. Hapus lowongan");
            textIO.getTextTerminal().println("5. Kembali");
            int menu = textIO.newIntInputReader().withDefaultValue(1).read("Pilih menu: ");

            if (menu > 5) {
                Utils.showMessageConfirmation("Menu tidak tersedia", textIO);
                textIO.getTextTerminal().resetToBookmark("manage-lowongan-menu");
                continue;
            }

            textIO.getTextTerminal().resetToBookmark("manage-lowongan-menu");

            return menu;
        }
    }

    /**
     * {@inheritDoc}
     * Pilihan menu:<br>
     * 1. Lihat daftar lowongan<br>
     * 2. Buat lowongan<br>
     * 3. Edit lowongan<br>
     * 4. Hapus lowongan<br>
     * 5. Kembali
     */
    @Override
    public void show() throws Exception {
        int selection = getSelection();
        switch (selection) {
            case 1:
                ListLowonganView.getInstance(this).show();
            case 2:
                CreateLowonganView.getInstance(this).show();
            case 3:
                UpdateLowonganView.getInstance(this).show();
            case 4:
                //TODO: Hapus lowongan
                throw new Exception("Not implemented yet");
            case 5:
                previousView.show();
                break;
            default:
                throw new Exception("Menu tidak tersedia");
        }
    }
}
