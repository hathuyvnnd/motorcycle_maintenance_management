package com.example.service_impl;

import com.example.dao.DichVuDao;
import com.example.model.DichVu;

import com.example.service.DichVuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service("dichVuService")
public class DichVuServiceImpl implements DichVuService {
    @Autowired
    DichVuDao dvDao;

    @Override
    public List<DichVu> findAll() {
        return dvDao.findAll();
    }

    @Override
    public DichVu findById(String id) {
        Optional<DichVu> opt = dvDao.findById(id);
        return opt.orElse(null);
    }

    @Override
    public DichVu create(DichVu dichVu) {

        // Sinh ID tự động
        String newId = generateNewId();
        dichVu.setIdDichVu(newId);
        ;
        return dvDao.save(dichVu);
    }

    // Hàm sinh ID mới
    private String generateNewId() {
        // Lấy ID cuối cùng
        String lastId = dvDao.findLastId();

        // Nếu không có ID, tạo ID đầu tiên
        if (lastId == null) {
            return "DV001";
        }

        // Lấy phần số từ ID (bỏ phần "DV") và tăng nó lên
        int number = Integer.parseInt(lastId.substring(2));
        number++;

        // Ghép phần số mới với "DV"
        return String.format("DV%03d", number); // Định dạng với 3 chữ số, ví dụ:DV002

    }

    @Override
    public void update(DichVu dichVu) {
        // Thông thường, với JpaRepository, update = save (nếu ID đã tồn tại)
        if (dvDao.existsById(dichVu.getIdDichVu())) {
            dvDao.save(dichVu);
        }
    }

    @Override
    public void deleteById(String id) {
        dvDao.deleteById(id);
    }

    @Override
    public boolean exitsById(String id) {
        return dvDao.existsById(id);
    }

    @Override
    public List<DichVu> geListDVByKey(String key) {
        return dvDao.findByTenDichVuContainingIgnoreCase(key);
    }
}
