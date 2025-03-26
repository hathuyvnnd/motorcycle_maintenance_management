package com.example.service_impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.dao.PhuTungDao;
import com.example.model.DichVu;
import com.example.model.LoaiPhuTung;
import com.example.model.PhuTung;
import com.example.service.PhuTungService;

import java.util.List;
import java.util.Optional;

@Service("phuTungService")
public class PhuTungServiceImpl implements PhuTungService {
    @Autowired
    private PhuTungDao pTungDao;

    @Override
    public List<PhuTung> findByLoaiPT(LoaiPhuTung loaiPT) {
        System.out.println("🔍 Đang tìm phụ tùng theo loại: " + loaiPT.getIdLoaiPT());
        List<PhuTung> list = pTungDao.findByLoaiPT(loaiPT);
        System.out.println("✅ Số phụ tùng tìm thấy: " + list.size());
        return list;
    }

    @Override
    public List<PhuTung> findAll() {
        return pTungDao.findAll();
    }

    @Override
    public PhuTung findById(String id) {
        Optional<PhuTung> opt = pTungDao.findById(id);
        return opt.orElse(null);
    }

    @Override
    public PhuTung create(PhuTung phuTung) {

        // Sinh ID tự động
        String newId = generateNewId();
        phuTung.setIdPhuTung(newId);
        ;
        return pTungDao.save(phuTung);
    }

    // Hàm sinh ID mới
    private String generateNewId() {
        // Lấy ID cuối cùng
        String lastId = pTungDao.findLastId();

        // Nếu không có ID, tạo ID đầu tiên
        if (lastId == null) {
            return "PT001";
        }

        // Lấy phần số từ ID (bỏ phần "PT") và tăng nó lên
        int number = Integer.parseInt(lastId.substring(2));
        number++;

        // Ghép phần số mới với "PT"
        return String.format("PT%03d", number); // Định dạng với 3 chữ số, ví dụ:PT002

    }

    @Override
    public void update(PhuTung phuTung) {
        // Thông thường, với JpaRepository, update = save (nếu ID đã tồn tại)
        if (pTungDao.existsById(phuTung.getIdPhuTung())) {
            pTungDao.save(phuTung);
        }
    }

    @Override
    public void deleteById(String id) {
        pTungDao.deleteById(id);
    }

    @Override
    public boolean exitsById(String id) {
        return pTungDao.existsById(id);
    }
}
