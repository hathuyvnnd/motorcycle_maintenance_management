package com.example.maintenece_motorcycle_management;

import com.example.model.NhanVien;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.List;

public class TestJDBC {
    public static void main(String[] args) {
        String url = "jdbc:sqlserver://localhost:1433;databaseName=MotorBike;encrypt=true;trustServerCertificate=true";
        String username = "sa";
        String password = "123";

        try {
            Connection conn = DriverManager.getConnection(url, username, password);
            System.out.println("✅ Kết nối thành công đến SQL Server!");

        } catch (SQLException e) {
            System.out.println("❌ Kết nối thất bại!");
            e.printStackTrace();
        }
    }
}
