package com.wastu.carikerja.Views.Item.Kategori;

import com.wastu.carikerja.Controllers.KategoriController;
import com.wastu.carikerja.Models.Kategori;
import com.wastu.carikerja.Utils;
import com.wastu.carikerja.Views.View;
import org.beryx.textio.TextIO;
import org.beryx.textio.TextIoFactory;

import java.sql.SQLException;

public class UpdateKategoriView implements View {
    private static UpdateKategoriView instance;
    private final View previousView;
    private final KategoriController kategoriController;
    private final TextIO textIO;

    private UpdateKategoriView(View previousView) throws SQLException {
        this.previousView = previousView;
        this.kategoriController = KategoriController.getInstance();
        this.textIO = TextIoFactory.getTextIO();
    }

    public static synchronized UpdateKategoriView getInstance(View previousView) throws SQLException {
        if (instance == null) {
            instance = new UpdateKategoriView(previousView);
        }
        return instance;
    }

    /**
     * Menampilkan view untuk mengubah kategori baru.
     *
     * <p>Aplikasi akan meminta user untuk memasukkan id kategori yang akan diupdate, kemudian akan menampilkan form untuk
     * mengupdate kategori. Inputan  memiliki nilai default data sebelumnya.
     *
     * @throws Exception jika terjadi kesalahan
     */
    @Override
    public void show() throws Exception {
        textIO.getTextTerminal().setBookmark("update-kategori");
        View.showHeader("Update Kategori", "Kosongkan field yang tidak ingin diupdate");

        long id;
        while (true) {
            textIO.getTextTerminal().setBookmark("update-kategori-id");
            String idStr = textIO.newStringInputReader().withMinLength(0).read("Masukan ID Kategori yang akan diubah: ");
            if (idStr.isEmpty()) {
                Utils.showMessageConfirmation("ID tidak boleh kosong", textIO);
                continue;
            }
            if (!Utils.isLong(idStr)) {
                Utils.showMessageConfirmation("ID harus berupa angka", textIO);
                continue;
            }
            if (!KategoriController.getInstance().isExists(Long.parseLong(idStr))) {
                Utils.showMessageConfirmation("ID tidak ditemukan", textIO);
                continue;
            }
            id = Long.parseLong(idStr);
            break;
        }

        Kategori kategori = kategoriController.get(id);

        String nama;
        textIO.getTextTerminal().setBookmark("update-kategori-nama");
        while (true) {
            nama = textIO.newStringInputReader().withDefaultValue(kategori.getNama()).withMinLength(0).read("Nama Kategori: ");
            if (nama.isBlank()) {
                Utils.showMessageConfirmation("Kategori tidak boleh kosong", textIO);
                textIO.getTextTerminal().resetToBookmark("update-kategori-nama");
                continue;
            }
            break;
        }

        kategoriController.update(id, nama);
        Utils.showMessageConfirmation("Kategori berhasil diupdate", textIO);

        textIO.getTextTerminal().resetToBookmark("update-kategori");
        previousView.show();
    }
}
