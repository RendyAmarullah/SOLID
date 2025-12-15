package com.company;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;

public class BukuRepository implements IBukuRepository {
    private static final String TABEL = "buku";

    @Override
    public boolean insert(Buku buku) {
        HashMap<String, String> map = new HashMap<>();
        map.put("judul_buku", String.format("'%s'", buku.getJudulBuku()));
        map.put("pengarang", String.format("'%s'", buku.getPengarang()));
        map.put("penerbit", String.format("'%s'", buku.getPenerbit()));
        map.put("ketersediaan", Integer.toString(buku.getKetersediaan()));
        
        return DBHelper.insert(TABEL, map);
    }

    @Override
    public boolean update(Buku buku) {
        HashMap<String, String> map = new HashMap<>();
        map.put("judul_buku", "'" + buku.getJudulBuku() + "'");
        map.put("pengarang", "'" + buku.getPengarang() + "'");
        map.put("penerbit", "'" + buku.getPenerbit() + "'");
        map.put("ketersediaan", Integer.toString(buku.getKetersediaan()));

        String clause = "id = " + buku.getIdBuku();
        return DBHelper.update(TABEL, map, clause);
    }

    @Override
    public boolean delete(int idBuku) {
        String clause = "id=" + idBuku;
        return DBHelper.delete(TABEL, clause);
    }

    @Override
    public ArrayList<Buku> getAll() {
        ResultSet resultSet = DBHelper.selectAll(TABEL);
        return extractBukuFromResultSet(resultSet);
    }

    @Override
    public Buku getById(int idBuku) {
        String query = "id = " + idBuku;
        ResultSet resultSet = DBHelper.selectAll(TABEL, query);
        ArrayList<Buku> hasil = extractBukuFromResultSet(resultSet);
        return hasil.isEmpty() ? null : hasil.get(0);
    }

    @Override
    public ArrayList<Buku> search(String column, String value) {
        String query = column + " like '%" + value + "%'";
        ResultSet resultSet = DBHelper.selectAll(TABEL, query);
        return extractBukuFromResultSet(resultSet);
    }

    // Helper private method untuk menghindari duplikasi kode (DRY Principle)
    private ArrayList<Buku> extractBukuFromResultSet(ResultSet resultSet) {
        ArrayList<Buku> listBuku = new ArrayList<>();
        try {
            while (resultSet != null && resultSet.next()) {
                Buku buku = new Buku();
                buku.setIdBuku(resultSet.getInt("id"));
                buku.setJudulBuku(resultSet.getString("judul_buku"));
                buku.setPengarang(resultSet.getString("pengarang"));
                buku.setPenerbit(resultSet.getString("penerbit"));
                buku.setKetersediaan(resultSet.getInt("ketersediaan"));
                listBuku.add(buku);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return listBuku;
    }
}