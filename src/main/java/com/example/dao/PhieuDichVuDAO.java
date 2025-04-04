package com.example.dao;

import com.example.model.HoaDon;
import com.example.model.PhieuDichVu;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import java.util.List;


@Repository
public interface PhieuDichVuDAO extends JpaRepository<PhieuDichVu, String> {
    List<PhieuDichVu> findByHoaDon(HoaDon hoaDon);
    PhieuDichVu findByIdPhieuDichVu(String idPhieuDichVu);

    //Lọc Phiếu Dịch Vụ theo từng khách hàng
    @Query("SELECT pdv FROM PhieuDichVu pdv JOIN HoaDon h ON h.phieuDichVuHD.idPhieuDichVu = pdv.idPhieuDichVu WHERE h.khachHang.idKhachHang=?1")
    List<PhieuDichVu> findPhieuDichVuByKhachHangId(String idKhachHang);

    @Query(value = "SELECT TOP 1 IdPhieuDichVu FROM PhieuDichVu ORDER BY IdPhieuDichVu DESC ", nativeQuery = true)
    String findLastId();

}
