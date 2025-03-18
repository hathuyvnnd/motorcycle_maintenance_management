package com.example.dao;

import com.example.model.DichVu;
import com.example.model.LoaiXe;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DichVuDao extends JpaRepository<DichVu,String> {
}
