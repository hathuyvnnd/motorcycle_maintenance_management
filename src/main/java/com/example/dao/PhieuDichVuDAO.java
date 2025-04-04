package com.example.dao;

import com.example.model.PhieuDichVu;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface PhieuDichVuDAO extends JpaRepository<PhieuDichVu, String> {
    @Query(value = "SELECT TOP 1 IdPhieuDichVu FROM PhieuDichVu ORDER BY IdPhieuDichVu DESC ", nativeQuery = true)
    String findLastId();

}
