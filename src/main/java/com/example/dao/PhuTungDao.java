package com.example.dao;

import com.example.model.LoaiPhuTung;
import com.example.model.PhuTung;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;


public interface PhuTungDao extends JpaRepository<PhuTung, String> {
   List<PhuTung> findByLoaiPT(LoaiPhuTung loaiPT);
}
