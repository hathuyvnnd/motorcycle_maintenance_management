package com.example.service_impl;

import java.security.SecureRandom;
import java.util.List;
import java.util.Optional;

import com.example.dao.KhachHangDao;
import com.example.model.*;
import com.example.service.KhachHangService;
import com.example.util.SendMail;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.example.dao.TaiKhoanDao;
import com.example.service.TaiKhoanService;

@Service
public class TaiKhoanServiceImpl implements TaiKhoanService {

    @Autowired
    private TaiKhoanDao taiKhoanDao;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    KhachHangDao khDao;

    @Autowired
    SendMail sendMail;

    @Override
    public List<TaiKhoan> findAll() {
        return taiKhoanDao.findAll();
    }

    @Override
    public TaiKhoan findById(String id) {
        Optional<TaiKhoan> taiKhoanOptional = taiKhoanDao.findById(id);
        return taiKhoanOptional.orElse(null);
    }

    @Override
    public TaiKhoan create(TaiKhoan taiKhoan) {
        // Mã hóa mật khẩu trước khi lưu
        if (taiKhoan.getMatKhau() != null && !taiKhoan.getMatKhau().isEmpty()) {
            taiKhoan.setMatKhau(passwordEncoder.encode(taiKhoan.getMatKhau()));
        }

        // Chuyển đổi đối tượng sang lớp con tương ứng dựa vào giá trị vaiTro
        if ("Nhân viên".equalsIgnoreCase(taiKhoan.getVaiTro()) && !(taiKhoan instanceof TaiKhoanNhanVien)) {
            TaiKhoanNhanVien tkNV = new TaiKhoanNhanVien();
            BeanUtils.copyProperties(taiKhoan, tkNV);
            taiKhoan = tkNV;
        } else if ("Khách hàng".equalsIgnoreCase(taiKhoan.getVaiTro()) && !(taiKhoan instanceof TaiKhoanKhachHang)) {
            TaiKhoanKhachHang tkKH = new TaiKhoanKhachHang();
            BeanUtils.copyProperties(taiKhoan, tkKH);
            taiKhoan = tkKH;
        } else if ("Admin".equalsIgnoreCase(taiKhoan.getVaiTro()) && !(taiKhoan instanceof TaiKhoanAdmin)) {
            TaiKhoanAdmin tkAdmin = new TaiKhoanAdmin();
            BeanUtils.copyProperties(taiKhoan, tkAdmin);
            taiKhoan = tkAdmin;
        }
        return taiKhoanDao.save(taiKhoan);
    }

    // Hàm sinh ID mới
    // private String generateNewId() {
    // // Lấy ID cuối cùng
    // String lastId = taiKhoanDao.findLastId();

    // // Nếu không có ID, tạo ID đầu tiên
    // if (lastId == null) {
    // return "TK001";
    // }

    // // Lấy phần số từ ID (bỏ phần "TK") và tăng nó lên
    // int number = Integer.parseInt(lastId.substring(2));
    // number++;

    // // Ghép phần số mới với "TK"
    // return String.format("TK%03d", number); // Định dạng với 3 chữ số, ví dụ:
    // TK002
    // }

    @Override
    public void update(TaiKhoan taiKhoan) {
        if (taiKhoanDao.existsById(taiKhoan.getIdTaiKhoan())) {
            // Mã hóa mật khẩu nếu được cung cấp
            // if (taiKhoan.getMatKhau() != null && !taiKhoan.getMatKhau().isEmpty()) {
            // taiKhoan.setMatKhau(passwordEncoder.encode(taiKhoan.getMatKhau()));
            // }
            // Chuyển đổi đối tượng sang lớp con tương ứng dựa vào giá trị vaiTro
            if ("Nhân viên".equalsIgnoreCase(taiKhoan.getVaiTro()) && !(taiKhoan instanceof TaiKhoanNhanVien)) {
                TaiKhoanNhanVien tkNV = new TaiKhoanNhanVien();
                BeanUtils.copyProperties(taiKhoan, tkNV);
                taiKhoan = tkNV;
            } else if ("Khách hàng".equalsIgnoreCase(taiKhoan.getVaiTro())
                    && !(taiKhoan instanceof TaiKhoanKhachHang)) {
                TaiKhoanKhachHang tkKH = new TaiKhoanKhachHang();
                BeanUtils.copyProperties(taiKhoan, tkKH);
                taiKhoan = tkKH;
            } else if ("Admin".equalsIgnoreCase(taiKhoan.getVaiTro()) && !(taiKhoan instanceof TaiKhoanAdmin)) {
                TaiKhoanAdmin tkAdmin = new TaiKhoanAdmin();
                BeanUtils.copyProperties(taiKhoan, tkAdmin);
                taiKhoan = tkAdmin;
            }
            taiKhoanDao.save(taiKhoan);
        }
    }

    @Override
    public void deleteById(String id) {
        taiKhoanDao.deleteById(id);
    }

    @Override
    public boolean exitsById(String id) {
        return taiKhoanDao.existsById(id);
    }

    //////////////////// Hàm tạo mật khẩu random////////////////////////////////////
    @Override
    public String randomPassword(int length) {
        String chars = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";
        SecureRandom random = new SecureRandom();
        StringBuilder sb = new StringBuilder(length);

        for (int i = 0; i < length; i++) {
            sb.append(chars.charAt(random.nextInt(chars.length())));
        }
        return sb.toString();
    }

    /////////////////// Hàm reset mật khẩu Tài Khoản Khách Hàng qua
    /////////////////// email/////////////
    @Override
    public boolean resetPasswordByEmail(String email) {
        KhachHang kh = khDao.findKhachHangByEmail(email);
        if (kh == null) {
            return false;
        }
        TaiKhoan tk = kh.getTaiKhoanKH();
        String newPassword = randomPassword(8);

        // Mã hóa mật khẩu trước khi lưu
        tk.setMatKhau(passwordEncoder.encode(newPassword));
        taiKhoanDao.save(tk);

        // Gửi email HTML với mật khẩu mới
        sendMail.sendPasword(kh.getEmail(), newPassword);
        return true;

    }

    @Override
    public TaiKhoan findByIdTaiKhoan(String id) {
        return taiKhoanDao.findTaiKhoanByIdTaiKhoan(id);
    }

}
