package com.wastu.carikerja.View.Menu;

import org.beryx.textio.TextIO;

public abstract class BaseMenuView {
    protected final TextIO textIO;
    protected String title;

    protected BaseMenuView(TextIO textIO, String title) {
        this.textIO = textIO;
        this.title = title;
    }

    protected TextIO getTextIO() {
        return textIO;
    }

    /**
     * Mendapatkan pilihan menu yang dipilih oleh user.
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
