package com.example.dao;

import com.example.model.HoaDon;
import com.example.model.KhachHang;
import com.example.model.PhieuDichVu;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;


public interface HoaDonDao extends JpaRepository<HoaDon, String> {
    List<HoaDon> findByKhachHang(KhachHang khachHang);
    
    //Lấy danh sách phiếu dịch vụ theo từng khách hàng
     @Query("SELECT h.phieuDichVuHD FROM HoaDon h WHERE h.khachHang.idKhachHang=?1")
    List<PhieuDichVu> findPhieuDichVuByKhachHangId(String idKhachHang);
}
