package com.example.maintenece_motorcycle_management.service;

import com.example.maintenece_motorcycle_management.dao.TaiKhoanDao;
import com.example.maintenece_motorcycle_management.model.TaiKhoan;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AccountService {
    private final TaiKhoanDao taiKhoanDao;

    public Optional<TaiKhoan> findByID(String id) {
        return taiKhoanDao.findById(id);
    }
}
