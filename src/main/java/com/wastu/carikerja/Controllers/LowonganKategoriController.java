package com.wastu.carikerja.Controllers;

import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.dao.DaoManager;
import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.table.TableUtils;
import com.wastu.carikerja.Helpers.DatabaseHelper;
import com.wastu.carikerja.Models.Kategori;
import com.wastu.carikerja.Models.Lowongan;
import com.wastu.carikerja.Models.LowonganKategori;

import java.sql.SQLException;
import java.util.List;

public class LowonganKategoriController {
    private final Dao<LowonganKategori, Long> lowonganKategoriDao;
    private static LowonganKategoriController instance;

    public static synchronized LowonganKategoriController getInstance() throws SQLException {
        if (instance == null) {
            instance = new LowonganKategoriController();
        }
        return instance;
    }

    /**
     * Ketika class pertama kali dijalankan, maka table LowonganKategori akan dibuat (apabila belum ada).
     *
     * @throws SQLException Jika koneksi gagal.
     */
    private LowonganKategoriController() throws SQLException {
        ConnectionSource connectionSource = DatabaseHelper.getInstance().getConnectionSource();
        lowonganKategoriDao = DaoManager.createDao(connectionSource, LowonganKategori.class);
        TableUtils.createTableIfNotExists(connectionSource, LowonganKategori.class);
    }

    /**
     * Melihat semua LowonganKategori yang sesuai dengan lowongan yang diberikan.
     *
     * @param lowongan Lowongan yang dicari.
     * @return List lowongan kategori.
     */
    public List<LowonganKategori> getByLowongan(Lowongan lowongan) throws SQLException {
        return lowonganKategoriDao.queryForEq("lowongan_id", lowongan.getId());
    }

    /**
     * Melihat semua LowonganKategori yang sesuai dengan lowongan yang diberikan.
     *
     * @param kategori Kategori yang dicari.
     * @return List lowongan kategori.
     */
    public List<LowonganKategori> getByKategori(Kategori kategori) throws SQLException {
        return lowonganKategoriDao.queryForEq("kategori_id", kategori.getId());
    }

    public void create(LowonganKategori lowonganKategori) throws SQLException {
        lowonganKategoriDao.create(lowonganKategori);
    }

    public void update(LowonganKategori lowonganKategori) throws SQLException {
        lowonganKategoriDao.update(lowonganKategori);
    }

    public void delete(LowonganKategori lowonganKategori) throws SQLException {
        lowonganKategoriDao.delete(lowonganKategori);
    }
}
