package com.wastu.carikerja;

import com.j256.ormlite.jdbc.JdbcConnectionSource;
import com.j256.ormlite.support.ConnectionSource;
import org.beryx.textio.TextIO;
import org.beryx.textio.TextIoFactory;

public class Main {

    public static void main(String[] args) throws Exception {

        try {
            ConnectionSource connectionSource = new JdbcConnectionSource(Config.DB_URL, Config.DB_USERNAME, Config.DB_PASSWORD);
            TextIO mainTextIO = TextIoFactory.getTextIO();
            CariKerja cariKerja = new CariKerja(connectionSource, mainTextIO);
            cariKerja.run();

            // Dispose komponen yang sudah tidak dipakai.
            connectionSource.close();
            mainTextIO.dispose();

        } catch (Exception e) {
            // TODO: check connection exception
            e.printStackTrace();
        }
    }


}