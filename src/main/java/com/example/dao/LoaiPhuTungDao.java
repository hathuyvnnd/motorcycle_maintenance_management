package com.example.dao;

import com.example.model.LoaiPhuTung;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface LoaiPhuTungDao extends JpaRepository<LoaiPhuTung, String> {
}
