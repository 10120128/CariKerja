package com.wastu.carikerja.View;

import com.j256.ormlite.support.ConnectionSource;
import com.wastu.carikerja.Controllers.UserController;
import com.wastu.carikerja.CustomException.AuthenticationException;
import com.wastu.carikerja.Enums.UserRole;
import com.wastu.carikerja.Models.User;
import com.wastu.carikerja.Utils;
import org.beryx.textio.TextIO;
import org.beryx.textio.TextIoFactory;

import java.sql.SQLException;

public class UserView {
    private final UserController controller;

    public UserView(ConnectionSource source) throws SQLException {
        controller = new UserController(source);
    }

    public void tampilkanRegister(TextIO textIO) throws InterruptedException {
        textIO.getTextTerminal().setBookmark("register");
        while (true) {
            textIO.getTextTerminal().println("=== DAFTAR AKUN ===\n");

            String nama;
            textIO.getTextTerminal().setBookmark("nama");
            while (true) {
                nama = textIO.newStringInputReader()
                        .withMinLength(0)
                        .read("Masukan nama: ");
                if (nama.length() < 3) {
                    textIO.getTextTerminal().println("Nama tidak valid!");
                    Thread.sleep(1000);
                    textIO.getTextTerminal().resetToBookmark("nama");
                    continue;
                }
                break;
            }

            String email;
            textIO.getTextTerminal().setBookmark("email");
            while (true) {
                email = textIO.newStringInputReader()
                        .withMinLength(0)
                        .read("Masukan email: ");
                if (!Utils.cekEmail(email)) {
                    textIO.getTextTerminal().println("Email tidak valid!");
                    Thread.sleep(1000);
                    textIO.getTextTerminal().resetToBookmark("email");
                    continue;
                }
                break;
            }

            String password;
            textIO.getTextTerminal().setBookmark("password");
            while (true) {
                password = textIO.newStringInputReader()
                        .withMinLength(0)
                        .withInputMasking(true)
                        .read("Masukan password: ");
                if (password.length() < 6) {
                    textIO.getTextTerminal().println("Password tidak valid!");
                    Thread.sleep(1000);
                    textIO.getTextTerminal().resetToBookmark("password");
                    continue;

                }
                String confirmPassword;
                confirmPassword = textIO.newStringInputReader()
                        .withMinLength(0)
                        .withInputMasking(true)
                        .read("Konfirmasi password: ");
                if (!password.equals(confirmPassword)) {
                    textIO.getTextTerminal().println("Password tidak sama!");
                    Thread.sleep(1000);
                    textIO.getTextTerminal().resetToBookmark("password");
                    continue;
                }
                break;
            }



            try {
                User user = new User(nama, email, password, UserRole.USER);
                controller.daftar(user);
                textIO.getTextTerminal().println("Registrasi berhasil!");
                break;
            } catch (AuthenticationException | SQLException e) {
                textIO.getTextTerminal().println(e.getMessage());
                Thread.sleep(1000);
                textIO.getTextTerminal().resetToBookmark("register");
            }
        }
        Thread.sleep(1000);
        textIO.dispose();
    }

    public void tampilkanLogin(TextIO textIO) throws InterruptedException {
        textIO.getTextTerminal().setBookmark("login");
        while (true) {
            textIO.getTextTerminal().println("=== LOGIN ===\n");

            String email;
            textIO.getTextTerminal().setBookmark("email");
            while (true) {
                email = textIO.newStringInputReader()
                        .withMinLength(0)
                        .read("Masukan email: ");

                if (!Utils.cekEmail(email)) {
                    textIO.getTextTerminal().println("Email tidak valid!");
                    Thread.sleep(1000);
                    textIO.getTextTerminal().resetToBookmark("email");
                    continue;
                }
                break;
            }

            String password;
            textIO.getTextTerminal().setBookmark("password");
            while (true) {
                password = textIO.newStringInputReader()
                        .withMinLength(0)
                        .withInputMasking(true)
                        .read("Masukan password: ");

                if (password.length() < 6) {
                    textIO.getTextTerminal().println("Password tidak valid!");
                    Thread.sleep(1000);
                    textIO.getTextTerminal().resetToBookmark("password");
                    continue;
                }
                break;
            }

            try {
                controller.login(email, password);
                textIO.getTextTerminal().println("Login berhasil");
                break;
            } catch (AuthenticationException | SQLException e) {
                textIO.getTextTerminal().println(e.getMessage());
                Thread.sleep(1000);
                textIO.getTextTerminal().resetToBookmark("login");
            }
        }
        Thread.sleep(1000);
        textIO.dispose();

    }
}
