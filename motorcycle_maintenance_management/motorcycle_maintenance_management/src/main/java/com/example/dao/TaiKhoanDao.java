package com.example.dao;

import com.example.model.TaiKhoan;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TaiKhoanDao extends JpaRepository<TaiKhoan, String> {
}
