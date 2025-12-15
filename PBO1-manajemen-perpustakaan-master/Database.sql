-- 1. Buat Tabel Role
-- Tabel ini harus dibuat duluan karena dipanggil oleh person
CREATE TABLE role (
  id int PRIMARY KEY AUTO_INCREMENT,
  nama_role varchar(255)
);

-- Masukkan data awal role agar bisa digunakan (Opsional tapi disarankan)
INSERT INTO role (id, nama_role) VALUES (1, 'Admin'), (2, 'Anggota');

-- 2. Buat Tabel Person
-- Kolom role_id disesuaikan dengan Person.java baris 82
CREATE TABLE person (
  id int PRIMARY KEY AUTO_INCREMENT,
  nama varchar(255),
  username varchar(255) UNIQUE,
  password varchar(255),
  role_id int,
  CONSTRAINT fk_person_role FOREIGN KEY (role_id) REFERENCES role (id)
);

-- 3. Buat Tabel Buku
-- Sesuai dengan Buku.java
CREATE TABLE buku (
  id int PRIMARY KEY AUTO_INCREMENT,
  judul_buku varchar(255),
  pengarang varchar(255),
  penerbit varchar(255),
  ketersediaan int
);

-- 4. Buat Tabel Transaksi Buku
-- PERHATIAN: Nama kolom 'tangga_' disengaja mengikuti typo di TransaksiBuku.java
CREATE TABLE transaksi_buku (
  id int PRIMARY KEY AUTO_INCREMENT,
  peminjam int,
  buku int,
  tangga_pinjam date,   -- Sesuai TransaksiBuku.java baris 67 & 113
  tangga_kembali date,  -- Sesuai TransaksiBuku.java baris 68 & 114
  sts_pengembalian varchar(50), -- Sesuai TransaksiBuku.java baris 69
  CONSTRAINT fk_transaksi_peminjam FOREIGN KEY (peminjam) REFERENCES person (id),
  CONSTRAINT fk_transaksi_buku FOREIGN KEY (buku) REFERENCES buku (id)
);