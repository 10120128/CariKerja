package com.wastu.carikerja.Views;

import org.beryx.textio.TextIO;
import org.beryx.textio.TextIoFactory;

public interface View {

    void show() throws Exception;

    /**
     * Menampilkan header beserta sub menu yang sedang ditampilkan.
     */
    static void showHeader(String title, String subtitle){
        //TODO: Ganti header agar lebih menarik
        TextIO textIO = ViewUtils.getInstance().getTextIO();;
        textIO.getTextTerminal().println("Halaman: " + title);

        if(!subtitle.isBlank()){
            textIO.getTextTerminal().println(subtitle);
        }

        textIO.getTextTerminal().println("+--------------------------------+\n");
    }
}
