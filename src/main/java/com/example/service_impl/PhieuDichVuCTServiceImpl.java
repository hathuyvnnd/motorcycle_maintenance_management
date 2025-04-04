package com.example.service_impl;

import com.example.dao.PhieuDichVuCTDao;
import com.example.model.PhieuDichVuCT;
import com.example.service.PhieuDichVuCTService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE,makeFinal = true)
public class PhieuDichVuCTServiceImpl implements PhieuDichVuCTService {
    PhieuDichVuCTDao dao;
    public String generateNewId() {
        // Lấy ID cuối cùng
        String lastId = dao.findLastId();

        // Nếu không có ID, tạo ID đầu tiên
        if (lastId == null) {
            return "PDVCT001";
        }

        // Lấy phần số từ ID (bỏ phần "KH") và tăng nó lên
        int number = Integer.parseInt(lastId.substring(5));
        number++;

        // Ghép phần số mới với "KH"
        return String.format("PDVCT%03d", number); // Định dạng với 3 chữ số, ví dụ:KH002

    }

    @Override
    public PhieuDichVuCT create(PhieuDichVuCT entity) {
        return dao.save(entity);
    }
}
