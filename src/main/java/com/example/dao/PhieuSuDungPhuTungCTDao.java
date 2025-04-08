package com.example.dao;

import com.example.model.PhieuDichVu;
import com.example.model.PhieuSuDungPhuTungCT;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PhieuSuDungPhuTungCTDao extends JpaRepository<PhieuSuDungPhuTungCT, String> {
   List<PhieuSuDungPhuTungCT> findByPhieuDichVu(PhieuDichVu phieuDichVu);

   @Query("SELECT pt FROM PhieuSuDungPhuTungCT pt " +
           "WHERE pt.phieuDichVu.phieuGNX.lichHen.idLichHen = :idLichHen")
   List<PhieuSuDungPhuTungCT> findAllByLichHenId(@Param("idLichHen") String idLichHen);
}
