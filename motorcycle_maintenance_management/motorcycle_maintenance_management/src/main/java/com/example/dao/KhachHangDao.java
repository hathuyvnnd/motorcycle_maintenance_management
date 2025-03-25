package com.example.dao;

import com.example.model.KhachHang;
import org.springframework.data.jpa.repository.JpaRepository;

public interface KhachHangDao extends JpaRepository<KhachHang, String> {
}
