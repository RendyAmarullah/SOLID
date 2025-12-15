package com.company;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Scanner;

public class View {

    // Inisialisasi Repository
    private IBukuRepository bukuRepo = new BukuRepository();
    private AnggotaRepository anggotaRepo = new AnggotaRepository();
    // Tambahkan Repository Transaksi
    private TransaksiRepository transaksiRepo = new TransaksiRepository();

    public void viewTambahAnggota() {
        Scanner scn = new Scanner(System.in);
        System.out.println("Masukan Data Anggota Baru");
        System.out.print("Nama        : ");
        String nama = scn.nextLine();
        System.out.print("Username    : ");
        String username = scn.nextLine();
        System.out.print("Password    : ");
        String password = scn.nextLine();

        Anggota anggota = new Anggota(0, nama, username, password, 3);
        
        boolean isSuccess = anggotaRepo.create(anggota);

        if (isSuccess) {
            System.out.println("Data Anggota Baru Telah Ditambahkan");
        } else {
            System.out.println("Data Anggota Baru gagal ditambah");
        }
    }

    public void viewDataBuku() {
        ArrayList<Buku> allBuku = bukuRepo.getAll();
        
        System.out.println("NO | Judul Buku | Pengarang | Penerbit | Jumlah");
        int i = 1;
        for (Buku someBuku : allBuku) {
            System.out.println(String.format("%s | %s | %s | %s | %s"
                    , i, someBuku.getJudulBuku(), someBuku.getPengarang(), someBuku.getPenerbit(), someBuku.getKetersediaan()));
            i++;
        }
    }

    public void viewTambahBuku() {
        Scanner scn = new Scanner(System.in);
        try {
            System.out.println("Inputkan Data Buku");
            System.out.print("Judul Buku : ");
            String judul = scn.nextLine();
            System.out.print("Pengarang : ");
            String pengarang = scn.nextLine();
            System.out.print("Penerbit : ");
            String penerbit = scn.nextLine();
            System.out.print("Jumlah Buku : ");
            int jumlah = scn.nextInt();
            
            Buku buku = new Buku(0, judul, pengarang, penerbit, jumlah);
            
            if(bukuRepo.insert(buku)){
                System.out.println("Buku Berhasil Ditambahkan");
            } else {
                System.out.println("Buku Gagal Ditambahkan");
            }
        } catch (Exception e) {
            System.out.println("Inputan Anda Tidak Valid");
        }
    }

    public void viewHapusBuku() {
        Scanner scn = new Scanner(System.in);
        System.out.println("Masukan ID buku yang ingin dihapus!");
        try {
            System.out.print("ID Buku : ");
            int id = scn.nextInt();
            
            if(bukuRepo.delete(id)){
                System.out.println("Buku Berhasil Dihapus");
            } else {
                System.out.println("Buku Gagal Dihapus, ID mungkin salah");
            }
        } catch (Exception e) {
            System.out.println("Inputan Anda Tidak Valid");
        }
    }

    public void viewEditBuku() {
        Scanner sc = new Scanner(System.in);
        Scanner scn = new Scanner(System.in);
        System.out.print("Masukan Id Buku yang Ingin di Edit : ");
        int id = sc.nextInt();
        System.out.print("Judul Buku : ");
        String judul = scn.nextLine();
        System.out.print("Pengarang : ");
        String pengarang = scn.nextLine();
        System.out.print("Penerbit : ");
        String penerbit = scn.nextLine();
        System.out.print("Jumlah : ");
        int jumlah = sc.nextInt();
        
        Buku buku = new Buku(id, judul, pengarang, penerbit, jumlah);
        
        if(bukuRepo.update(buku)){
            System.out.println("Buku Berhasil Diedit");
        } else {
            System.out.println("Buku Gagal Diedit");
        }
    }

    public void viewSearchBerdasarkan(int key) {
        Scanner scn = new Scanner(System.in);
        ArrayList<Buku> hasil;
        String column = "";
        
        if (key == 1) {
            System.out.print("Masukan Judul Buku : ");
            column = "judul_buku";
        } else if (key == 2) {
            System.out.print("Masukan Judul Penerbit : ");
            column = "penerbit";
        } else {
            System.out.print("Masukan Judul Pengarang : ");
            column = "pengarang";
        }

        String keyword = scn.nextLine();
        
        hasil = bukuRepo.search(column, keyword);

        if (hasil.isEmpty()) {
            System.out.println("Buku Masih Belum Tersedia");
        } else {
            int i = 1;
            for (Buku someBuku : hasil) {
                System.out.println(String.format("%s | %s | %s | %s | %s"
                        , i, someBuku.getJudulBuku(), someBuku.getPengarang(), someBuku.getPenerbit(), someBuku.getKetersediaan()));
                i++;
            }
        }
    }

    //View Pustakawan (Belum di-refactor, masih menggunakan cara lama sesuai Pustakawan.java)
    public void viewDataPustakawan() throws SQLException {
        Pustakawan pustakawan = new Pustakawan();
        ArrayList<Pustakawan> allpustakawan = new ArrayList<>(pustakawan.read());
        int i = 1;
        System.out.println("No | Nama | Username | Password");
        for (Pustakawan pustakawan1 : allpustakawan) {
            System.out.println(String.format("%s | %s | %s | %s ", 
                    i, pustakawan1.getNama(), pustakawan1.getUsername(), pustakawan1.getPassword()));
            i++;
        }
    }

