package com.wastu.carikerja.Views.Item.Lowongan;

import com.github.freva.asciitable.AsciiTable;
import com.github.freva.asciitable.Column;
import com.github.freva.asciitable.HorizontalAlign;
import com.wastu.carikerja.Controllers.KategoriController;
import com.wastu.carikerja.Controllers.LowonganController;
import com.wastu.carikerja.Models.Kategori;
import com.wastu.carikerja.Models.Lowongan;
import com.wastu.carikerja.Utils;
import com.wastu.carikerja.Views.View;
import org.beryx.textio.TextIO;
import org.beryx.textio.TextIoFactory;
import org.beryx.textio.swing.SwingTextTerminal;

import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

public class UpdateLowonganView implements View {
    private static UpdateLowonganView instance;
    private View previousView;
    private final LowonganController lowonganController;
    private final KategoriController kategoriController;
    private final TextIO textIO;

    private UpdateLowonganView(View previousView) throws SQLException {
        this.previousView = previousView;
        this.lowonganController = LowonganController.getInstance();
        this.kategoriController = KategoriController.getInstance();
        this.textIO = TextIoFactory.getTextIO();
    }

    public static synchronized UpdateLowonganView getInstance(View previousView) throws SQLException {
        if (instance == null) {
            instance = new UpdateLowonganView(previousView);
        }else{
            instance.previousView = previousView;
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
        childTextIO.getTextTerminal().setBookmark("update-lowongan-kategori-id");
        while (true) {
            childTextIO.getTextTerminal().resetToBookmark("update-lowongan-kategori-id");
            String idStr = childTextIO.newStringInputReader().withMinLength(0).read("MasuKkan id kategori yang dipilih: ");
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
        textIO.getTextTerminal().setBookmark("update-lowongan");

        while (true) {
            textIO.getTextTerminal().resetToBookmark("update-lowongan");

            // Cek apakah sudah mempunyai kategori
            if (kategoriController.list().isEmpty()) {
                Utils.showMessageConfirmation("Anda belum memiliki kategori. Silahkan buat kategori terlebih dahulu.", textIO);
                break;
            }

            View.showHeader("Ubah Lowongan Baru", "Kosongkan field yang tidak ingin diubah");


            // Input id dan cek apakah lowongan tersedia.
            long id;
            textIO.getTextTerminal().setBookmark("update-lowongan-id");
            while (true) {
                textIO.getTextTerminal().resetToBookmark("update-lowongan-id");
                String idStr = textIO.newStringInputReader().withMinLength(0).read("Masukkan id lowongan yang ingin diubah: ");
                if (idStr.isEmpty()) {
                    Utils.showMessageConfirmation("Id tidak boleh kosong", textIO);
                    continue;
                }
                if (!Utils.isLong(idStr)) {
                    Utils.showMessageConfirmation("Id harus berupa angka", textIO);
                    continue;
                }

                if (!LowonganController.getInstance().isExists(Long.parseLong(idStr))) {
                    Utils.showMessageConfirmation("Id tidak ditemukan", textIO);
                    continue;
                }
                id = Long.parseLong(idStr);
                break;
            }

            textIO.getTextTerminal().println();

            // Dapatkan lowongan
            Lowongan lowongan = LowonganController.getInstance().get(id);

            String judul;
            textIO.getTextTerminal().setBookmark("update-lowongan-judul");
            while (true) {
                judul = textIO.newStringInputReader().withDefaultValue(lowongan.getJudul()).withMinLength(0).read("Judul Lowongan");
                if (judul.isBlank()) {
                    Utils.showMessageConfirmation("Judul tidak boleh kosong", textIO);
                    textIO.getTextTerminal().resetToBookmark("update-lowongan-judul");
                    continue;
                }
                break;
            }
            textIO.getTextTerminal().println();

            String deskripsi;
            textIO.getTextTerminal().setBookmark("update-lowongan-deskripsi");
            while (true) {
                deskripsi = textIO.newStringInputReader().withDefaultValue(lowongan.getDeskripsi()).withMinLength(0).read("Deskripsi Lowongan");
                if (deskripsi.isBlank()) {
                    Utils.showMessageConfirmation("Deskripsi tidak boleh kosong", textIO);
                    textIO.getTextTerminal().resetToBookmark("update-lowongan-deskripsi");
                    continue;
                }
                break;
            }
            textIO.getTextTerminal().println();

            String perusahaan;
            textIO.getTextTerminal().setBookmark("update-lowongan-perusahaan");
            while (true) {
                perusahaan = textIO.newStringInputReader().withDefaultValue(lowongan.getPerusahaan()).withMinLength(0).read("Nama Perusahaan");
                if (perusahaan.isBlank()) {
                    Utils.showMessageConfirmation("Perusahaan tidak boleh kosong", textIO);
                    textIO.getTextTerminal().resetToBookmark("update-lowongan-perusahaan");
                    continue;
                }
                break;
            }
            textIO.getTextTerminal().println();

            String tempat;
            textIO.getTextTerminal().setBookmark("update-lowongan-tempat");
            while (true) {
                tempat = textIO.newStringInputReader().withDefaultValue(lowongan.getTempat()).withMinLength(0).read("Tempat");
                if (tempat.isBlank()) {
                    Utils.showMessageConfirmation("Tempat tidak boleh kosong", textIO);
                    textIO.getTextTerminal().resetToBookmark("update-lowongan-tempat");
                    continue;
                }
                break;
            }

            textIO.getTextTerminal().println();

            textIO.getTextTerminal().setBookmark("update-lowongan-kategori");
            Kategori kategori = lowongan.getKategori();
            while (true) {
                textIO.getTextTerminal().resetToBookmark("update-lowongan-kategori");
                textIO.getTextTerminal().println("Kategori:");
                textIO.getTextTerminal().println("> Id\t: " + kategori.getId());
                textIO.getTextTerminal().println("> Nama\t: " + kategori.getNama());
                if(kategori.getId() == lowongan.getKategori().getId()){
                    boolean input = textIO.newBooleanInputReader().withDefaultValue(false).read("Ubah kategori?");
                    if(input) {
                        textIO.getTextTerminal().println("Mengarahkan ke menu pilih kategori...");
                        kategori = showSelectKategori();
                        continue;
                    }
                }
                break;
            }

            textIO.getTextTerminal().println();

            textIO.getTextTerminal().setBookmark("update-lowongan-tanggal-posting");
            Date tanggalPosting;
            while (true) {
                textIO.getTextTerminal().resetToBookmark("update-lowongan-tanggal-posting");
                String tanggalStr = textIO.newStringInputReader().withDefaultValue(lowongan.getTanggalPosting()).withMinLength(0).read("Tanggal Posting (dd/MM/yyyy)");
                if (tanggalStr.isBlank()) {
                    Utils.showMessageConfirmation("Tempat tidak boleh kosong", textIO);
                    textIO.getTextTerminal().resetToBookmark("update-lowongan-tempat");
                    continue;
                }

                // validasi tanggal
                try {
                    tanggalPosting = new SimpleDateFormat("dd/MM/yyyy").parse(tanggalStr);
                }catch (ParseException e) {
                    Utils.showMessageConfirmation("Format tanggal salah", textIO);
                    continue;
                }

                break;
            }

            Lowongan updatedLowongan = new Lowongan(lowongan.getId(), judul, perusahaan, deskripsi, kategori, tempat, tanggalPosting);
            lowonganController.update(updatedLowongan);

            // Konfirmasi
            boolean confirmation = textIO.newBooleanInputReader().withDefaultValue(false).read("Apakah anda yakin data sudah benar?");

            if (!confirmation) {
                continue;
            }

            Utils.showMessageConfirmation("Kategori berhasil diubah", textIO);
            break;
        }

        textIO.getTextTerminal().resetToBookmark("update-lowongan");
        previousView.show();
    }
}
