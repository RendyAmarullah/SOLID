package com.company;

import java.sql.SQLException;

public interface IView {
    void viewTambahPustakawan() throws SQLException;
    void viewDataPustakawan() throws SQLException;
    void viewDataBuku() throws SQLException;
    void viewDataTransaksiAnggota() throws SQLException;
    void viewDataTransaksi() throws SQLException;
    void viewTambahBuku() throws SQLException;
    void viewEditBuku() throws SQLException;
    void viewHapusBuku() throws SQLException;
    void viewPeminjaman() throws SQLException;
    void viewPengembalian() throws SQLException;
    void viewTambahAnggota() throws SQLException;
    void viewSearchBerdasarkan(int mode);
}
