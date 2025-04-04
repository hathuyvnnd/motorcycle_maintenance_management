package com.example.dao;

import com.example.model.NhanVien;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface NhanVienDao extends JpaRepository<NhanVien, String> {
    @Query("SELECT nv FROM NhanVien nv LEFT JOIN FETCH nv.taiKhoanNV")
    List<NhanVien> findAll();

    // Tìm id cuối cùng
    @Query(value = "SELECT TOP 1 IdNhanVien FROM NhanVien ORDER BY IdNhanVien DESC ", nativeQuery = true)
    String findLastId();

}
