package com.wastu.carikerja.Views.Item.User;

import com.wastu.carikerja.Controllers.UserController;
import com.wastu.carikerja.Enums.UserRole;
import com.wastu.carikerja.Models.User;
import com.wastu.carikerja.Utils;
import com.wastu.carikerja.Views.View;
import org.beryx.textio.TextIO;
import org.beryx.textio.TextIoFactory;

import java.sql.SQLException;

public class UpdateUserView implements View {
    private static UpdateUserView instance;
    private View previousView;
    private final UserController userController;
    private final TextIO textIO;

    private UpdateUserView(View previousView) throws SQLException {
        this.previousView = previousView;
        this.userController = UserController.getInstance();
        this.textIO = TextIoFactory.getTextIO();
    }

    public static synchronized UpdateUserView getInstance(View previousView) throws SQLException {
        if (instance == null) {
            instance = new UpdateUserView(previousView);
        }else{
            instance.previousView = previousView;
        }
        return instance;
    }


    /**
     * Menampilkan view untuk update user.
     *
     * <p>Aplikasi akan meminta user untuk memasukkan id user yang akan diupdate, kemudian akan menampilkan form untuk
     * mengupdate data user. Inputan user memiliki nilai default data sebelumnya.
     *
     * @throws Exception jika terjadi kesalahan
     */
    @Override
    public void show() throws Exception {
        textIO.getTextTerminal().setBookmark("update-user");
        while (true) {
            textIO.getTextTerminal().resetToBookmark("update-user");
            View.showHeader("Update User", "Kosongkan field yang tidak ingin diupdate");
            String id = textIO.newStringInputReader().withMinLength(0).read("Masukkan ID user yang akan diupdate: ");

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
            String nama;
            textIO.getTextTerminal().setBookmark("update-user-nama");
            while (true) {
                nama = textIO.newStringInputReader().withDefaultValue(user.getNama()).read("Masukkan nama user");
                if (Utils.containsNumberic(nama)) {
                    Utils.showMessageConfirmation("Nama user tidak boleh terdapat angka", textIO);
                    textIO.getTextTerminal().resetToBookmark("update-user-nama");
                    continue;
                }
                break;
            }

            String email;
            textIO.getTextTerminal().setBookmark("update-user-email");
            while (true) {
                email = textIO.newStringInputReader().withDefaultValue(user.getEmail()).read("Masukkan email user");
                if (!Utils.isEmail(email)) {
                    Utils.showMessageConfirmation("Email tidak valid", textIO);
                    textIO.getTextTerminal().resetToBookmark("update-user-email");
                    continue;
                }
                break;
            }

            boolean isAdmin = textIO.newBooleanInputReader().withDefaultValue(user.isAdmin()).read("Apakah admin?");
            UserRole userRole = isAdmin ? UserRole.ADMIN : UserRole.USER;

            userController.update(Long.parseLong(id), nama, email, userRole);

            Utils.showMessageConfirmation("User berhasil diupdate", textIO);

            textIO.getTextTerminal().resetToBookmark("update-user");
            previousView.show();
            break;
        }
    }
}
