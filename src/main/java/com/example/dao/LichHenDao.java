package com.example.dao;

import com.example.model.LichHen;
import com.example.model.LoaiXe;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LichHenDao extends JpaRepository<LichHen,String> {
}
