package com.company;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.StringJoiner;

public class DBHelper {
    
    // --- READ OPERATIONS (SELECT) ---
    // Catatan: Idealnya method ini mengembalikan List<Object> atau menggunakan RowMapper
    // agar koneksi bisa ditutup di sini. Namun, untuk menjaga kompatibilitas dengan
    // kode View Anda, saya tetap mengembalikan ResultSet.

    public static ResultSet selectAll(String table) {
        return executeQuery("SELECT * FROM " + table);
    }

    public static ResultSet selectAll(String table, String condition) {
        return executeQuery(String.format("SELECT * FROM %s WHERE %s", table, condition));
    }

    public static ResultSet selectAll(String table, String joinTable, String foreignKey) {
        String query = String.format("SELECT * FROM %s JOIN %s ON %s.%s = %s.id", 
                                     table, joinTable, table, foreignKey, joinTable);
        return executeQuery(query);
    }

    public static ResultSet selectAll(String table, String condition, String joinTable, String foreignKey) {
        String query = String.format("SELECT * FROM %s JOIN %s ON %s.%s = %s.id WHERE %s", 
                                     table, joinTable, table, foreignKey, joinTable, condition);
        return executeQuery(query);
    }

    public static ResultSet selectColumn(String table, String[] columns) {
        String columnList = String.join(", ", columns);
        return executeQuery(String.format("SELECT %s FROM %s", columnList, table));
    }

    public static ResultSet selectColumn(String table, String[] columns, String condition) {
        String columnList = String.join(", ", columns);
        return executeQuery(String.format("SELECT %s FROM %s WHERE %s", columnList, table, condition));
    }

    // --- WRITE OPERATIONS (INSERT, UPDATE, DELETE) ---

    public static boolean insert(String table, Map<String, String> data) {
        if (data.isEmpty()) return false;

        // Membangun Query: INSERT INTO table (col1, col2) VALUES (?, ?)
        StringJoiner columns = new StringJoiner(", ");
        StringJoiner placeholders = new StringJoiner(", ");
        List<String> values = new ArrayList<>();

        for (Map.Entry<String, String> entry : data.entrySet()) {
            columns.add(entry.getKey());
            placeholders.add("?");
            values.add(entry.getValue());
        }

        String sql = String.format("INSERT INTO %s (%s) VALUES (%s)", table, columns, placeholders);
        
        return executeUpdate(sql, values);
    }

    public static boolean update(String table, Map<String, String> data, String condition) {
        if (data.isEmpty()) return false;

        // Membangun Query: UPDATE table SET col1 = ?, col2 = ? WHERE condition
        StringJoiner setClause = new StringJoiner(", ");
        List<String> values = new ArrayList<>();

        for (Map.Entry<String, String> entry : data.entrySet()) {
            setClause.add(entry.getKey() + " = ?");
            values.add(entry.getValue());
        }

        String sql = String.format("UPDATE %s SET %s WHERE %s", table, setClause, condition);

        return executeUpdate(sql, values);
    }

    public static boolean delete(String table, String condition) {
        String sql = String.format("DELETE FROM %s WHERE %s", table, condition);
        // Delete biasanya tidak butuh parameter bind jika condition sudah string jadi
        // Tapi idealnya 'condition' pun menggunakan parameter (ex: "id=?").
        // Untuk saat ini kita jalankan langsung.
        return executeUpdate(sql, new ArrayList<>());
    }

    // --- HELPER METHODS (PRIVATE) ---

    /**
     * Menjalankan query SELECT.
     * Peringatan: ResultSet yang dikembalikan terikat pada koneksi yang terbuka.
     * Pastikan caller menutup ResultSet (dan Statement/Connection terkait jika memungkinkan)
     */
    private static ResultSet executeQuery(String sql) {
        // System.out.println("DEBUG SQL: " + sql); 
        try {
            // Menggunakan DBConection (sesuai nama file asli Anda)
            DBConection db = new DBConection();
            Statement statement = db.connection().createStatement();
            return statement.executeQuery(sql);
        } catch (SQLException e) {
            System.err.println("Error executing query: " + sql);
            e.printStackTrace();
            return null;
        }
    }

    /**
     * Menjalankan query INSERT/UPDATE/DELETE dengan PreparedStatement.
     * Menggunakan try-with-resources untuk menutup koneksi secara otomatis.
     */
    private static boolean executeUpdate(String sql, List<String> parameters) {
        // System.out.println("DEBUG SQL: " + sql);
        try (Connection conn = new DBConection().connection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            // Mengisi nilai parameter (?) dengan data yang aman
            for (int i = 0; i < parameters.size(); i++) {
                pstmt.setString(i + 1, parameters.get(i));
            }

            return pstmt.executeUpdate() > 0;

        } catch (SQLException e) {
            System.err.println("Error executing update: " + sql);
            e.printStackTrace();
            return false;
        }
    }
}