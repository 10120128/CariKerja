package com.wastu.carikerja.Models;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

@DatabaseTable(tableName = "lowongan_kategori")
public class LowonganKategori {
    @DatabaseField(generatedId = true)
    private long id;

    @DatabaseField(foreign = true, columnName = "lowongan_id")
    private Lowongan lowongan;

    @DatabaseField(foreign = true, columnName = "kategori_id")
    private Kategori kategori;

    public LowonganKategori() {
    }


    public LowonganKategori(long id, Lowongan lowongan, Kategori kategori) {
        this.id = id;
        this.lowongan = lowongan;
        this.kategori = kategori;
    }

    public LowonganKategori(Lowongan lowongan, Kategori kategori) {
        this.lowongan = lowongan;
        this.kategori = kategori;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public Lowongan getLowongan() {
        return lowongan;
    }

    public void setLowongan(Lowongan lowongan) {
        this.lowongan = lowongan;
    }

    public Kategori getKategori() {
        return kategori;
    }

    public void setKategori(Kategori kategori) {
        this.kategori = kategori;
    }
}
