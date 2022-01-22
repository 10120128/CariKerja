package com.wastu.carikerja.Views.Item.Kategori;

import com.github.freva.asciitable.AsciiTable;
import com.github.freva.asciitable.Column;
import com.github.freva.asciitable.HorizontalAlign;
import com.wastu.carikerja.Controllers.KategoriController;
import com.wastu.carikerja.Models.Kategori;
import com.wastu.carikerja.Views.View;
import org.beryx.textio.TextIO;
import org.beryx.textio.TextIoFactory;

import java.sql.SQLException;
import java.util.Arrays;
import java.util.List;

public class ListKategoriView implements View {
    private static ListKategoriView instance;
    private View previousView;
    private final KategoriController kategoriController;
    private final TextIO textIO;

    private ListKategoriView(View previousView) throws SQLException {
        this.previousView = previousView;
        this.kategoriController = KategoriController.getInstance();
        this.textIO = TextIoFactory.getTextIO();
    }

    public static synchronized ListKategoriView getInstance(View previousView) throws SQLException {
        if (instance == null) {
            instance = new ListKategoriView(previousView);
        }else{
            instance.previousView = previousView;
        }
        return instance;
    }

    /**
     * Menampilkan daftar kategori, akan kembali ke view sebelumnya jika user memilih untuk kembali.
     *
     * @throws Exception jika terjadi error
     */
    @Override
    public void show() throws Exception {
        textIO.getTextTerminal().setBookmark("list-kategori");
        List<Kategori> listKategori = kategoriController.list();

        if (listKategori.isEmpty()) {
            textIO.getTextTerminal().println("Tidak ada kategori yang tersedia");
        } else {
            View.showHeader("Daftar kategori", "");
            textIO.getTextTerminal().println(AsciiTable.getTable(listKategori, Arrays.asList(
                    new Column().header("Id").headerAlign(HorizontalAlign.CENTER).dataAlign(HorizontalAlign.LEFT).with(kategori -> Long.toString(kategori.getId())),
                    new Column().header("Nama").headerAlign(HorizontalAlign.CENTER).dataAlign(HorizontalAlign.LEFT).with(Kategori::getNama))
            ));
        }

        textIO.newStringInputReader().withMinLength(0).read("Tekan <enter> untuk kembali.");
        textIO.getTextTerminal().resetToBookmark("list-kategori");
        previousView.show();
    }
}
