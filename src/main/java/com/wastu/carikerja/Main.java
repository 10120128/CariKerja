package com.wastu.carikerja;

import com.wastu.carikerja.Helpers.DatabaseHelper;
import org.beryx.textio.TextIO;
import org.beryx.textio.TextIoFactory;

public class Main {

    public static void main(String[] args) {

        try {
            TextIO mainTextIO = TextIoFactory.getTextIO();

            CariKerja cariKerja = new CariKerja();
            cariKerja.run();

            // Dispose komponen yang sudah tidak dipakai.
            DatabaseHelper.getInstance().closeConnectionSource();
            mainTextIO.dispose();

        } catch (Exception e) {
            // TODO: check connection exception
            e.printStackTrace();
        }
    }


}