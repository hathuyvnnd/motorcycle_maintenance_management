package com.example.dao;

import com.example.model.PhieuDichVu;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PhieuDichVuDAO extends JpaRepository<PhieuDichVu, String> {
}
