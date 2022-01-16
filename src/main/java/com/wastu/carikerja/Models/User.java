package com.wastu.carikerja.Models;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;
import com.wastu.carikerja.Enums.UserRole;
import com.wastu.carikerja.Utils;

import java.security.NoSuchAlgorithmException;

@DatabaseTable(tableName = "user")
public class User {
    @DatabaseField(generatedId = true)
    private long id;
    @DatabaseField
    private String nama;
    @DatabaseField(unique = true)
    private String email;
    @DatabaseField
    private String password;
    @DatabaseField
    private UserRole role;

    public User() {
    }

    public User(long id, String nama, String email, String password, UserRole role) {
        this.id = id;
        this.nama = nama;
        this.email = email;
        this.password = password;
        this.role = role;
    }

    public User(String nama, String email, String password, UserRole role) {
        this.nama = nama;
        this.email = email;
        this.password = password;
        this.role = role;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getNama() {
        return nama;
    }

    public void setNama(String nama) {
        this.nama = nama;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public UserRole getRole() {
        return role;
    }

    public void setRole(UserRole role) {
        this.role = role;
    }


}
