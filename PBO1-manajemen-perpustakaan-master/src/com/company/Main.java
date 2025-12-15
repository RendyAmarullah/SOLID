package com.company;

import java.sql.SQLException;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) throws SQLException {
        Scanner sc = new Scanner(System.in);

        IView view = new ConsoleViewAdapter(new View());
        AuthService authService = new AuthServiceImpl();

        ViewControl vc = new ViewControl(view, authService, sc);
        vc.loginUser();
    }
}
