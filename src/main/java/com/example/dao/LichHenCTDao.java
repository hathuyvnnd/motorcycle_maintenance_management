package com.example.dao;

import com.example.model.LichHen;
import com.example.model.LichHenCT;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface LichHenCTDao extends JpaRepository<LichHenCT, String> {
    @Query(value = "SELECT TOP 1 IdLichHenCT FROM LichHenCT ORDER BY IdLichHenCT DESC ", nativeQuery = true)
    String findLastId();

    List<LichHenCT> findByIdLichHen_IdLichHen(String idLichHen);

}
