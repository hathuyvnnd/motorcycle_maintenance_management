package com.example.dao;

import com.example.model.LoaiXe;
import com.example.model.NhanVien;
import org.springframework.data.jpa.repository.JpaRepository;

public interface NhanVienDao extends JpaRepository<NhanVien,String> {
}
