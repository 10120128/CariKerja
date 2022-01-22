package com.wastu.carikerja.Views.Item.Lowongan;

import com.wastu.carikerja.Controllers.LowonganController;
import com.wastu.carikerja.Models.Lowongan;
import com.wastu.carikerja.Utils;
import com.wastu.carikerja.Views.View;
import org.beryx.textio.TextIO;
import org.beryx.textio.TextIoFactory;

import java.sql.SQLException;

public class DeleteLowonganView implements View {
    private static DeleteLowonganView instance;
    private View previousView;
    private final LowonganController lowonganController;
    private final TextIO textIO;

    private DeleteLowonganView(View previousView) throws SQLException {
        this.previousView = previousView;
        this.lowonganController = LowonganController.getInstance();
        this.textIO = TextIoFactory.getTextIO();
    }

    public static synchronized DeleteLowonganView getInstance(View previousView) throws SQLException {
        if (instance == null) {
            instance = new DeleteLowonganView(previousView);
        }else{
            instance.previousView = previousView;
        }
        return instance;
    }

    /**
     * Menampilkan view untuk menghapus lowongan.
     *
     * <p>Aplikasi meminta inputan id lowongan yang akan dihapus, jika id lowongan ditemukan maka akan dihapus.
     *
     * @throws Exception jika terjadi kesalahan
     */
    @Override
    public void show() throws Exception {
        textIO.getTextTerminal().setBookmark("delete-lowongan");
        while (true) {
            textIO.getTextTerminal().resetToBookmark("delete-lowongan");
            View.showHeader("Hapus Lowongan", "");

            long id;
            textIO.getTextTerminal().setBookmark("delete-lowongan-id");
            while (true) {
                textIO.getTextTerminal().resetToBookmark("delete-lowongan-id");
                String idStr = textIO.newStringInputReader().withMinLength(0).read("Id lowongan yang akan dihapus: ");
                if (idStr.isEmpty()) {
                    Utils.showMessageConfirmation("ID tidak boleh kosong", textIO);
                    continue;
                }
                if (!Utils.isLong(idStr)) {
                    Utils.showMessageConfirmation("ID harus berupa angka", textIO);
                    continue;
                }
                if (!lowonganController.isExists(Long.parseLong(idStr))) {
                    Utils.showMessageConfirmation("ID tidak ditemukan", textIO);
                    continue;
                }
                id = Long.parseLong(idStr);
                break;
            }

            Lowongan lowongan = lowonganController.get(id);

            textIO.getTextTerminal().println();
            textIO.getTextTerminal().println("Id:\n" + lowongan.getId() + "\n");
            textIO.getTextTerminal().println("Judul:\n" + lowongan.getJudul()+ "\n");
            textIO.getTextTerminal().println("Deskripsi:\n" + lowongan.getDeskripsi() + "\n");
            textIO.getTextTerminal().println("Kategori:\n" + lowongan.getKategori().getNama() + "\n");
            textIO.getTextTerminal().println("Tempat:\n" + lowongan.getTempat() + "\n");
            textIO.getTextTerminal().println("Perusahaan:\n" + lowongan.getPerusahaan() + "\n");
            textIO.getTextTerminal().println("Tanggal Posting:\n" + lowongan.getTanggalPosting() + "\n");

            boolean confirmation = textIO.newBooleanInputReader().withDefaultValue(false).read("Apakah anda yakin ingin menghapus lowongan ini?");

            if (!confirmation) {
                continue;
            }

            lowonganController.delete(id);
            Utils.showMessageConfirmation("Lowongan berhasil dihapus", textIO);
            break;
        }

        textIO.getTextTerminal().resetToBookmark("delete-lowongan");
        previousView.show();
    }
}
