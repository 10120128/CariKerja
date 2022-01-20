package com.wastu.carikerja.View;

import org.beryx.textio.TextIO;

public abstract class BaseView {
    protected final TextIO textIO;
    protected String title;

    protected BaseView(TextIO textIO, String title) {
        this.textIO = textIO;
        this.title = title;
    }

    protected TextIO getTextIO() {
        return textIO;
    }

    /**
     * Menampilkan menu utama.
     * @return pilihan menu
     */
    protected abstract int getMenuSelection();

    /**
     * Menampilkan menu utama dan menghandle pilihan menu.<br>
     *
     * @throws Exception jika terjadi kesalahan
     */
    public abstract void viewMenu() throws Exception;

    /**
     * Menampilkan header beserta sub menu yang sedang ditampilkan.
     */
    protected void viewHeader(String subtitle){
        //TODO: Ganti header agar lebih menarik
        textIO.getTextTerminal().println("Halaman: " + title);
        textIO.getTextTerminal().println("Submenu: " + subtitle);
        textIO.getTextTerminal().println("+--------------------------------+");
    }
}
