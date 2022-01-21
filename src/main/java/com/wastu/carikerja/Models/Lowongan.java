package com.wastu.carikerja.Models;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

import java.util.Date;

@DatabaseTable(tableName = "kategori")
public class Lowongan {
    @DatabaseField(generatedId = true)
    private long id;
    @DatabaseField(unique = true)
    private String judul;
    @DatabaseField()
    private String perusahaan;
    @DatabaseField
    private String deskripsi;
    @DatabaseField
    private String tempat;
    @DatabaseField
    private Date tanggalPosting;

    public Lowongan() {
    }

    public Lowongan(long id, String judul, String perusahaan, String deskripsi, String tempat, Date tanggalPosting) {
        this.id = id;
        this.judul = judul;
        this.perusahaan = perusahaan;
        this.deskripsi = deskripsi;
        this.tempat = tempat;
        this.tanggalPosting = tanggalPosting;
    }

    public Lowongan(String judul, String perusahaan, String deskripsi, String tempat, Date tanggalPosting) {
        this.judul = judul;
        this.perusahaan = perusahaan;
        this.deskripsi = deskripsi;
        this.tempat = tempat;
        this.tanggalPosting = tanggalPosting;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getJudul() {
        return judul;
    }

    public void setJudul(String judul) {
        this.judul = judul;
    }

    public String getPerusahaan() {
        return perusahaan;
    }

    public void setPerusahaan(String perusahaan) {
        this.perusahaan = perusahaan;
    }

    public String getDeskripsi() {
        return deskripsi;
    }

    public void setDeskripsi(String deskripsi) {
        this.deskripsi = deskripsi;
    }

    public String getTempat() {
        return tempat;
    }

    public void setTempat(String tempat) {
        this.tempat = tempat;
    }

    public Date getTanggalPosting() {
        return tanggalPosting;
    }

    public void setTanggalPosting(Date tanggalPosting) {
        this.tanggalPosting = tanggalPosting;
    }
}
