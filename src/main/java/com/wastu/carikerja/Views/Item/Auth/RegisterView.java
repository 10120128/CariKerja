package com.wastu.carikerja.Views.Item.Auth;

import com.wastu.carikerja.Controllers.UserController;
import com.wastu.carikerja.Enums.UserRole;
import com.wastu.carikerja.Models.User;
import com.wastu.carikerja.Utils;
import com.wastu.carikerja.Views.Menu.UserMenuView;
import com.wastu.carikerja.Views.View;
import com.wastu.carikerja.Views.ViewUtils;
import org.beryx.textio.TextIO;
import org.beryx.textio.TextIoFactory;

import java.sql.SQLException;

public class RegisterView implements View {
    private final TextIO textIO;
    private static RegisterView instance;
    private View previousView;
    private final UserController userController;

    private RegisterView(View previousView) throws SQLException {
        textIO = ViewUtils.getInstance().getTextIO();;
        this.previousView = previousView;
        userController = UserController.getInstance();
    }

    public static synchronized RegisterView getInstance(View previousView) throws SQLException {
        if(instance == null){
            instance = new RegisterView(previousView);
        }else{
            instance.previousView = previousView;
        }
        return instance;
    }

    /**
     * Menampilkan form register.
     *
     * @throws Exception jika terjadi kesalahan.
     */
    @Override
    public void show() throws Exception {
        textIO.getTextTerminal().setBookmark("register");
        while (true) {
            View.showHeader("Daftar Akun", "Silahkan masukkan informasi yang dibutuhkan. Ketik <exit> untuk kembali.");

            String nama;
            textIO.getTextTerminal().setBookmark("nama");
            while (true) {
                nama = textIO.newStringInputReader().withMinLength(0).read("Masukkan nama\t\t:");

                if (nama.equalsIgnoreCase("exit")) {
                    textIO.getTextTerminal().resetToBookmark("register");
                    previousView.show();
                }

                if (nama.length() < 3) {
                    Utils.showMessageConfirmation("Nama tidak valid", textIO);
                    textIO.getTextTerminal().resetToBookmark("nama");
                    continue;
                }
                break;
            }

            String email;
            textIO.getTextTerminal().setBookmark("email");
            while (true) {
                email = textIO.newStringInputReader().withMinLength(0).read("Masukkan email\t\t:");

                if(email.equalsIgnoreCase("exit")){
                    textIO.getTextTerminal().resetToBookmark("register");
                    previousView.show();
                }

                if (!Utils.isEmail(email)) {
                    Utils.showMessageConfirmation("Email tidak valid", textIO);
                    textIO.getTextTerminal().resetToBookmark("email");
                    continue;
                }
                break;
            }

            String password;
            textIO.getTextTerminal().setBookmark("password");
            while (true) {
                password = textIO.newStringInputReader().withMinLength(0).withInputMasking(true).read("Masukkan password\t:");

                if(password.equalsIgnoreCase("exit")){
                    textIO.getTextTerminal().resetToBookmark("register");
                    previousView.show();
                }

                if (password.length() < 6) {
                    Utils.showMessageConfirmation("Password harus lebih dari 6 karakter", textIO);
                    textIO.getTextTerminal().resetToBookmark("password");
                    continue;
                }
                String confirmPassword;
                confirmPassword = textIO.newStringInputReader().withMinLength(0).withInputMasking(true).read("Konfirmasi password\t:");
                if (!password.equals(confirmPassword)) {
                    Utils.showMessageConfirmation("Password tidak sama", textIO);
                    textIO.getTextTerminal().resetToBookmark("password");
                    continue;
                }
                break;
            }

            // Konfirmasi
            boolean confirmation = textIO.newBooleanInputReader().withDefaultValue(true).read("Apakah data yang anda masukkan sudah benar?");
            if (!confirmation) {
                textIO.getTextTerminal().resetToBookmark("register");
                continue;
            }

            try {
                User user = new User(nama, email, password, UserRole.USER);
                userController.create(user);
                userController.login(email, password);
                Utils.showMessageConfirmation("\nAkun berhasil dibuat", textIO);
                textIO.getTextTerminal().resetToBookmark("register");

                // Pergi ke halaman utama user
                UserMenuView.getInstance(this).show();
                break;
            } catch (Exception e) {
                Utils.showMessageConfirmation(e.getMessage(), textIO);
            } finally {
                textIO.getTextTerminal().resetToBookmark("register");
            }
        }
    }
}
