package com.wastu.carikerja.Views.Item.Lowongan;

import com.github.freva.asciitable.AsciiTable;
import com.github.freva.asciitable.Column;
import com.github.freva.asciitable.HorizontalAlign;
import com.wastu.carikerja.Controllers.LowonganController;
import com.wastu.carikerja.Models.Lowongan;
import com.wastu.carikerja.Utils;
import com.wastu.carikerja.Views.View;
import com.wastu.carikerja.Views.ViewUtils;
import org.beryx.textio.TextIO;
import org.beryx.textio.TextIoFactory;

import java.sql.SQLException;
import java.util.Arrays;
import java.util.List;

public class ListLowonganView implements View {
    private static ListLowonganView instance;
    private View previousView;
    private final LowonganController lowonganController;
    private final TextIO textIO;

    private ListLowonganView(View previousView) throws SQLException {
        this.previousView = previousView;
        this.lowonganController = LowonganController.getInstance();
        this.textIO = ViewUtils.getInstance().getTextIO();;
    }

    public static synchronized ListLowonganView getInstance(View previousView) throws SQLException {
        if (instance == null) {
            instance = new ListLowonganView(previousView);
        }else{
            instance.previousView = previousView;
        }
        return instance;
    }

    /**
     * Menampilkan daftar lowongan, akan kembali ke view sebelumnya jika user memilih untuk kembali.
     *
     * @throws Exception jika terjadi error
     */
    @Override
    public void show() throws Exception {
        textIO.getTextTerminal().setBookmark("list-lowongan");
        Utils.showLoading(textIO);
        List<Lowongan> listLowongan = lowonganController.list();
        textIO.getTextTerminal().resetToBookmark("list-lowongan");

        if (listLowongan.isEmpty()) {
            textIO.getTextTerminal().println("Tidak ada lowongan yang tersedia");
        } else {
            View.showHeader("Lihat Daftar Lowongan", "");
            textIO.getTextTerminal().println(AsciiTable.getTable(listLowongan, Arrays.asList(
                    new Column().header("Id").headerAlign(HorizontalAlign.CENTER).dataAlign(HorizontalAlign.LEFT).with(lowongan -> Long.toString(lowongan.getId())),
                    new Column().header("Judul").headerAlign(HorizontalAlign.CENTER).dataAlign(HorizontalAlign.LEFT).with(lowongan -> Utils.toElipsis(lowongan.getJudul(), 40)),
                    new Column().header("Kategori").headerAlign(HorizontalAlign.CENTER).dataAlign(HorizontalAlign.LEFT).with(lowongan -> lowongan.getKategori().getNama()))
            ));
        }

        textIO.getTextTerminal().setBookmark("list-lowongan-input");
        while (true) {
            textIO.getTextTerminal().resetToBookmark("list-lowongan-input");
            textIO.getTextTerminal().println("\nMasukkan id lowongan untuk meliat detail, atau tulis <exit> untuk kembali:");
            String input = textIO.newStringInputReader().withMinLength(0).read(">");
            if (input.isEmpty()) {
                Utils.showMessageConfirmation("Input tidak boleh kosong", textIO);
                continue;
            }

            if (input.equalsIgnoreCase("exit")) {
                break;
            }

            if (!Utils.isLong(input)) {
                Utils.showMessageConfirmation("Id tidak valid", textIO);
                continue;
            }

            Lowongan lowongan = listLowongan.stream().filter(l -> l.getId() == Long.parseLong(input)).findFirst().orElse(null);
            if (lowongan == null) {
                Utils.showMessageConfirmation("Lowongan dengan id " + input + " tidak ditemukan", textIO);
                continue;
            }

            // Jika lowongan ditemukan, tampilkan detail lowongan
            textIO.getTextTerminal().resetToBookmark("list-lowongan");
            DetailLowonganView.getInstance(this, lowongan).show();
        }

        textIO.newStringInputReader().withMinLength(0).read("Tekan <enter> untuk kembali.");
        textIO.getTextTerminal().resetToBookmark("list-lowongan");
        previousView.show();
    }
}
