package com.company;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;

public class TransaksiRepository {
    private static final String TABEL = "transaksi_buku";

    // Create Transaksi Baru
    public boolean create(int idPerson, int idBuku) {
        LocalDate tanggalPinjam = LocalDate.now();
        LocalDate tanggalKembali = tanggalPinjam.plusDays(7);
        
        HashMap<String, String> map = new HashMap<>();
        map.put("peminjam", String.valueOf(idPerson));
        map.put("buku", String.valueOf(idBuku));
        map.put("tangga_pinjam", String.format("'%s'", tanggalPinjam));
        map.put("tangga_kembali", String.format("'%s'", tanggalKembali));
        map.put("sts_pengembalian", "'Belum Kembali'");

        return DBHelper.insert(TABEL, map);
    }

    // Update (Pengembalian Buku)
    public boolean updateStatusKembali(int idPerson, int idBuku) {
        HashMap<String, String> map = new HashMap<>();
        map.put("sts_pengembalian", "'Sudah Kembali'");
        // Hati-hati: klausa ini akan mengupdate semua history peminjaman buku X oleh user Y
        // Sebaiknya tambahkan kondisi "AND sts_pengembalian = 'Belum Kembali'"
        String clause = String.format("peminjam = %d AND buku = %d AND sts_pengembalian = 'Belum Kembali'", idPerson, idBuku);
        
        return DBHelper.update(TABEL, map, clause);
    }

    // Read All
    public ArrayList<TransaksiBuku> getAll() {
        String[] col = {"t.id", "p.nama", "b.judul_buku", "t.tangga_pinjam", "t.tangga_kembali", "t.sts_pengembalian"};
        String join = TABEL + " t JOIN buku b ON t.buku=b.id JOIN person p ON t.peminjam = p.id";
        ResultSet resultSet = DBHelper.selectColumn(join, col);
        return extractFromResultSet(resultSet);
    }

    // Read by Nama Peminjam
    public ArrayList<TransaksiBuku> getByPeminjam(String namaPeminjam) {
        String[] col = {"t.id", "p.nama", "b.judul_buku", "t.tangga_pinjam", "t.tangga_kembali", "t.sts_pengembalian"};
        String join = TABEL + " t JOIN buku b ON t.buku=b.id JOIN person p ON t.peminjam = p.id";
        String requirement = "p.nama LIKE '%" + namaPeminjam + "%'";
        ResultSet resultSet = DBHelper.selectColumn(join, col, requirement);
        return extractFromResultSet(resultSet);
    }

    // Helper Private Method
    private ArrayList<TransaksiBuku> extractFromResultSet(ResultSet resultSet) {
        ArrayList<TransaksiBuku> list = new ArrayList<>();
        try {
            while (resultSet.next()) {
                TransaksiBuku t = new TransaksiBuku();
                t.setId(resultSet.getInt("id"));
                t.setPeminjam(resultSet.getString("nama")); // Mengambil nama dari join
                t.setBuku(resultSet.getString("judul_buku")); // Mengambil judul dari join
                t.setTanggal_pinjam(resultSet.getDate("tangga_pinjam"));
                t.setTanggal_kembali(resultSet.getDate("tangga_kembali"));
                t.setSts_pengembalian(resultSet.getString("sts_pengembalian"));
                list.add(t);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }
}