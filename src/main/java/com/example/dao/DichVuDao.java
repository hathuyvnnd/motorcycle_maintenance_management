package com.example.dao;

import com.example.model.DichVu;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DichVuDao extends JpaRepository<DichVu, String> {
    @Query(value = "SELECT TOP 1 IdDichVu FROM DichVu ORDER BY IdDichVu DESC ", nativeQuery = true)
    String findLastId();

    List<DichVu> findAll();

    List<DichVu> findByTrangThai(Boolean trangThai);
}
