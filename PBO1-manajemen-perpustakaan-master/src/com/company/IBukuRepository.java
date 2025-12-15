package com.company;

import java.util.ArrayList;

// Interface ini mendefinisikan APA yang bisa dilakukan, bukan BAGAIMANA caranya.
public interface IBukuRepository {
    boolean insert(Buku buku);
    boolean update(Buku buku);
    boolean delete(int idBuku);
    ArrayList<Buku> getAll();
    Buku getById(int idBuku);
    ArrayList<Buku> search(String column, String value);
}