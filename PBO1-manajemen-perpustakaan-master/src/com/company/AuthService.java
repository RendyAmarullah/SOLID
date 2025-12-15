package com.company;

import java.sql.SQLException;

public interface AuthService {
    Person login(String username, String password) throws SQLException;
}