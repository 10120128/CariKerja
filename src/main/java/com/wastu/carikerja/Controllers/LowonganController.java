package com.wastu.carikerja.Controllers;

import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.dao.DaoManager;
import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.table.TableUtils;
import com.wastu.carikerja.Enums.UserRole;
import com.wastu.carikerja.Helpers.DatabaseHelper;
import com.wastu.carikerja.Helpers.SessionHelper;
import com.wastu.carikerja.Models.Kategori;
import com.wastu.carikerja.Models.Lowongan;
import com.wastu.carikerja.Models.LowonganKategori;
import com.wastu.carikerja.Models.User;
import com.wastu.carikerja.Utils;

import java.sql.SQLException;
import java.util.List;
import java.util.Objects;

public class LowonganController {
    private final Dao<Lowongan, Long> lowonganDao;
    private static LowonganController instance;

    public static synchronized LowonganController getInstance() throws SQLException {
        if (instance == null) {
            instance = new LowonganController();
        }
        return instance;
    }

    /**
     * Ketika class pertama kali dijalankan, maka table Lowongan akan dibuat (apabila belum ada).
     *
     * @throws SQLException Jika koneksi gagal.
     */
    private LowonganController() throws SQLException {
        ConnectionSource connectionSource = DatabaseHelper.getInstance().getConnectionSource();
        this.lowonganDao = DaoManager.createDao(connectionSource, Lowongan.class);
        TableUtils.createTableIfNotExists(connectionSource, User.class);
    }

    /**
     * Mendapatkan Lowongan berdasarkan id.
     *
     * @param id id lowongan
     * @return Lowongan
     * @throws Exception Ketika user tidak ditemukan & koneksi gagal.
     */
    public Lowongan get(long id) throws Exception {
        Lowongan lowongan = lowonganDao.queryForId(id);
        if (lowongan == null) {
            throw new Exception("User tidak ditemukan");
        }
        return lowongan;
    }

    /**
     * Mendapatkan semua lowongan.
     *
     * @return List<Lowongan> semua lowongan.
     * @throws SQLException Ketika koneksi gagal.
     */
    public List<Lowongan> list() throws SQLException {
        return lowonganDao.queryForAll();
    }


    public void update(Lowongan lowongan) throws SQLException {
        lowonganDao.update(lowongan);
    }

    public void create(Lowongan lowongan) throws Exception {
        lowonganDao.create(lowongan);
    }

    public void delete(long id) throws SQLException {
        lowonganDao.deleteById(id);
    }
}
