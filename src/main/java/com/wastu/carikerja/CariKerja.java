package com.wastu.carikerja;

import com.j256.ormlite.support.ConnectionSource;
import com.wastu.carikerja.Controllers.UserController;
import com.wastu.carikerja.Enums.UserRole;
import com.wastu.carikerja.Handlers.SessionHandler;
import com.wastu.carikerja.Models.User;
import com.wastu.carikerja.View.AdminView;
import com.wastu.carikerja.View.UserView;
import org.beryx.textio.TextIO;

public class CariKerja {
    private final TextIO textIO;
    private final UserView userView;
    private final AdminView adminView;
    private final UserController userController;

    public CariKerja(ConnectionSource connectionSource, TextIO textIO) throws Exception {
        this.textIO = textIO;
        this.userView = new UserView(connectionSource, textIO);
        this.adminView = new AdminView(textIO);
        this.userController = new UserController(connectionSource);
    }

    /**
     * Menjalankan aplikasi dengan menampilkan menu utama.
     *
     * @throws Exception Mengembalikan exception jika terjadi kesalahan.
     */
    public void run() throws Exception {
        textIO.getTextTerminal().setBookmark("menu");
        while (true) {
            textIO.getTextTerminal().resetToBookmark("menu");
            int selection = getMainMenuSelection();
            textIO.getTextTerminal().resetToBookmark("menu");

            // Panggil menu
            try {
                switch (selection) {
                    case 1:
                        handleLoginView();
                        break;
                    case 2:
                        handleRegisterView();
                        break;
                    case 3:
                        handleExit();
                        break;
                }
            } catch (Exception e) {
                // Jika terjadi kesalahan maka ulang kembali menu utama.
                Utils.showMessageConfirmation("Terjadi kesalahan", textIO);
                continue;
            }


            // Cek status login, apabila terdeteksi logout maka kembali ke menu utama.
            if (!SessionHandler.getInstance().isLogin()) {
                continue;
            }

            break;
        }
    }

    private void handleExit() {
        textIO.dispose();
        System.exit(0);
    }

    private void handleLoginView() throws Exception {
        showLogin();
        User user = SessionHandler.getInstance().getUser();

        // Tampilkan menu sesuai masing-masing role.
        if (user.getRole() == UserRole.ADMIN) {
            adminView.viewMenu();
        } else {
            userView.viewMenu();
        }
    }

    /**
     * Menampilkan halaman login.
     */
    public void showLogin() {
        textIO.getTextTerminal().setBookmark("login");
        while (true) {
            textIO.getTextTerminal().println("Login");
            textIO.getTextTerminal().println("Silahkan masukan informasi yang dibutuhkan\n");

            String email;
            textIO.getTextTerminal().setBookmark("email");
            while (true) {
                email = textIO.newStringInputReader().withMinLength(0).read("Masukkan email: ");

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
                password = textIO.newStringInputReader().withMinLength(0).withInputMasking(true).read("Masukkan password: ");

                if (password.length() < 6) {
                    Utils.showMessageConfirmation("Password tidak valid", textIO);
                    textIO.getTextTerminal().resetToBookmark("password");
                    continue;
                }
                break;
            }

            try {
                userController.login(email, password);
                textIO.getTextTerminal().println("Login berhasil");
                break;
            } catch (Exception e) {
                Utils.showMessageConfirmation(e.getMessage(), textIO);
            } finally {
                textIO.getTextTerminal().resetToBookmark("login");
            }
        }
    }


    private void handleRegisterView() throws Exception {
        showRegister();
        User user = SessionHandler.getInstance().getUser();

        // Tampilkan menu sesuai masing-masing role.
        if (user.getRole() == UserRole.ADMIN) {
            adminView.viewMenu();
        } else {
            userView.viewMenu();
        }
    }

    /**
     * Menampilkan halaman register.
     */
    public void showRegister() {
        textIO.getTextTerminal().setBookmark("register");
        while (true) {
            textIO.getTextTerminal().println("Daftar Akun");
            textIO.getTextTerminal().println("Silahkan masukan informasi yang dibutuhkan.\n");

            String nama;
            textIO.getTextTerminal().setBookmark("nama");
            while (true) {
                nama = textIO.newStringInputReader().withMinLength(0).read("Masukkan nama: ");
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
                email = textIO.newStringInputReader().withMinLength(0).read("Masukkan email: ");
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
                password = textIO.newStringInputReader().withMinLength(0).withInputMasking(true).read("Masukkan password: ");
                if (password.length() < 6) {
                    Utils.showMessageConfirmation("Password harus lebih dari 6 karakter", textIO);
                    textIO.getTextTerminal().resetToBookmark("password");
                    continue;

                }
                String confirmPassword;
                confirmPassword = textIO.newStringInputReader().withMinLength(0).withInputMasking(true).read("Konfirmasi password: ");
                if (!password.equals(confirmPassword)) {
                    Utils.showMessageConfirmation("Password tidak sama", textIO);
                    textIO.getTextTerminal().resetToBookmark("password");
                    continue;
                }
                break;
            }

            try {
                User user = new User(nama, email, password, UserRole.USER);
                userController.create(user);
                userController.login(email, password);
                Utils.showMessageConfirmation("\nAkun berhasil dibuat", textIO);
                break;
            } catch (Exception e) {
                Utils.showMessageConfirmation(e.getMessage(), textIO);
            } finally {
                textIO.getTextTerminal().resetToBookmark("register");
            }
        }
    }


    /**
     * Menampilkan menu utama.<br>
     * Pilihan menu:<br>
     * 1. Login<br>
     * 2. Daftar<br>
     * 3. Keluar<br>
     *
     * @return Pilihan menu
     */
    private int getMainMenuSelection() {
        while (true) {
            textIO.getTextTerminal().println("Selamat Datang!\n");
            textIO.getTextTerminal().println("1. Login");
            textIO.getTextTerminal().println("2. Daftar");
            textIO.getTextTerminal().println("3. Keluar");
            int menu = textIO.newIntInputReader().withDefaultValue(1).read("Pilih menu: ");

            if (menu > 3) {
                Utils.showMessageConfirmation("Menu tidak tersedia", textIO);
                continue;
            }

            return menu;
        }
    }
}
