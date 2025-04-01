package com.example.dao;

import com.example.model.HoaDon;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface HoaDonDao extends JpaRepository<HoaDon, String> {
}
