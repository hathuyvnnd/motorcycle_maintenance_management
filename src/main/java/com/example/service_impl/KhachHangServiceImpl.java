package com.example.service_impl;

import com.example.dao.KhachHangDao;
import com.example.dao.NhanVienDao;
import com.example.model.KhachHang;
import com.example.model.NhanVien;
import com.example.service.KhachHangService;
import com.example.service.NhanVienService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class KhachHangServiceImpl implements KhachHangService {

    private final KhachHangDao khachHangDao;

    // Constructor injection
    public KhachHangServiceImpl(KhachHangDao khachHangDao) {
        this.khachHangDao = khachHangDao;
    }

    @Override
    public List<KhachHang> findAll() {
        return khachHangDao.findAll();
    }

    @Override
    public KhachHang findById(String id) {
        Optional<KhachHang> opt = khachHangDao.findById(id);
        return opt.orElse(null);
    }

    @Override
    public KhachHang create(KhachHang khachHang) {

        // Sinh ID tự động
        String newId = generateNewId();
        khachHang.setIdKhachHang(newId);
        ;
        return khachHangDao.save(khachHang);
    }

    // Hàm sinh ID mới
    private String generateNewId() {
        // Lấy ID cuối cùng
        String lastId = khachHangDao.findLastId();

        // Nếu không có ID, tạo ID đầu tiên
        if (lastId == null) {
            return "KH001";
        }

        // Lấy phần số từ ID (bỏ phần "KH") và tăng nó lên
        int number = Integer.parseInt(lastId.substring(2));
        number++;

        // Ghép phần số mới với "KH"
        return String.format("KH%03d", number); // Định dạng với 3 chữ số, ví dụ:KH002

    }

    @Override
    public void update(KhachHang khachHang) {
        // Thông thường, với JpaRepository, update = save (nếu ID đã tồn tại)
        if (khachHangDao.existsById(khachHang.getIdKhachHang())) {
            khachHangDao.save(khachHang);
        }
    }

    @Override
    public void deleteById(String id) {
        KhachHang existing = khachHangDao.findById(id).orElse(null);
        if (existing != null) {
            // Nếu cần, cắt quan hệ để tránh cascade xóa tài khoản
            existing.setTaiKhoanKH(null);
            khachHangDao.delete(existing);
        }
    }

    @Override
    public boolean exitsById(String id) {
        return khachHangDao.existsById(id);
    }
}