    public void viewTambahPustakawan() {
        Scanner sc = new Scanner(System.in);
        System.out.println("Inputkan data Pustakawan baru");
        System.out.print("Nama: ");
        String nama = sc.nextLine();
        System.out.print("Username: ");
        String username = sc.nextLine();
        System.out.print("Password: ");
        String password = sc.nextLine();
        Pustakawan pustakawan = new Pustakawan(0, nama, username, password, 2);
        pustakawan.create();
    }

    // --- REVISI: Menggunakan TransaksiRepository ---
    
    public void viewDataTransaksiAnggota() {
        Scanner sc = new Scanner(System.in);
        System.out.print("Inputkan Nama Anggota :");
        String nama = sc.nextLine();
        
        // Menggunakan Repository
        ArrayList<TransaksiBuku> allTransaksi = transaksiRepo.getByPeminjam(nama);
        
        System.out.println("NO | Judul Buku | Pengarang | Penerbit | Jumlah");
        int i = 1;
        for (TransaksiBuku transaksi : allTransaksi) {
            System.out.println(String.format("%s | %s | %s | %s | %s | %s"
                    , i, transaksi.getPeminjam(), transaksi.getBuku(), transaksi.getTanggal_pinjam(), transaksi.getTanggal_kembali(), transaksi.getSts_pengembalian()));
            i++;
        }
    }

    public void viewPeminjaman() {
        Scanner sc = new Scanner(System.in);
        ArrayList<Buku> bukus = new ArrayList<>();
        
        System.out.print("Masukan Jumlah Buku yang dipinjam(Maksimal 3) : ");
        int jumlah = sc.nextInt();
        
        if (jumlah > 3) {
            System.out.println("Maksimal buku yang dipinjam adalah 3");
        } else {
            System.out.print("Masukan Id Anggota : ");
            int idAnggota = sc.nextInt();
            
            Anggota anggota = anggotaRepo.getById(idAnggota);

            if (anggota != null) {
                if (!anggotaRepo.hasOutstandingLoans(idAnggota)) {
                    for (int i = 0; i < jumlah; i++) {
                        System.out.print("Masukan id Buku yang dipinjam : ");
                        int idBuku = sc.nextInt();
                        
                        Buku buku = bukuRepo.getById(idBuku);
                        
                        if (buku != null) {
                            bukus.add(buku);
                            // Simpan Transaksi Menggunakan Repository
                            transaksiRepo.create(idAnggota, idBuku);
                        } else {
                            System.out.println("ID Buku " + idBuku + " tidak ditemukan.");
                        }
                    }
                    
                    try {
                        System.out.print("Nama : " + anggota.getNama());
                        System.out.printf(" Telah Meminjam Buku sebanyak " + jumlah + " Yaitu dengan rincian berikut :\n");
                        int i = 1;
                        for (Buku buk : bukus) {
                            System.out.println(String.format("%s. %s | %s | %s", i, buk.getJudulBuku(), buk.getPengarang(), buk.getPenerbit()));
                            i++;
                            buk.setKetersediaan(buk.getKetersediaan() - 1);
                            bukuRepo.update(buk);
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                } else {
                    System.out.println("Anggota Masih Memiliki Tanggungan Buku yang belum dikembalikan");
                }
            } else {
                System.out.println("Id Anggota tidak ditemukan");
            }
        }
    }

    public void viewPengembalian() throws SQLException {
        Scanner sc = new Scanner(System.in);
        int jumlahPinjaman = 0;
        System.out.print("Masukan Id Anggota : ");
        int idAnggota = sc.nextInt();
        
        // Menggunakan helper langsung untuk hitung jumlah (opsional bisa dipindah ke repo)
        String colum[] = {"COUNT(sts_pengembalian) AS total"};
        String constrain = "peminjam = " + idAnggota + " and sts_pengembalian = 'Belum Kembali' GROUP BY peminjam";
        ResultSet resultSet = DBHelper.selectColumn("transaksi_buku", colum, constrain);
        while (resultSet.next()) {
            jumlahPinjaman = resultSet.getInt("total");
        }
        
        int i = 0;
        while (i < jumlahPinjaman) {
            System.out.print("Masukan ID Buku yang dipinjam :");
            int idBuku = sc.nextInt();
            
            Buku buku = bukuRepo.getById(idBuku);
            
            if (buku != null) {
                // Update Stok Buku
                buku.setKetersediaan(buku.getKetersediaan() + 1);
                bukuRepo.update(buku);
                
                // Update Status Transaksi Menggunakan Repository
                boolean success = transaksiRepo.updateStatusKembali(idAnggota, idBuku);
                if(success) {
                    System.out.println("Buku berhasil dikembalikan.");
                } else {
                    System.out.println("Gagal mengupdate status transaksi.");
                }
                
                i++;
            } else {
                System.out.println("ID Buku tidak ditemukan dalam database.");
            }
        }
    }

    public void viewDataTransaksi() throws SQLException {
        // Menggunakan Repository
        ArrayList<TransaksiBuku> allTransaksi = transaksiRepo.getAll();
        
        System.out.println("NO | Judul Buku | Pengarang | Penerbit | Jumlah");
        int i = 1;
        for (TransaksiBuku transaksi : allTransaksi) {
            System.out.println(String.format("%s | %s | %s | %s | %s | %s"
                    , i, transaksi.getPeminjam(), transaksi.getBuku(), transaksi.getTanggal_pinjam(), transaksi.getTanggal_kembali(), transaksi.getSts_pengembalian()));
            i++;
        }
    }
}