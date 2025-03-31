package com.example.dao;

import com.example.model.KhachHang;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface KhachHangDao extends JpaRepository<KhachHang, String> {
    @Query(value = "SELECT TOP 1 IdKhachHang FROM KhachHang ORDER BY IdKhachHang DESC ", nativeQuery = true)
    String findLastId();



    @Query(value = "SELECT TOP 1 * FROM KhachHang WHERE SoDienThoai = :sodienthoai ORDER BY IdKhachHang DESC", nativeQuery = true)
    KhachHang findTopBySoDienThoai(@Param("sodienthoai") String sodienthoai);



}
