package com.company;

import java.sql.SQLException;

public class ConsoleViewAdapter implements IView {

    private final View view;

    public ConsoleViewAdapter(View view) {
        this.view = view;
    }

    public void viewTambahPustakawan() throws SQLException { view.viewTambahPustakawan(); }
    public void viewDataPustakawan() throws SQLException { view.viewDataPustakawan(); }
    public void viewDataBuku() throws SQLException { view.viewDataBuku(); }
    public void viewDataTransaksiAnggota() throws SQLException { view.viewDataTransaksiAnggota(); }
    public void viewDataTransaksi() throws SQLException { view.viewDataTransaksi(); }
    public void viewTambahBuku() throws SQLException { view.viewTambahBuku(); }
    public void viewEditBuku() throws SQLException { view.viewEditBuku(); }
    public void viewHapusBuku() throws SQLException { view.viewHapusBuku(); }
    public void viewPeminjaman() throws SQLException { view.viewPeminjaman(); }
    public void viewPengembalian() throws SQLException { view.viewPengembalian(); }
    public void viewTambahAnggota() throws SQLException { view.viewTambahAnggota(); }
    public void viewSearchBerdasarkan(int mode) { view.viewSearchBerdasarkan(mode); }
}
