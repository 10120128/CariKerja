package com.wastu.carikerja.Views.Item;

import com.github.freva.asciitable.AsciiTable;
import com.github.freva.asciitable.Column;
import com.github.freva.asciitable.HorizontalAlign;
import com.wastu.carikerja.Controllers.UserController;
import com.wastu.carikerja.Models.User;
import com.wastu.carikerja.Utils;
import com.wastu.carikerja.Views.View;
import org.beryx.textio.TextIO;
import org.beryx.textio.TextIoFactory;

import java.sql.SQLException;
import java.util.Arrays;
import java.util.List;

public class ListUserView implements View {
    private static ListUserView instance;
    private final View previousView;
    private final UserController userController;
    private final TextIO textIO;

    private ListUserView(View previousView) throws SQLException {
        this.previousView = previousView;
        this.userController = UserController.getInstance();
        this.textIO = TextIoFactory.getTextIO();
    }

    public static synchronized ListUserView getInstance(View previousView) throws SQLException {
        if (instance == null) {
            instance = new ListUserView(previousView);
        }
        return instance;
    }

    /**
     * Menampilkan daftar user, akan kembali ke view sebelumnya jika user memilih untuk kembali.
     *
     * @throws Exception jika terjadi error
     */
    @Override
    public void show() throws Exception {
        textIO.getTextTerminal().setBookmark("list-user");
        List<User> listUser = userController.list();

        if (listUser.isEmpty()) {
            textIO.getTextTerminal().println("Tidak ada user yang ditemukan");
        } else {
            textIO.getTextTerminal().println(AsciiTable.getTable(listUser, Arrays.asList(
                    new Column().header("Id").headerAlign(HorizontalAlign.CENTER).dataAlign(HorizontalAlign.LEFT).with(user -> Long.toString(user.getId())),
                    new Column().header("Nama").headerAlign(HorizontalAlign.CENTER).dataAlign(HorizontalAlign.LEFT).with(User::getNama),
                    new Column().header("Email").headerAlign(HorizontalAlign.CENTER).dataAlign(HorizontalAlign.LEFT).with(User::getEmail),
                    new Column().header("Role").headerAlign(HorizontalAlign.CENTER).dataAlign(HorizontalAlign.LEFT).with(user -> String.valueOf(user.getRole())))));
        }


        textIO.newStringInputReader().withMinLength(0).read("Tekan <enter> untuk kembali.");
        textIO.getTextTerminal().resetToBookmark("list-user");
        previousView.show();
    }
}
