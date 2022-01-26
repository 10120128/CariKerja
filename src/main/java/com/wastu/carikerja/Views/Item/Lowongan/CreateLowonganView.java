package com.wastu.carikerja.Views.Item.Lowongan;

import com.wastu.carikerja.Controllers.KategoriController;
import com.wastu.carikerja.Controllers.LowonganController;
import com.wastu.carikerja.Models.Kategori;
import com.wastu.carikerja.Models.Lowongan;
import com.wastu.carikerja.Utils;
import com.wastu.carikerja.Views.View;
import com.wastu.carikerja.Views.ViewUtils;
import org.beryx.textio.TextIO;

import java.sql.SQLException;
import java.util.Date;

public class CreateLowonganView implements View {
    private static CreateLowonganView instance;
    private View previousView;
    private final LowonganController lowonganController;
    private final KategoriController kategoriController;
    private final TextIO textIO;

    private CreateLowonganView(View previousView) throws SQLException {
        this.previousView = previousView;
        this.lowonganController = LowonganController.getInstance();
        this.kategoriController = KategoriController.getInstance();
        this.textIO = ViewUtils.getInstance().getTextIO();
        ;
    }

    public static synchronized CreateLowonganView getInstance(View previousView) throws SQLException {
        if (instance == null) {
            instance = new CreateLowonganView(previousView);
        } else {
            instance.previousView = previousView;
        }
        return instance;
    }

    /**
     * Menampilkan view untuk membuat lowongan baru.
     *
     * @throws Exception jika terjadi kesalahan
     */
    @Override
    public void show() throws Exception {
        textIO.getTextTerminal().setBookmark("create-lowongan");

        while (true) {
            textIO.getTextTerminal().resetToBookmark("create-lowongan");
            // Cek apakah sudah mempunyai kategori
            if (kategoriController.list().isEmpty()) {
                Utils.showMessageConfirmation("Anda belum memiliki kategori. Silahkan buat kategori terlebih dahulu.", textIO);
                break;
            }

            View.showHeader("Buat Lowongan Baru", "");

            String judul;
            textIO.getTextTerminal().setBookmark("create-lowongan-judul");
            while (true) {
                judul = textIO.newStringInputReader().withMinLength(0).read("Judul Lowongan\t\t:");
                if (judul.isBlank()) {
                    Utils.showMessageConfirmation("Judul tidak boleh kosong", textIO);
                    textIO.getTextTerminal().resetToBookmark("create-lowongan-judul");
                    continue;
                }
                break;
            }

            String deskripsi;
            textIO.getTextTerminal().setBookmark("create-lowongan-deskripsi");
            while (true) {
                deskripsi = textIO.newStringInputReader().withMinLength(0).read("Deskripsi Lowongan\t:");
                if (deskripsi.isBlank()) {
                    Utils.showMessageConfirmation("Deskripsi tidak boleh kosong", textIO);
                    textIO.getTextTerminal().resetToBookmark("create-lowongan-deskripsi");
                    continue;
                }
                break;
            }

            String perusahaan;
            textIO.getTextTerminal().setBookmark("create-lowongan-perusahaan");
            while (true) {
                perusahaan = textIO.newStringInputReader().withMinLength(0).read("Nama Perusahaan\t\t:");
                if (perusahaan.isBlank()) {
                    Utils.showMessageConfirmation("Perusahaan tidak boleh kosong", textIO);
                    textIO.getTextTerminal().resetToBookmark("create-lowongan-perusahaan");
                    continue;
                }
                break;
            }

            String tempat;
            textIO.getTextTerminal().setBookmark("create-lowongan-tempat");
            while (true) {
                tempat = textIO.newStringInputReader().withMinLength(0).read("Tempat\t\t\t:");
                if (tempat.isBlank()) {
                    Utils.showMessageConfirmation("Tempat tidak boleh kosong", textIO);
                    textIO.getTextTerminal().resetToBookmark("create-lowongan-tempat");
                    continue;
                }
                break;
            }

            textIO.getTextTerminal().setBookmark("create-lowongan-kategori");
            Kategori kategori = null;
            while (true) {
                textIO.getTextTerminal().resetToBookmark("create-lowongan-kategori");
                if (kategori != null) {
                    textIO.getTextTerminal().println("Kategori\t\t\t:");
                    textIO.getTextTerminal().println("> Id\t: " + kategori.getId());
                    textIO.getTextTerminal().println("> Nama\t: " + kategori.getNama());
                    break;
                }
                textIO.getTextTerminal().println("Mengarahkan ke menu pilih kategori...");
                kategori = ViewUtils.showSelectKategori();
            }

            Date tanggalPosting = new Date();

            Lowongan lowongan = new Lowongan(judul, perusahaan, deskripsi, kategori, tempat, tanggalPosting);

            // Konfirmasi
            boolean confirmation = textIO.newBooleanInputReader().withDefaultValue(false).read("Apakah anda yakin ingin membuat lowongan ini?");

            if (!confirmation) {
                continue;
            }

            lowonganController.create(lowongan);
            Utils.showMessageConfirmation("Kategori berhasil dibuat", textIO);
            break;
        }

        textIO.getTextTerminal().resetToBookmark("create-lowongan");
        previousView.show();
    }
}
