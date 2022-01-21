package com.wastu.carikerja.Views.Item.Kategori;

import com.wastu.carikerja.Controllers.KategoriController;
import com.wastu.carikerja.Models.Kategori;
import com.wastu.carikerja.Utils;
import com.wastu.carikerja.Views.View;
import org.beryx.textio.TextIO;
import org.beryx.textio.TextIoFactory;

import java.sql.SQLException;

public class DeleteKategoriView implements View {
    private static DeleteKategoriView instance;
    private View previousView;
    private final KategoriController kategoriController;
    private final TextIO textIO;

    private DeleteKategoriView(View previousView) throws SQLException {
        this.previousView = previousView;
        this.kategoriController = KategoriController.getInstance();
        this.textIO = TextIoFactory.getTextIO();
    }

    public static synchronized DeleteKategoriView getInstance(View previousView) throws SQLException {
        if (instance == null) {
            instance = new DeleteKategoriView(previousView);
        }else{
            instance.previousView = previousView;
        }
        return instance;
    }

    /**
     * Menampilkan view untuk menghapus kategori.
     *
     * <p>Aplikasi meminta inputan id kategori yang akan dihapus, jika id kategori ditemukan maka akan dihapus.
     *
     * @throws Exception jika terjadi kesalahan
     */
    @Override
    public void show() throws Exception {
        textIO.getTextTerminal().setBookmark("delete-kategori");
        while (true) {
            textIO.getTextTerminal().resetToBookmark("delete-kategori");
            View.showHeader("Hapus Kategori", "");

            long id;
            textIO.getTextTerminal().setBookmark("update-kategori-id");
            while (true) {
                textIO.getTextTerminal().resetToBookmark("update-kategori-id");
                String idStr = textIO.newStringInputReader().withMinLength(0).read("Id kategori yang akan dihapus: ");
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

            textIO.getTextTerminal().println();
            textIO.getTextTerminal().println("Id Kategori\t: " + kategori.getId());
            textIO.getTextTerminal().println("Nama Kategori\t: " + kategori.getNama());

            boolean confirmation = textIO.newBooleanInputReader().withDefaultValue(false).read("Apakah anda yakin ingin menghapus kategori ini?");

            if (!confirmation) {
                continue;
            }

            kategoriController.delete(id);
            Utils.showMessageConfirmation("Kategori berhasil dihapus", textIO);
            break;
        }

        textIO.getTextTerminal().resetToBookmark("delete-kategori");
        previousView.show();
    }
}
