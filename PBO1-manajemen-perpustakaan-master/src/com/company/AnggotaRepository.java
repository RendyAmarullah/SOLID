package com.company;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;

public class AnggotaRepository {
    // Mengakses nama tabel dari parent class atau hardcode string "person"
    private static final String TABEL = "person";

    // Mengembalikan boolean agar UI yang menentukan pesan sukses/gagal
    public boolean create(Anggota anggota) {
        HashMap<String, String> map = new HashMap<>();
        // Mengambil data dari objek anggota
        map.put("nama", String.format("'%s'", anggota.getNama()));
        map.put("username", String.format("'%s'", anggota.getUsername()));
        map.put("password", String.format("'%s'", anggota.getPassword()));
        map.put("role_id", "3"); // Role 3 untuk Anggota

        return DBHelper.insert(TABEL, map);
    }

    public ArrayList<Anggota> getAll() {
        // Mengambil data dimana role_id = 3 (Anggota)
        ResultSet resultSet = DBHelper.selectAll(TABEL, "role_id = 3");
        ArrayList<Anggota> allAnggota = new ArrayList<>();
        
        try {
            while (resultSet.next()) {
                Anggota anggota = new Anggota();
                anggota.setIdPerson(resultSet.getInt("id"));
                anggota.setNama(resultSet.getString("nama"));
                anggota.setUsername(resultSet.getString("username"));
                anggota.setPassword(resultSet.getString("password"));
                anggota.setRole(resultSet.getInt("role_id"));
                allAnggota.add(anggota);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return allAnggota;
    }

    public Anggota getById(int idPerson) {
        String query = "id = " + idPerson + " AND role_id = 3";
        ResultSet resultSet = DBHelper.selectAll(TABEL, query);
        Anggota anggota = null;
        try {
            if (resultSet.next()) {
                anggota = new Anggota();
                anggota.setIdPerson(resultSet.getInt("id"));
                anggota.setNama(resultSet.getString("nama"));
                // set properti lainnya...
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return anggota;
    }

    // Logika bisnis pengecekan peminjaman dipindah ke sini
    public boolean hasOutstandingLoans(int idPerson) {
        String[] column = {"sts_pengembalian"};
        String clause = String.format("peminjam = %s ORDER BY tangga_pinjam DESC LIMIT 1", idPerson);
        // Note: Pastikan nama tabel transaksi dan kolom sesuai dengan DB Anda
        ResultSet resultSet = DBHelper.selectColumn("transaksi_buku", column, clause);
        
        try {
            if (resultSet.next()) {
                String status = resultSet.getString("sts_pengembalian");
                // Return true jika statusnya "Belum Kembali" (artinya ada tanggungan)
                return status.equals("Belum Kembali");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false; // Tidak ada tanggungan jika data tidak ditemukan
    }
}