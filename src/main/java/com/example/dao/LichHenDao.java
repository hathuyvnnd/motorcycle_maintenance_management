package com.example.dao;

import com.example.model.LichHen;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface LichHenDao extends JpaRepository<LichHen, String> {
    @Query("SELECT lh FROM LichHen lh WHERE lh.thoiGian BETWEEN :startOfDay AND :endOfDay")
    List<LichHen> findLichHenToday(@Param("startOfDay") LocalDateTime startOfDay, @Param("endOfDay") LocalDateTime endOfDay);

//    @Query("SELECT lh FROM LichHen lh WHERE lh.bienSoXe LIKE %:bienSoXe%")
//    List<LichHen> findByBienSoXe(@Param("bienSoXe") String bienSoXe);
    List<LichHen> findByBienSoXeContaining(String bienSoXe);

}
