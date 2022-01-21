package com.wastu.carikerja;

import com.wastu.carikerja.Helpers.DatabaseHelper;
import com.wastu.carikerja.Views.MainMenuView;
import org.beryx.textio.TextIO;
import org.beryx.textio.TextIoFactory;

public class Main {

    public static void main(String[] args) {
        //TODO : di menu login harus bisa kembali ke menu awal atau sebelumnya
        //TODO : di menu Register harus bisa kembali ke menu awal atau sebelumnya
        //TODO : di halaman update user harus bisa kembali ke menu  sebelumnya
        //TODO : di halaman hapus user harus bisa kembali ke menu  sebelumnya
        //TODO : di buat lowongan baru harus bisa kembali ke menu  sebelumnya
        try {
            TextIO mainTextIO = TextIoFactory.getTextIO();

            MainMenuView cariKerja = MainMenuView.getInstance();
            cariKerja.show();

            // Dispose komponen yang sudah tidak dipakai.
            DatabaseHelper.getInstance().closeConnectionSource();
            mainTextIO.dispose();

        } catch (Exception e) {
            // TODO: check connection exception
            e.printStackTrace();
        }
    }


}