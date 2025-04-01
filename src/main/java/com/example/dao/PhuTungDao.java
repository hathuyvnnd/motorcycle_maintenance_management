package com.example.dao;

import com.example.model.DichVu;
import com.example.model.LoaiPhuTung;
import com.example.model.PhuTung;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface PhuTungDao extends JpaRepository<PhuTung, String> {
   List<PhuTung> findByLoaiPT(LoaiPhuTung loaiPT);

   @Query(value = "SELECT TOP 1 IdPhuTung FROM PhuTung ORDER BY IdPhuTung DESC ", nativeQuery = true)
   String findLastId();

   List<PhuTung> findByTinhTrang(Boolean tinhTrang);

   // Tìm phụ tùng theo dịch vụ
   List<PhuTung> findByDichVuPT(DichVu dichVu);
}
