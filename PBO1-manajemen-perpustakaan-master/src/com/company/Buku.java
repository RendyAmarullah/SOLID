package com.company;

public class Buku {
    private int idBuku;
    private String judulBuku;
    private String pengarang;
    private String penerbit;
    private int ketersediaan;

    public Buku() {}

    public Buku(int idBuku, String judulBuku, String pengarang, String penerbit, int ketersediaan) {
        this.idBuku = idBuku;
        this.judulBuku = judulBuku;
        this.pengarang = pengarang;
        this.penerbit = penerbit;
        this.ketersediaan = ketersediaan;
    }

    // Getter dan Setter
    public int getIdBuku() { return idBuku; }
    public void setIdBuku(int idBuku) { this.idBuku = idBuku; }

    public String getJudulBuku() { return judulBuku; }
    public void setJudulBuku(String judulBuku) { this.judulBuku = judulBuku; }

    public String getPengarang() { return pengarang; }
    public void setPengarang(String pengarang) { this.pengarang = pengarang; }

    public String getPenerbit() { return penerbit; }
    public void setPenerbit(String penerbit) { this.penerbit = penerbit; }

    public int getKetersediaan() { return ketersediaan; }
    public void setKetersediaan(int ketersediaan) { this.ketersediaan = ketersediaan; }
}