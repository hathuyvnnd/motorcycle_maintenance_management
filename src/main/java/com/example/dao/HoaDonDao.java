package com.example.dao;

import com.example.model.HoaDon;
import com.example.model.KhachHang;
import com.example.model.PhieuDichVu;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

import org.springframework.stereotype.Repository;

@Repository
public interface HoaDonDao extends JpaRepository<HoaDon, String> {
    List<HoaDon> findByKhachHang(KhachHang khachHang);

    // Lấy danh sách phiếu dịch vụ theo từng khách hàng
    @Query("SELECT h.phieuDichVuHD FROM HoaDon h WHERE h.khachHang.idKhachHang=?1")
    List<PhieuDichVu> findPhieuDichVuByKhachHangId(String idKhachHang);

    @Query(value = "SELECT TOP 1 IdHoaDon FROM Hoadon ORDER BY IdHoaDon DESC ", nativeQuery = true)
    String findLastId();

    HoaDon findByPhieuDichVuHD_PhieuGNX_LichHen_IdLichHen(String idLichHen);

   @Query("SELECT h FROM HoaDon h " +
       "JOIN h.phieuDichVuHD pdv " +
       "JOIN pdv.phieuGNX pgnx " +
       "JOIN pgnx.lichHen lh " +
       "WHERE LOWER(lh.bienSoXe) LIKE LOWER(CONCAT('%', :keyword, '%'))")
List<HoaDon> searchHoaDonByBienSoXeKeyword(@Param("keyword") String keyword);


}
