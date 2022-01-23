package com.wastu.carikerja.user;

enum Role {
    ADMIN,
    USER
}

public class User {
    private String id;
    private String nama;
    private String email;
    private String password;
    private Role role;
}
