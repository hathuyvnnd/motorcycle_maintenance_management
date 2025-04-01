package com.example.dao;

import com.example.model.HoaDon;
import com.example.model.PhieuGhiNhanTinhTrangXe;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;




public interface PhieuGhiNhanTinhTtangXeDao extends JpaRepository<PhieuGhiNhanTinhTrangXe, String> {
    PhieuGhiNhanTinhTrangXe findByHoaDon(HoaDon hoaDon);
    PhieuGhiNhanTinhTrangXe findByIdPhieuGNX(String idPhieuGNX);
}
