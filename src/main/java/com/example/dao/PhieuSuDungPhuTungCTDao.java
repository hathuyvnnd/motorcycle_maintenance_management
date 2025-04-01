package com.example.dao;

import com.example.model.PhieuDichVu;
import com.example.model.PhieuSuDungPhuTungCT;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;


public interface PhieuSuDungPhuTungCTDao extends JpaRepository<PhieuSuDungPhuTungCT, String> {
   List<PhieuSuDungPhuTungCT> findByPhieuDichVu(PhieuDichVu phieuDichVu);
}
