package com.example.dao;

import com.example.model.PhieuDichVu;
import com.example.model.PhieuDichVuCT;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface PhieuDichVuCTDao extends JpaRepository<PhieuDichVuCT, String> {
    List<PhieuDichVuCT> findByPhieuDichVu(PhieuDichVu phieuDichVu);
    @Query(value = "SELECT TOP 1 IdPhieuDichVuCT FROM PhieuDichVuCT ORDER BY IdPhieuDichVuCT DESC ", nativeQuery = true)
    String findLastId();
}
