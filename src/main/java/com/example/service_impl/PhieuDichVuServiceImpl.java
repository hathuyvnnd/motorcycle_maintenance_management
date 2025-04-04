package com.example.service_impl;

import com.example.dao.*;
import com.example.dto.request.dichvu.PhieuDichVuCreateRequest;
import com.example.dto.request.lichhen.LichHenCreateRequest;
import com.example.dto.request.phieudichvu.PhieuDichVuRequest;
import com.example.exception.AppException;
import com.example.exception.ErrorCode;
import com.example.mapper.PhieuDichVuMapper;
import com.example.model.LichHen;
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
    PhieuDichVuDAO dao;
    DichVuDao dichVuDao;
    PhuTungDao phuTungDao;
    PhieuDichVuCTDao phieuDichVuCTDao;
    PhieuSuDungPhuTungCTDao phieuSuDungPhuTungCTDao;

    PhieuDichVuMapper mapper;

    public String generateNewId() {
        // Lấy ID cuối cùng
        String lastId = dao.findLastId();

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
    public PhieuDichVu createPhieuDichVuRequest(PhieuDichVuCreateRequest request) {
        if (exitsById(request.getIdPhieuDichVu()))
            throw new AppException(ErrorCode.LICHHEN_TONTAI);
        PhieuDichVu pdv = mapper.toPhieuDichVu(request);
        PhieuDichVu savedpdv = dao.save(pdv);
        System.out.println("PhieuDichVu saved with id: " + savedpdv.getIdPhieuDichVu());  // Log the saved ID
        return savedpdv;
    }

    @Override
    public PhieuDichVu findById(String s) {
//        return phieuDichVuDAO.findById(s).orElseThrow(() ->
//                new AppException(ErrorCode.PHIEUDICHVU_NOTFOUND));
        System.out.println("Đang tìm phiếu dịch vụ với ID: " + s);
        return dao.findById(s).orElse(null);    }
//    @Override
//    public PhieuDichVu createPhieuDichVuRequest(PhieuDichVuRequest request) {
//        PhieuDichVu pttx = mapper.toPhieuTinhTrangXe(request);
//        return dao.save(pttx);
//    }
}
