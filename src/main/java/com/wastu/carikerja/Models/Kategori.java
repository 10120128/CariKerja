package com.wastu.carikerja.Models;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

@DatabaseTable(tableName = "kategori")
public class Kategori {
    @DatabaseField(generatedId = true)
    private long id;
    @DatabaseField(unique = true)
    private String nama;

    public Kategori() {
    }

    public Kategori(long id, String nama) {
        this.id = id;
        this.nama = nama;
    }

    public Kategori(String nama) {
        this.nama = nama;
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
}
