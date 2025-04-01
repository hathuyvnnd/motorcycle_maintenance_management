package com.example.dao;

import com.example.model.PhieuDichVu;
import com.example.model.PhieuDichVuCT;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface PhieuDichVuCTDao extends JpaRepository<PhieuDichVuCT, String> {
    List<PhieuDichVuCT> findByPhieuDichVu(PhieuDichVu phieuDichVu);
}
