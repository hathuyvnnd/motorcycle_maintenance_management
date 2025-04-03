package com.example.service_impl;

import com.example.dao.*;
import com.example.dto.request.phieudichvu.PhieuDichVuRequest;
import com.example.model.PhieuDichVu;
import com.example.model.PhieuGhiNhanTinhTrangXe;
import com.example.service.PhieuDichVuService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE,makeFinal = true)
public class PhieuDichVuServiceImpl implements PhieuDichVuService {
    PhieuDichVuDAO phieuDichVuDAO;
    DichVuDao dichVuDao;
    PhuTungDao phuTungDao;
    PhieuDichVuCTDao phieuDichVuCTDao;
    PhieuSuDungPhuTungCTDao phieuSuDungPhuTungCTDao;

    public String generateNewId() {
        // Lấy ID cuối cùng
        String lastId = phieuDichVuDAO.findLastId();

        // Nếu không có ID, tạo ID đầu tiên
        if (lastId == null) {
            return "PDV001";
        }

        // Lấy phần số từ ID (bỏ phần "KH") và tăng nó lên
        int number = Integer.parseInt(lastId.substring(3));
        number++;

        // Ghép phần số mới với "KH"
        return String.format("PDV%03d", number); // Định dạng với 3 chữ số, ví dụ:KH002

    }


    @Override
    public PhieuDichVu createPhieuDichVuRequest(PhieuDichVuRequest request) {
        PhieuDichVu pttx = mapper.toPhieuTinhTrangXe(request);
        return dao.save(pttx);
    }
}
