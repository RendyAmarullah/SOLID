package com.company;

public class Anggota extends Person {
    
    public Anggota() {
    }

    public Anggota(int idPerson, String nama, String username, String password, int role) {
        super(idPerson, nama, username, password, role);
    }

    // Getter dan Setter sudah diwarisi dari class Person,
    // jadi tidak perlu ditulis ulang kecuali ada atribut khusus tambahan.
}