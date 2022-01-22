package com.wastu.carikerja.Views;

import com.wastu.carikerja.Helpers.SessionHelper;
import com.wastu.carikerja.Utils;
import com.wastu.carikerja.Views.Item.Auth.LoginView;
import com.wastu.carikerja.Views.Item.Auth.RegisterView;
import org.beryx.textio.TextIO;
import org.beryx.textio.TextIoFactory;

public class MainMenuView implements View{
    private static MainMenuView instance;
    private final TextIO textIO;

    private MainMenuView() {
        this.textIO = TextIoFactory.getTextIO();
    }

    public static synchronized MainMenuView getInstance() {
        if (instance == null) {
            instance = new MainMenuView();
        }
        return instance;
    }

    /**
     * Menjalankan aplikasi dengan menampilkan menu utama.
     *
     * @throws Exception Mengembalikan exception jika terjadi kesalahan.
     */
    @Override
    public void show() throws Exception {

        textIO.getTextTerminal().setBookmark("menu");
        while (true) {
            textIO.getTextTerminal().resetToBookmark("menu");
            int selection = getMainMenuSelection();
            textIO.getTextTerminal().resetToBookmark("menu");

            // Panggil menu
                switch (selection) {
                    case 1:
                        LoginView.getInstance(this).show();
                        break;
                    case 2:
                        RegisterView.getInstance(this).show();
                        break;
                    case 3:
                        handleExit();
                        break;
                }


            // Cek status login, apabila terdeteksi logout maka kembali ke menu utama.
            if (!SessionHelper.getInstance().isLogin()) {
                continue;
            }

            break;
        }
    }

    private void handleExit() {
        textIO.dispose();
        System.exit(0);
    }

    /**
     * Menampilkan menu utama.<br>
     * Pilihan menu:<br>
     * 1. Login<br>
     * 2. Daftar<br>
     * 3. Keluar<br>
     *
     * @return Pilihan menu
     */
    private int getMainMenuSelection() {
        textIO.getTextTerminal().setBookmark("main-menu");
        while (true) {
            textIO.getTextTerminal().resetToBookmark("main-menu");
            textIO.getTextTerminal().println("Selamat Datang!\n");
            textIO.getTextTerminal().println("1. Login");
            textIO.getTextTerminal().println("2. Daftar");
            textIO.getTextTerminal().println("3. Keluar");
            int menu = textIO.newIntInputReader().withDefaultValue(1).read("Pilih menu ");

            if (menu > 3) {
                Utils.showMessageConfirmation("Menu tidak tersedia", textIO);
                continue;
            }

            return menu;
        }
    }


}
