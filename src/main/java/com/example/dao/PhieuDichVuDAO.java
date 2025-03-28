package com.example.dao;

import com.example.model.HoaDon;
import com.example.model.PhieuDichVu;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;


@Repository
public interface PhieuDichVuDAO extends JpaRepository<PhieuDichVu, String> {
    List<PhieuDichVu> findByHoaDon(HoaDon hoaDon);
}
