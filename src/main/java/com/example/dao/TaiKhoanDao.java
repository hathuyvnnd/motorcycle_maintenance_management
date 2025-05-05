package com.example.dao;

import com.example.model.TaiKhoan;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface TaiKhoanDao extends JpaRepository<TaiKhoan, String> {
    @Query(value = "SELECT TOP 1 IdTaiKhoan FROM TaiKhoan ORDER BY IdTaiKhoan DESC ", nativeQuery = true)
    String findLastId();

    TaiKhoan findTaiKhoanByIdTaiKhoan(String id);

}
