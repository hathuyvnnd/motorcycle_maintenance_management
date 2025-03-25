package com.example.dao;

import com.example.model.KhachHang;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface KhachHangDao extends JpaRepository<KhachHang, String> {
    @Query(value = "SELECT TOP 1 IdKhachHang FROM KhachHang ORDER BY IdKhachHang DESC ", nativeQuery = true)
    String findLastId();
}
