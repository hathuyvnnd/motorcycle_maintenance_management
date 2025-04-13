package com.example.service_impl;

import com.example.dao.NhanVienDao;
import com.example.exception.AppException;
import com.example.exception.ErrorCode;
import com.example.model.NhanVien;
import com.example.service.NhanVienService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class NhanVienServiceImpl implements NhanVienService {
    @Autowired
    private NhanVienDao nhanVienDao;

    // // Constructor injection
    // public NhanVienServiceImpl(NhanVienDao nhanVienDao) {
    // this.nhanVienDao = nhanVienDao;
    // }

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
    public NhanVien create(NhanVien nhanVien) {

        // Sinh ID tự động
        String newId = generateNewId();
        nhanVien.setIdNhanVien(newId);
        ;
        return nhanVienDao.save(nhanVien);
    }

    // Hàm sinh ID mới
    private String generateNewId() {
        // Lấy ID cuối cùng
        String lastId = nhanVienDao.findLastId();

        // Nếu không có ID, tạo ID đầu tiên
        if (lastId == null) {
            return "NV001";
        }

        // Lấy phần số từ ID (bỏ phần "NV") và tăng nó lên
        int number = Integer.parseInt(lastId.substring(2));
        number++;

        // Ghép phần số mới với "NV"
        return String.format("NV%03d", number); // Định dạng với 3 chữ số, ví dụ:NV002

    }

    @Override
    public void update(NhanVien nhaVien) {
        // Thông thường, với JpaRepository, update = save (nếu ID đã tồn tại)
        if (nhanVienDao.existsById(nhaVien.getIdNhanVien())) {
            nhanVienDao.save(nhaVien);
        }
    }

    @Override
    public void deleteById(String id) {
        NhanVien existing = nhanVienDao.findById(id).orElse(null);
        if (existing != null) {
            // Nếu cần, cắt quan hệ để tránh cascade xóa tài khoản
            existing.setTaiKhoanNV(null);
            nhanVienDao.delete(existing);
        }
    }

    @Override
    public boolean exitsById(String id) {
        return nhanVienDao.existsById(id);
    }

    public NhanVien updateLA(NhanVien reqq) {
        NhanVien nv = nhanVienDao.findById(reqq.getIdNhanVien()).orElseThrow(() -> new AppException(ErrorCode.INVALID_KEY));
        nv.setHinhAnh(reqq.getHinhAnh());
        nv.setDiaChi(reqq.getDiaChi());
        nv.setTen(reqq.getTen());
        nv.setEmail(reqq.getEmail());
        return nhanVienDao.save(nv);
    }
}
