package com.example.dao;

import com.example.model.LoaiXe;
import com.example.model.PhuTung;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PhuTungDao extends JpaRepository<PhuTung,String> {
}
