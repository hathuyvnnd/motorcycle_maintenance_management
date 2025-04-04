package com.example.dao;

import com.example.model.HoaDon;
import com.example.model.LichHen;
import com.example.model.PhieuGhiNhanTinhTrangXe;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Date;

@Repository
public interface PhieuGhiNhanTinhTrangXeDao extends JpaRepository<PhieuGhiNhanTinhTrangXe, String> {
    @Query(value = "SELECT TOP 1 IdPhieuGNX FROM PhieuGhiNhanTinhTrangXe ORDER BY IdPhieuGNX DESC ", nativeQuery = true)
    String findLastId();

    PhieuGhiNhanTinhTrangXe findByBienSoXeAndNgayNhan(String bienSoXe, Date NgayNhan);

///////////Cái này của Hathuy đừng có xóa////////////////////////
    PhieuGhiNhanTinhTrangXe findByIdPhieuGNX(String idPhieuGNX);
//////////////////////////////////////////////////////////////////
}
