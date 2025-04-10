package com.example.service_impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.dao.PhieuSuDungPhuTungCTDao;
import com.example.model.PhieuDichVu;
import com.example.model.PhieuDichVuCT;
import com.example.model.PhieuSuDungPhuTungCT;
import com.example.service.PhieuSuDungPhuTungCTService;

@Service
public class PhieuSuDungPTCTImpl implements PhieuSuDungPhuTungCTService{
    @Autowired
    PhieuSuDungPhuTungCTDao ptctDao;
    @Override
    public List<PhieuSuDungPhuTungCT> findByPDV(PhieuDichVu phieuDichVu){
        return ptctDao.findByPhieuDichVu(phieuDichVu);
    }

    public String generateNewId() {
        // Lấy ID cuối cùng
        String lastId = ptctDao.findLastId();

        // Nếu không có ID, tạo ID đầu tiên
        if (lastId == null) {
            return "PSTCT001";
        }

        // Lấy phần số từ ID (bỏ phần "KH") và tăng nó lên
        int number = Integer.parseInt(lastId.substring(5));
        number++;

        // Ghép phần số mới với "KH"
        return String.format("PSTCT%03d", number); // Định dạng với 3 chữ số, ví dụ:KH002

    }
     @Override
    public PhieuSuDungPhuTungCT create(PhieuSuDungPhuTungCT entity) {
        return ptctDao.save(entity);
    }
}
