package com.example.service_impl;

import com.example.dao.KhachHangDao;
import com.example.dao.NhanVienDao;
import com.example.dao.TaiKhoanDao;
import com.example.dto.request.khachhang.KhachHangDTO;
import com.example.exception.AppException;
import com.example.exception.ErrorCode;
import com.example.model.KhachHang;
import com.example.model.NhanVien;
import com.example.model.TaiKhoan;
import com.example.service.KhachHangService;
import com.example.service.NhanVienService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class KhachHangServiceImpl implements KhachHangService {
    @Autowired
    private KhachHangDao khachHangDao;
    @Autowired
    TaiKhoanDao tkDao;

    // // Constructor injection
    // public KhachHangServiceImpl(KhachHangDao khachHangDao) {
    // this.khachHangDao = khachHangDao;
    // }

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
    public String findIdKhachHangByTaiKhoanKH_IdTaiKhoan(String sdt) {
        return khachHangDao.findIdKhachHangBySoDienThoai(sdt).orElseThrow(() -> new AppException(ErrorCode.USER_NOTFOUND));
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
    public String generateNewId() {
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

    public KhachHang findByTaiKhoanKH(TaiKhoan byId) {
        return khachHangDao.findByTaiKhoanKH(byId)
                .orElseThrow(() -> new AppException(ErrorCode.USER_NOTFOUND));
    }

    @Override
    @Transactional
    public KhachHang dangKyKhachHang(String soDienThoai, String matKhau, String hoTen, String diaChi, String email, String hinhAnh) {
        // 1. Tạo tài khoản mới
        TaiKhoan taiKhoan = new TaiKhoan();
        taiKhoan.setIdTaiKhoan(soDienThoai);  // Sử dụng số điện thoại làm ID khách hàng
        taiKhoan.setMatKhau(matKhau);
        taiKhoan.setTrangThai(true);  // Có thể tùy chỉnh trạng thái
        taiKhoan.setVaiTro("khachHang");  // Vai trò là "KH" (Khách hàng)

        tkDao.save(taiKhoan);  // Lưu tài khoản

        // 2. Tạo khách hàng mới và liên kết với tài khoản
        KhachHang khachHang = new KhachHang();
        khachHang.setIdKhachHang(generateNewId()); // Lấy ID tự tăng dạng chuỗi KH + số tự tăng
        khachHang.setTaiKhoanKH(taiKhoan);
        khachHang.setHoTen(hoTen);
        khachHang.setDiaChi(diaChi);
        khachHang.setEmail(email);
        khachHang.setHinhAnh(hinhAnh);
        khachHang.setNgayDangKi(new Date());  // Ghi lại thời gian đăng ký

        khachHangDao.save(khachHang);  // Lưu khách hàng

        return khachHang;  // Trả về đối tượng khách hàng vừa tạo
    }
}
