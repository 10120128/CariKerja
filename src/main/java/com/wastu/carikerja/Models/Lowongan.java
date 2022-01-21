package com.wastu.carikerja.Models;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

import java.util.Date;

@DatabaseTable(tableName = "lowongan")
public class Lowongan {
    @DatabaseField(generatedId = true)
    private long id;
    @DatabaseField()
    private String judul;
    @DatabaseField()
    private String perusahaan;
    @DatabaseField
    private String deskripsi;
    @DatabaseField(foreign = true, foreignAutoRefresh = true)
    Kategori kategori;
    @DatabaseField
    private String tempat;
    @DatabaseField
    private Date tanggalPosting;

    public Lowongan() {
    }

    public Lowongan(String judul, String perusahaan, String deskripsi, Kategori kategori, String tempat, Date tanggalPosting) {
        this.judul = judul;
        this.perusahaan = perusahaan;
        this.deskripsi = deskripsi;
        this.kategori = kategori;
        this.tempat = tempat;
        this.tanggalPosting = tanggalPosting;
    }

    public Lowongan(long id, String judul, String perusahaan, String deskripsi, Kategori kategori, String tempat, Date tanggalPosting) {
        this.id = id;
        this.judul = judul;
        this.perusahaan = perusahaan;
        this.deskripsi = deskripsi;
        this.kategori = kategori;
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

    public Kategori getKategori() {
        return kategori;
    }

    public void setKategori(Kategori kategori) {
        this.kategori = kategori;
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
