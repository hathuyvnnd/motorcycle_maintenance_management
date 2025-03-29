package com.example.dao;

import com.example.model.KhachHang;
import com.example.model.TaiKhoan;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

@Repository
public interface KhachHangDao extends JpaRepository<KhachHang, String> {
   Optional<KhachHang> findByTaiKhoanKH(TaiKhoan taiKhoan);

    @Query(value = "SELECT TOP 1 IdKhachHang FROM KhachHang ORDER BY IdKhachHang DESC ", nativeQuery = true)
    String findLastId();

    KhachHang findByTaiKhoanKH_IdTaiKhoan(String idTaiKhoan);
    String findIdKhachHangByTaiKhoanKH_IdTaiKhoan(String idTaiKhoan);

    @Query("SELECT k.idKhachHang FROM KhachHang k WHERE k.taiKhoanKH.idTaiKhoan = :soDienThoai")
    Optional<String> findIdKhachHangBySoDienThoai(@Param("soDienThoai") String soDienThoai);

}
