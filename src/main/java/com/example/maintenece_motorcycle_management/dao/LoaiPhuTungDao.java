package com.example.maintenece_motorcycle_management.dao;

import com.example.model.LoaiPhuTung;
import com.example.model.LoaiXe;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LoaiPhuTungDao extends JpaRepository<LoaiPhuTung,String> {
}
