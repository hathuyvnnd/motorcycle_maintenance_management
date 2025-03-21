package com.example.service_impl;

import com.example.dao.NhanVienDao;
import com.example.model.NhanVien;
import com.example.service.NhanVienService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class NhanVienServiceImpl implements NhanVienService {

    private final NhanVienDao nhanVienDao;

    // Constructor injection
    public NhanVienServiceImpl(NhanVienDao nhanVienDao) {
        this.nhanVienDao = nhanVienDao;
    }

    @Override
    public List<NhanVien> findAll() {
        return nhanVienDao.findAll();
    }

    @Override
    public NhanVien findById(String id) {
        Optional<NhanVien> opt = nhanVienDao.findById(id);
        return opt.orElse(null);
    }

    @Override
    public NhanVien create(NhanVien entity) {
        // Nếu muốn sinh khóa tự động (String) thì cần logic riêng,
        // hoặc bạn đã có sẵn ID => save thẳng
        return nhanVienDao.save(entity);
    }

    @Override
    public void update(NhanVien entity) {
        // Thông thường, với JpaRepository, update = save (nếu ID đã tồn tại)
        if (nhanVienDao.existsById(entity.getIdNhanVien())) {
            nhanVienDao.save(entity);
        }
    }

    @Override
    public void deleteById(String id) {
        nhanVienDao.deleteById(id);
    }

    @Override
    public boolean exitsById(String id) {
        return nhanVienDao.existsById(id);
    }
}
