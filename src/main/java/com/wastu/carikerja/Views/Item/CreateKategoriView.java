package com.wastu.carikerja.Views.Item;

import com.wastu.carikerja.Controllers.KategoriController;
import com.wastu.carikerja.Models.Kategori;
import com.wastu.carikerja.Utils;
import com.wastu.carikerja.Views.View;
import org.beryx.textio.TextIO;
import org.beryx.textio.TextIoFactory;

import java.sql.SQLException;

public class CreateKategoriView implements View {
    private static CreateKategoriView instance;
    private final View previousView;
    private final KategoriController kategoriController;
    private final TextIO textIO;

    private CreateKategoriView(View previousView) throws SQLException {
        this.previousView = previousView;
        this.kategoriController = KategoriController.getInstance();
        this.textIO = TextIoFactory.getTextIO();
    }

    public static synchronized CreateKategoriView getInstance(View previousView) throws SQLException {
        if (instance == null) {
            instance = new CreateKategoriView(previousView);
        }
        return instance;
    }

    /**
     * Menampilkan view untuk membuat kategori baru.
     *
     * @throws Exception jika terjadi kesalahan
     */
    @Override
    public void show() throws Exception {
        textIO.getTextTerminal().setBookmark("create-kategori");
        View.showHeader("Buat Kategori Baru", "");

        String nama;
        textIO.getTextTerminal().setBookmark("create-kategori-nama");
        while (true) {
            nama = textIO.newStringInputReader().withMinLength(0).read("Nama Kategori: ");
            if (nama.isBlank()) {
                Utils.showMessageConfirmation("Kategori tidak boleh kosong", textIO);
                textIO.getTextTerminal().resetToBookmark("create-kategori-nama");
                continue;
            }
            break;
        }

        kategoriController.create(new Kategori(nama));
        Utils.showMessageConfirmation("Kategori berhasil dibuat", textIO);

        textIO.getTextTerminal().resetToBookmark("create-kategori");
        previousView.show();
    }
}
