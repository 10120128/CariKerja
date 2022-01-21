package com.wastu.carikerja.Controllers;

import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.dao.DaoManager;
import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.table.TableUtils;
import com.wastu.carikerja.Helpers.DatabaseHelper;
import com.wastu.carikerja.Models.Kategori;

import java.sql.SQLException;
import java.util.Comparator;
import java.util.List;

public class KategoriController {
    private final Dao<Kategori, Long> kategoriDao;
    private static KategoriController instance;

    public static synchronized KategoriController getInstance() throws SQLException {
        if (instance == null) {
            instance = new KategoriController();
        }
        return instance;
    }

    private KategoriController() throws SQLException {
        ConnectionSource connectionSource = DatabaseHelper.getInstance().getConnectionSource();
        this.kategoriDao = DaoManager.createDao(connectionSource, Kategori.class);
        TableUtils.createTableIfNotExists(connectionSource, Kategori.class);
    }

    public Kategori get(long id) throws Exception {
        Kategori kategori = kategoriDao.queryForId(id);
        if (kategori == null) {
            throw new Exception("Kategori tidak ditemukan");
        }
        return kategori;
    }

    public List<Kategori> list() throws SQLException {
        return kategoriDao.queryForAll().stream().sorted(Comparator.comparing(Kategori::getId)).collect(java.util.stream.Collectors.toList());
    }

    public void update(long id, String nama) throws SQLException {
        Kategori kategori = kategoriDao.queryForId(id);
        kategori.setNama(nama);
        kategoriDao.update(kategori);
    }

    public void create(Kategori kategori) throws Exception {

        // Jika ada maka throw exception
        if (kategoriDao.queryForEq("nama", kategori.getNama()).stream().findFirst().orElse(null) != null) {
            throw new Exception("Kategori sudah ada");
        }

        kategoriDao.create(kategori);
    }

    public void delete(long id) throws SQLException {
        kategoriDao.deleteById(id);
    }

    public boolean isExists(long id) throws SQLException {
        return kategoriDao.queryForId(id) != null;
    }
}
