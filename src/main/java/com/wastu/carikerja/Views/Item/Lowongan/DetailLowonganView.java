package com.wastu.carikerja.Views.Item.Lowongan;

import com.wastu.carikerja.Models.Lowongan;
import com.wastu.carikerja.Views.View;
import org.beryx.textio.TextIO;
import org.beryx.textio.TextIoFactory;

import java.sql.SQLException;

public class DetailLowonganView implements View {
    private static DetailLowonganView instance;
    private View previousView;
    private final TextIO textIO;
    private final Lowongan lowongan;

    private DetailLowonganView(View previousView, Lowongan lowongan) {
        this.previousView = previousView;
        this.lowongan = lowongan;
        this.textIO = TextIoFactory.getTextIO();
    }

    public static synchronized DetailLowonganView getInstance(View previousView, Lowongan lowongan) throws SQLException {
        if (instance == null) {
            instance = new DetailLowonganView(previousView, lowongan);
        } else {
            instance.previousView = previousView;
        }
        return instance;
    }

    /**
     * Menampilkan detail lowongan, akan kembali ke view sebelumnya jika user memilih untuk kembali.
     *
     * @throws Exception jika terjadi error
     */
    @Override
    public void show() throws Exception {
        textIO.getTextTerminal().setBookmark("detail-lowongan");

        View.showHeader("Detail Lowongan", "");

        // Tampilkan detail lowongan
        textIO.getTextTerminal().println("Judul:");
        textIO.getTextTerminal().println(lowongan.getJudul());
        textIO.getTextTerminal().println();
        textIO.getTextTerminal().println("Deskripsi:");
        textIO.getTextTerminal().println(lowongan.getDeskripsi());
        textIO.getTextTerminal().println();
        textIO.getTextTerminal().println("Kategori:");
        textIO.getTextTerminal().println(lowongan.getKategori().getNama());
        textIO.getTextTerminal().println();
        textIO.getTextTerminal().println("Perusahaan:");
        textIO.getTextTerminal().println(lowongan.getPerusahaan());
        textIO.getTextTerminal().println();
        textIO.getTextTerminal().println("Tempat:");
        textIO.getTextTerminal().println(lowongan.getTempat());
        textIO.getTextTerminal().println();
        textIO.getTextTerminal().println("Tanggal:");
        textIO.getTextTerminal().println(lowongan.getTanggalPosting());

        textIO.getTextTerminal().println();
        textIO.newStringInputReader().withMinLength(0).read("Tekan <enter> untuk kembali.");
        textIO.getTextTerminal().resetToBookmark("detail-lowongan");
        previousView.show();
    }
}
