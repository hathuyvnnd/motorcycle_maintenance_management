package com.example.dao;

import com.example.model.HoaDon;
import com.example.model.LichHen;
import com.example.model.PhieuGhiNhanTinhTrangXe;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Repository
public interface PhieuGhiNhanTinhTrangXeDao extends JpaRepository<PhieuGhiNhanTinhTrangXe, String> {
    @Query(value = "SELECT TOP 1 IdPhieuGNX FROM PhieuGhiNhanTinhTrangXe ORDER BY IdPhieuGNX DESC ", nativeQuery = true)
    String findLastId();

    PhieuGhiNhanTinhTrangXe findByLichHen_IdLichHen(String idLichHen);
    @Query("SELECT p FROM PhieuGhiNhanTinhTrangXe p WHERE LOWER(p.lichHen.bienSoXe) LIKE LOWER(CONCAT('%', :keyword, '%'))")
    List<PhieuGhiNhanTinhTrangXe> searchByBienSoXeKeyword(@Param("keyword") String keyword);

///////////Cái này của Hathuy đừng có xóa////////////////////////
    PhieuGhiNhanTinhTrangXe findByIdPhieuGNX(String idPhieuGNX);
//////////////////////////////////////////////////////////////////
}
