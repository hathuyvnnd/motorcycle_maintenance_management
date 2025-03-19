package com.example.dao;

import com.example.model.HoaDon;
import com.example.model.LoaiXe;
import org.springframework.data.jpa.repository.JpaRepository;

public interface HoaDonDao extends JpaRepository<HoaDon,String> {
}
