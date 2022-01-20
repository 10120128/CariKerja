package com.wastu.carikerja.View;

import com.j256.ormlite.support.ConnectionSource;
import com.wastu.carikerja.Handlers.SessionHandler;
import com.wastu.carikerja.Controllers.UserController;
import com.wastu.carikerja.Utils;
import org.beryx.textio.TextIO;

import java.sql.SQLException;

public class UserView extends BaseView {
    private static final String TITLE = "User";
    private final UserController controller;

    public UserView(ConnectionSource source, TextIO textIO) throws SQLException {
        super(textIO, TITLE);
        controller = new UserController(source);
    }

    @Override
    protected int getMenuSelection() {
        while (true) {
            super.viewHeader("Menu User");
            textIO.getTextTerminal().println("Selamat Datang " + SessionHandler.getInstance().getUser().getNama() + "!\n");
            textIO.getTextTerminal().println("1. Daftar Lowongan");
            textIO.getTextTerminal().println("2. Cari Lowongan");
            textIO.getTextTerminal().println("3. Filter Lowongan (Kategori)");
            textIO.getTextTerminal().println("4. Logout");
            int menu = textIO.newIntInputReader().withDefaultValue(1).read("Pilih menu: ");

            if (menu > 4) {
                Utils.showMessageConfirmation("Menu tidak tersedia", textIO);
                continue;
            }

            return menu;
        }
    }

    /**
     * {@inheritDoc}
     * Pilihan Menu:<br>
     * 1. Daftar Lowongan<br>
     * 2. Cari Lowongan<br>
     * 3. Filter Lowongan (Kategori)<br>
     * 4. Logout
     */
    @Override
    public void viewMenu() throws Exception {
        int selection = getMenuSelection();
        switch (selection) {
            case 1:
                // TODO: Daftar lowongan
                throw new Exception("Daftar Lowongan belum tersedia");
            case 2:
                // TODO: Cari lowongan
                throw new Exception("Cari lowongan belum tersedia");
            case 3:
                // TODO: Filter lowongan
                throw new Exception("Filter lowongan belum tersedia");
            case 4:
                SessionHandler.getInstance().logout();
                break;
            default:
                throw new Exception("Menu tidak tersedia");
        }

    }


//    public void tampilkanList(User currentUser, TextIO textIO) throws SQLException, InterruptedException {
//
//        if(currentUser.getRole() != UserRole.ADMIN){
//            textIO.getTextTerminal().println("Anda tidak memiliki hak akses untuk mengakses halaman ini");
//
//            textIO.newStringInputReader()
//                    .read("Tekan <Enter> untuk melanjutkan");
//            textIO.dispose();
//        }
//
//        List<User> listUser = controller.list();
//
//        String table = AsciiTable.getTable(listUser, Arrays.asList(
//                new Column().header("Id").dataAlign(HorizontalAlign.LEFT).with(user -> Long.toString(user.getId())),
//                new Column().header("Nama").dataAlign(HorizontalAlign.LEFT).with(User::getNama),
//                new Column().header("Email").dataAlign(HorizontalAlign.LEFT).with(User::getEmail),
//                new Column().header("Role").dataAlign(HorizontalAlign.LEFT).with(user -> user.getRole().toString())));
//
//        textIO.getTextTerminal().println(table);
//
//        textIO.newStringInputReader()
//                .read("Tekan <Enter> untuk melanjutkan");
//        textIO.dispose();
//    }
//
//    public void tampilkanEdit(User currentUser, TextIO textIO) throws Exception {
//
//        // Cek apakah user admin
//        if(currentUser.getRole() != UserRole.ADMIN){
//            textIO.getTextTerminal().println("Anda tidak memiliki hak akses untuk mengakses halaman ini");
//
//            textIO.newStringInputReader()
//                    .read("Tekan <Enter> untuk melanjutkan");
//            textIO.dispose();
//        }
//
//        textIO.getTextTerminal().setBookmark("edit");
//        while (true) {
//            textIO.getTextTerminal().println("=== EDIT ===\n");
//            long id = textIO.newIntInputReader()
//                    .read("Masukkan ID: ");
//            User user = controller.get(id);
//            if (user == null) {
//                textIO.getTextTerminal().println("User tidak ditemukan");
//                continue;
//            }
//
//            textIO.getTextTerminal().println("Apa yang ingin anda ubah?");
//            textIO.getTextTerminal().println("1. Nama");
//            textIO.getTextTerminal().println("2. Email");
//            textIO.getTextTerminal().println("3. Password");
//            textIO.getTextTerminal().println("4. Role");
//
//            int pilihan = textIO.newIntInputReader()
//                    .read("Pilihan: ");
//
//            switch (pilihan) {
//                case 1:
//                    while (true) {
//                        String nama = textIO.newStringInputReader()
//                                .read("Nama: ");
//                        if(nama.isEmpty()){
//                            textIO.getTextTerminal().println("Nama tidak boleh kosong");
//                            continue;
//                        }
//                        user.setNama(nama);
//                        break;
//                    }
//                    break;
//                case 2:
//                    while (true) {
//                        String email = textIO.newStringInputReader()
//                                .read("Email: ");
//                        if(email.isEmpty()){
//                            textIO.getTextTerminal().println("Email tidak boleh kosong");
//                            continue;
//                        }
//
//                        if(!Utils.cekEmail(email)){
//                            textIO.getTextTerminal().println("Email tidak valid");
//                            continue;
//                        }
//
//                        user.setEmail(email);
//                        break;
//                    }
//                    break;
//                case 3:
//                    while (true) {
//                        String password = textIO.newStringInputReader()
//                                .read("Password: ");
//                        if(password.isEmpty()){
//                            textIO.getTextTerminal().println("Password tidak boleh kosong");
//                            continue;
//                        }
//
//                        if(password.length() < 6){
//                            textIO.getTextTerminal().println("Password minimal 6 karakter");
//                        }
//
//
//
//                    }
//
//            }
//
//        }
//    }
}
