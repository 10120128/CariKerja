package com.wastu.carikerja;

import org.beryx.textio.TextIO;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.SimpleDateFormat;
import java.util.Base64;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Utils {

    public static String encryptSha256(String input) {
        MessageDigest digest = null;
        try {
            digest = MessageDigest.getInstance("SHA-256");
            byte[] hash = digest.digest(input.getBytes(StandardCharsets.UTF_8));
            return Base64.getEncoder().encodeToString(hash);
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
            return "";
        }
    }

    public static boolean isEmail(String email) {
        Pattern pattern =
                Pattern.compile("^[A-Z0-9._%+-]+@[A-Z0-9.-]+\\.[A-Z]{2,6}$", Pattern.CASE_INSENSITIVE);
        Matcher matcher = pattern.matcher(email);
        return matcher.find();
    }

    public static void showMessageConfirmation(String message, TextIO textIO) {
        textIO.getTextTerminal().println();
        textIO.getTextTerminal().println(message);
        textIO.newStringInputReader().withMinLength(0).read("Tekan <enter> untuk melanjutkan.");
    }

    public static void showLoading(TextIO textIO) {
        textIO.getTextTerminal().println("Mohon tunggu sebentar...");
    }

    public static boolean isLong(String id) {
        try {
            Long.parseLong(id);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    public static String toElipsis(String text, int maxLength) {
        if (text.length() > maxLength) {
            return text.substring(0, maxLength) + "...";
        } else {
            return text;
        }
    }

    public static String convertDateToString(Date date) {
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        return sdf.format(date);
    }

    public static boolean containsNumberic(String input) {
        return input.matches(".*\\d+.*");
    }
}