package com.wastu.carikerja.Views.Submenu;

import com.wastu.carikerja.Utils;
import com.wastu.carikerja.Views.Item.DeleteUserView;
import com.wastu.carikerja.Views.Item.ListUserView;
import com.wastu.carikerja.Views.Item.UpdateUserView;
import com.wastu.carikerja.Views.View;
import org.beryx.textio.TextIO;
import org.beryx.textio.TextIoFactory;

public class ManageUserSubmenu implements View {
    private static ManageUserSubmenu instance;
    private final TextIO textIO;
    private final View previousView;

    private ManageUserSubmenu(View previousView) {
        textIO = TextIoFactory.getTextIO();
        this.previousView = previousView;
    }

    public static synchronized ManageUserSubmenu getInstance(View previousView) {
        if (instance == null) {
            instance = new ManageUserSubmenu(previousView);
        }
        return instance;
    }

    private int getSelection() {
        textIO.getTextTerminal().setBookmark("manage-user-menu");
        while (true) {
            View.viewHeader("Kelola akun user", "");
            textIO.getTextTerminal().println("1. Lihat daftar user");
            textIO.getTextTerminal().println("2. Ubah user");
            textIO.getTextTerminal().println("3. Hapus user");
            textIO.getTextTerminal().println("4. Kembali");
            int menu = textIO.newIntInputReader().withDefaultValue(1).read("Pilih menu: ");

            if (menu > 4) {
                Utils.showMessageConfirmation("Menu tidak tersedia", textIO);
                textIO.getTextTerminal().resetToBookmark("manage-user-menu");
                continue;
            }

            textIO.getTextTerminal().resetToBookmark("manage-user-menu");

            return menu;
        }
    }

    /**
     * {@inheritDoc}
     * Pilihan menu:<br>
     * 1. Lihat daftar user<br>
     * 2. Ubah user<br>
     * 3. Hapus user<br>
     * 4. Kembali<br>
     */
    @Override
    public void show() throws Exception {
        int selection = getSelection();
        switch (selection) {
            case 1:
                ListUserView.getInstance(this).show();
            case 2:
                UpdateUserView.getInstance(this).show();
            case 3:
                DeleteUserView.getInstance(this).show();
            case 4:
                previousView.show();
                break;
            default:
                throw new Exception("Menu tidak tersedia");
        }
    }
}
