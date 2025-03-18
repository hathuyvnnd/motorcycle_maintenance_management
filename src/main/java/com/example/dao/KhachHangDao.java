package com.example.dao;

import com.example.model.KhachHang;
import com.example.model.LoaiXe;
import org.springframework.data.jpa.repository.JpaRepository;

public interface KhachHangDao extends JpaRepository<KhachHang,String> {
}
