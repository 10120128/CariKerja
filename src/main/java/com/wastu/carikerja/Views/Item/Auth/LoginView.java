package com.wastu.carikerja.Views.Item.Auth;

import com.wastu.carikerja.Controllers.UserController;
import com.wastu.carikerja.Helpers.SessionHelper;
import com.wastu.carikerja.Utils;
import com.wastu.carikerja.Views.Menu.AdminMenuView;
import com.wastu.carikerja.Views.Menu.UserMenuView;
import com.wastu.carikerja.Views.View;
import org.beryx.textio.TextIO;
import org.beryx.textio.TextIoFactory;

import java.sql.SQLException;

public class LoginView implements View {
    private static LoginView instance;
    private final TextIO textIO;
    private final UserController userController;
    private View previousView;
    
    private LoginView(View previousView) throws SQLException {
        textIO = TextIoFactory.getTextIO();
        userController = UserController.getInstance();
        this.previousView = previousView;
    }
    
    public static LoginView getInstance(View previousView) throws SQLException {
        if(instance == null) {
            instance = new LoginView(previousView);
        }else{
            instance.previousView = previousView;
        }
        return instance;
    }
    
    @Override
    public void show() throws Exception {
        textIO.getTextTerminal().setBookmark("login");
        while (true) {
            View.showHeader("Login", "Silahkan masukkan informasi yang dibutuhkan");

            String email;
            textIO.getTextTerminal().setBookmark("email");
            while (true) {
                email = textIO.newStringInputReader().withMinLength(0).read("Masukkan email\t\t:");

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

                if (password.length() < 6) {
                    Utils.showMessageConfirmation("Password tidak valid", textIO);
                    textIO.getTextTerminal().resetToBookmark("password");
                    continue;
                }
                break;
            }

            try {
                userController.login(email, password);
                Utils.showMessageConfirmation("Login berhasil.", textIO);
                textIO.getTextTerminal().resetToBookmark("login");

                // Lempar ke view admin jika user admin
                if(SessionHelper.getInstance().isAdmin()) {
                    AdminMenuView.getInstance(this).show();
                }else {
                    UserMenuView.getInstance(this).show();
                }

                break;
            } catch (Exception e) {
                Utils.showMessageConfirmation(e.getMessage(), textIO);
            } finally {
                textIO.getTextTerminal().resetToBookmark("login");
            }
        }
    }
}
