package com.example.serviceimpl;

import com.example.dao.NhanVienDao;
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

	    // Lấy danh sách tất cả nhân viên
	    @Override
	    public List<NhanVien> findAll() {
	        return nhanVienDao.findAll();
	    }

	    // Tìm nhân viên theo ID
	    @Override
	    public NhanVien findById(String id) {
	        Optional<NhanVien> optionalNhanVien = nhanVienDao.findById(id);
	        return optionalNhanVien.orElse(null);
	    }

	    // Thêm mới nhân viên
	    @Override
	    public NhanVien create(NhanVien nhanVien) {
	        return nhanVienDao.save(nhanVien);
	    }
	    
	    // Kiểm tra xem nhân viên có tồn tại hay không
	    @Override
	    public boolean exitsById(String id) {
	        return nhanVienDao.existsById(id);
	    }
	    
	    // Cập nhật thông tin nhân viên
	    @Override
	    public void update(NhanVien nhanVien) {
	        // Kiểm tra nhân viên tồn tại trước khi cập nhật
	        if (nhanVienDao.existsById(nhanVien.getIdNhanVien())) {
	            nhanVienDao.save(nhanVien);
	        }
	    }

	    // Xóa nhân viên theo ID
	    @Override
	    public void deleteById(String id) {
	        if (nhanVienDao.existsById(id)) {
	            nhanVienDao.deleteById(id);
	        }
	    }

	   
}
