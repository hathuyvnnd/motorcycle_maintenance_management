package com.example.dao;

import com.example.model.HoaDon;
import com.example.model.KhachHang;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;


public interface HoaDonDao extends JpaRepository<HoaDon, String> {
    List<HoaDon> findByKhachHang(KhachHang khachHang);
}
