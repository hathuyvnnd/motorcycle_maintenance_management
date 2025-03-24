package com.example.service_impl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.dao.TaiKhoanDao;
import com.example.model.TaiKhoan;
import com.example.model.TaiKhoanAdmin;
import com.example.model.TaiKhoanKhachHang;
import com.example.model.TaiKhoanNhanVien;
import com.example.service.TaiKhoanService;

@Service
public class TaiKhoanServiceImpl implements TaiKhoanService {

    @Autowired
    private TaiKhoanDao taiKhoanDao;

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
        // Sinh ID tự động
        String newId = generateNewId();
        taiKhoan.setIdTaiKhoan(newId);

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
    private String generateNewId() {
        // Lấy ID cuối cùng
        String lastId = taiKhoanDao.findLastId();

        // Nếu không có ID, tạo ID đầu tiên
        if (lastId == null) {
            return "TK001";
        }

        // Lấy phần số từ ID (bỏ phần "TK") và tăng nó lên
        int number = Integer.parseInt(lastId.substring(2));
        number++;

        // Ghép phần số mới với "TK"
        return String.format("TK%03d", number); // Định dạng với 3 chữ số, ví dụ: TK002
    }

    @Override
    public void update(TaiKhoan taiKhoan) {
        if (taiKhoanDao.existsById(taiKhoan.getIdTaiKhoan())) {
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
}
