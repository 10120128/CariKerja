package com.wastu.carikerja.Views.Item.User;

import com.wastu.carikerja.Controllers.UserController;
import com.wastu.carikerja.Models.User;
import com.wastu.carikerja.Utils;
import com.wastu.carikerja.Views.View;
import com.wastu.carikerja.Views.ViewUtils;
import org.beryx.textio.TextIO;
import org.beryx.textio.TextIoFactory;

import java.sql.SQLException;

public class DeleteUserView implements View {
    private static DeleteUserView instance;
    private View previousView;
    private final UserController userController;
    private final TextIO textIO;

    private DeleteUserView(View previousView) throws SQLException {
        this.previousView = previousView;
        this.userController = UserController.getInstance();
        this.textIO = ViewUtils.getInstance().getTextIO();;
    }

    public static synchronized DeleteUserView getInstance(View previousView) throws SQLException {
        if (instance == null) {
            instance = new DeleteUserView(previousView);
        }else{
            instance.previousView = previousView;
        }
        return instance;
    }


    /**
     * Menampilkan view untuk delete user.
     *
     * <p>Aplikasi akan meminta user untuk memasukkan id user yang akan dihapus.
     *
     * @throws Exception jika terjadi kesalahan
     */
    @Override
    public void show() throws Exception {
        textIO.getTextTerminal().setBookmark("delete-user");
        while (true) {
            textIO.getTextTerminal().resetToBookmark("delete-user");
            View.showHeader("Hapus User", "");
            String id = textIO.newStringInputReader().withMinLength(0).read("Masukkan ID user yang akan dihapus: ");

            if (id.isBlank()) {
                Utils.showMessageConfirmation("ID user tidak boleh kosong", textIO);
                continue;
            }

            if (!Utils.isLong(id)) {
                Utils.showMessageConfirmation("ID user harus berupa angka", textIO);
                continue;
            }

            if (!userController.isUserExists(Long.parseLong(id))) {
                Utils.showMessageConfirmation("ID user tidak ditemukan", textIO);
                continue;
            }

            User user = userController.get(Long.parseLong(id));

            textIO.getTextTerminal().println();
            textIO.getTextTerminal().println("Id User\t\t: " + user.getId());
            textIO.getTextTerminal().println("Nama User\t: " + user.getNama());
            textIO.getTextTerminal().println("Email User\t: " + user.getEmail());
            textIO.getTextTerminal().println("Role User\t: " + user.getRole().toString());
            boolean confirmation = textIO.newBooleanInputReader().withDefaultValue(false).read("Apakah anda yakin ingin menghapus user ini?");

            if (!confirmation) {
                continue;
            }

            userController.delete(Long.parseLong(id));
            Utils.showMessageConfirmation("User berhasil dihapus", textIO);

            textIO.getTextTerminal().resetToBookmark("delete-user");
            previousView.show();
            break;
        }
    }
}
