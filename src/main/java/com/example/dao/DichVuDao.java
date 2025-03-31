package com.example.dao;

import com.example.model.DichVu;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DichVuDao extends JpaRepository<DichVu, String> {

}
