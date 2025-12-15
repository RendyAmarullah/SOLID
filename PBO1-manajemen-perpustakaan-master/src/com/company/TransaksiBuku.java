package com.company;

import java.sql.Date;

public class TransaksiBuku { // Hapus "implements Crud"
    private int id;
    private String peminjam; // Bisa diubah jadi int peminjamId jika mau lebih rapi
    private String buku;     // Bisa diubah jadi int bukuId
    private Date tanggal_pinjam;
    private Date tanggal_kembali;
    private String sts_pengembalian;

    public TransaksiBuku() {
    }

    public TransaksiBuku(int id, String peminjam, String buku, Date tanggal_pinjam, Date tanggal_kembali, String sts_pengembalian) {
        this.id = id;
        this.peminjam = peminjam;
        this.buku = buku;
        this.tanggal_pinjam = tanggal_pinjam;
        this.tanggal_kembali = tanggal_kembali;
        this.sts_pengembalian = sts_pengembalian;
    }

    // Getter dan Setter Saja
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public String getPeminjam() { return peminjam; }
    public void setPeminjam(String peminjam) { this.peminjam = peminjam; }

    public String getBuku() { return buku; }
    public void setBuku(String buku) { this.buku = buku; }

    public Date getTanggal_pinjam() { return tanggal_pinjam; }
    public void setTanggal_pinjam(Date tanggal_pinjam) { this.tanggal_pinjam = tanggal_pinjam; }

    public Date getTanggal_kembali() { return tanggal_kembali; }
    public void setTanggal_kembali(Date tanggal_kembali) { this.tanggal_kembali = tanggal_kembali; }

    public String getSts_pengembalian() { return sts_pengembalian; }
    public void setSts_pengembalian(String sts_pengembalian) { this.sts_pengembalian = sts_pengembalian; }
}