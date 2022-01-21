package com.wastu.carikerja.Views.Item.Lowongan;

import com.github.freva.asciitable.AsciiTable;
import com.github.freva.asciitable.Column;
import com.github.freva.asciitable.HorizontalAlign;
import com.wastu.carikerja.Controllers.KategoriController;
import com.wastu.carikerja.Controllers.LowonganController;
import com.wastu.carikerja.Models.Kategori;
import com.wastu.carikerja.Models.Lowongan;
import com.wastu.carikerja.Models.User;
import com.wastu.carikerja.Utils;
import com.wastu.carikerja.Views.View;
import org.beryx.textio.TextIO;
import org.beryx.textio.TextIoFactory;
import org.beryx.textio.swing.SwingTextTerminal;

import java.awt.*;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

public class CreateLowonganView implements View {
    private static CreateLowonganView instance;
    private final View previousView;
    private final LowonganController lowonganController;
    private final KategoriController kategoriController;
    private final TextIO textIO;

    private CreateLowonganView(View previousView) throws SQLException {
        this.previousView = previousView;
        this.lowonganController = LowonganController.getInstance();
        this.kategoriController = KategoriController.getInstance();
        this.textIO = TextIoFactory.getTextIO();
    }

    public static synchronized CreateLowonganView getInstance(View previousView) throws SQLException {
        if (instance == null) {
            instance = new CreateLowonganView(previousView);
        }
        return instance;
    }

    /**
     * Memunculkan terminal baru agar user dapat memilih kategori. Akan mengembalikan objek kategori yang dipilih.
     *
     * @return Kategori
     */
    private Kategori showSelectKategori() throws Exception {
        SwingTextTerminal childTerm = new SwingTextTerminal();
        childTerm.init();
        TextIO childTextIO = new TextIO(childTerm);

        // Show Kategori with table
        List<Kategori> listKategori = kategoriController.list();
        childTextIO.getTextTerminal().println(AsciiTable.getTable(listKategori, Arrays.asList(
                new Column().header("Id").headerAlign(HorizontalAlign.CENTER).dataAlign(HorizontalAlign.LEFT).with(kategori -> Long.toString(kategori.getId())),
                new Column().header("Nama").headerAlign(HorizontalAlign.CENTER).dataAlign(HorizontalAlign.LEFT).with(Kategori::getNama))));

        // Input kategori id
        long id;
        childTextIO.getTextTerminal().setBookmark("create-lowongan-kategori-id");
        while (true) {
            childTextIO.getTextTerminal().resetToBookmark("create-lowongan-kategori-id");
            String idStr = childTextIO.newStringInputReader().withMinLength(0).read("Masukkan id kategori yang dipilih: ");
            if (idStr.isEmpty()) {
                Utils.showMessageConfirmation("ID tidak boleh kosong", childTextIO);
                continue;
            }
            if (!Utils.isLong(idStr)) {
                Utils.showMessageConfirmation("ID harus berupa angka", childTextIO);
                continue;
            }
            if (!KategoriController.getInstance().isExists(Long.parseLong(idStr))) {
                Utils.showMessageConfirmation("ID tidak ditemukan", childTextIO);
                continue;
            }
            id = Long.parseLong(idStr);
            break;
        }
        Kategori kategori = KategoriController.getInstance().get(id);
        Utils.showMessageConfirmation("Kategori berhasil dipilih.", childTextIO);
        childTerm.dispose();

        return kategori;
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
                kategori = showSelectKategori();
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
