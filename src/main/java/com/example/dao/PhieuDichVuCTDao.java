package com.example.dao;

import com.example.model.PhieuDichVu;
import com.example.model.PhieuDichVuCT;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

public interface PhieuDichVuCTDao extends JpaRepository<PhieuDichVuCT, String> {
    List<PhieuDichVuCT> findByPhieuDichVu(PhieuDichVu phieuDichVu);
}
