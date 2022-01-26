package com.wastu.carikerja.Views;

import com.github.freva.asciitable.AsciiTable;
import com.github.freva.asciitable.Column;
import com.github.freva.asciitable.HorizontalAlign;
import com.wastu.carikerja.Controllers.KategoriController;
import com.wastu.carikerja.Models.Kategori;
import com.wastu.carikerja.Utils;
import org.beryx.textio.TextIO;
import org.beryx.textio.swing.SwingTextTerminal;

import java.awt.*;
import java.util.Arrays;
import java.util.List;

public class ViewUtils {
    private static ViewUtils instance;
    private SwingTextTerminal mainTerm;
    private TextIO textIO;

    private ViewUtils(){
        mainTerm = new SwingTextTerminal();
        mainTerm.init();
        textIO = new TextIO(mainTerm);
        mainTerm.getFrame().setPreferredSize(new Dimension(900, 500));
    }

    public static synchronized ViewUtils  getInstance() {
        if (instance == null) {
            instance = new ViewUtils();
        }
        return instance;
    }

    public TextIO getTextIO(){
        return textIO;
    }

    /**
     * Memunculkan terminal baru agar user dapat memilih kategori. Akan mengembalikan objek kategori yang dipilih.
     *
     * @return Kategori
     */
    public static Kategori showSelectKategori() throws Exception {
        SwingTextTerminal childTerm = new SwingTextTerminal();
        childTerm.init();
        TextIO childTextIO = new TextIO(childTerm);

        // Show Kategori with table
        List<Kategori> listKategori = KategoriController.getInstance().list();
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
}
