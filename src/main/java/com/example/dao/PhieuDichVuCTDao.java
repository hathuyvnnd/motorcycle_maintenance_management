package com.example.dao;

import com.example.model.PhieuDichVu;
import com.example.model.PhieuDichVuCT;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface PhieuDichVuCTDao extends JpaRepository<PhieuDichVuCT, String> {
    List<PhieuDichVuCT> findByPhieuDichVu(PhieuDichVu phieuDichVu);
    @Query(value = "SELECT TOP 1 IdPhieuDichVuCT FROM PhieuDichVuCT ORDER BY IdPhieuDichVuCT DESC ", nativeQuery = true)
    String findLastId();

    @Query("SELECT ct FROM PhieuDichVuCT ct " +
            "WHERE ct.phieuDichVu.phieuGNX.lichHen.idLichHen = :idLichHen")
    List<PhieuDichVuCT> findDichVuByLichHenId(@Param("idLichHen") String idLichHen);

    List<PhieuDichVuCT> findByPhieuDichVu_IdPhieuDichVu(String idPhieuDichVu);
    void deleteByPhieuDichVu_IdPhieuDichVu(String idPhieuDichVu);
}
