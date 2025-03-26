package com.example.dao;

import com.example.model.KhachHang;
import com.example.model.TaiKhoan;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface KhachHangDao extends JpaRepository<KhachHang, String> {
   KhachHang findByTaiKhoanKH(TaiKhoan taiKhoan);

}
