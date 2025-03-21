package com.example.dao;

import com.example.model.NhanVien;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface NhanVienDao extends JpaRepository<NhanVien, String> {
}
