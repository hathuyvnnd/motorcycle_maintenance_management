package com.example.maintenece_motorcycle_management.service;

import com.example.maintenece_motorcycle_management.dao.KhachHangDao;
import com.example.maintenece_motorcycle_management.model.KhachHang;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CustomerService {
    private final KhachHangDao khachHangDao;

    public Optional<KhachHang> findByIdKhachHang(String idTaiKhoan) {
        List<KhachHang> kh = khachHangDao.findByIdKhachHang(idTaiKhoan);
        return kh.size() > 0 ? Optional.of(kh.get(0)) : Optional.empty();
    }
}
