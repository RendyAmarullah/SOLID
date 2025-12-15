package com.company;

import java.sql.SQLException;

public class AuthServiceImpl implements AuthService {

    @Override
    public Person login(String username, String password) throws SQLException {
        // tetap pakai logic lama, tapi ViewControl tidak memanggil Person.loginUser() langsung
        return Person.loginUser(username, password);
    }
}
