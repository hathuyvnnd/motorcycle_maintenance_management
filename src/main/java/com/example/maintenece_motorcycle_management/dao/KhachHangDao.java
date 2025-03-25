package com.example.maintenece_motorcycle_management.dao;

import com.example.maintenece_motorcycle_management.model.KhachHang;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface KhachHangDao extends JpaRepository<KhachHang, String> {

    @Query(" select  o from KhachHang o where o.idTaiKhoan = :id")
    List<KhachHang> findByIdKhachHang(@Param("id") String idTaiKhoan);
}
