package com.example.service_impl;

import com.example.dao.PhieuGhiNhanTinhTrangXeDao;
import com.example.dto.request.tinhtrangxe.CreateTinhTrangXeRequest;
import com.example.dto.request.tinhtrangxe.UpdateTinhTrangRequest;
import com.example.exception.AppException;
import com.example.exception.ErrorCode;
import com.example.model.HoaDon;
import com.example.mapper.PhieuTinhTrangMapper;
import com.example.model.LichHen;

import com.example.model.PhieuGhiNhanTinhTrangXe;
import com.example.service.PhieuGhiNhanTinhTrangXeService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE,makeFinal = true)
@Service
public class PhieuTinhTrangServiceImpl implements PhieuGhiNhanTinhTrangXeService {
    PhieuGhiNhanTinhTrangXeDao dao;
    PhieuTinhTrangMapper mapper;

    @Override
    public List<PhieuGhiNhanTinhTrangXe> findAll() {
        return dao.findAll();
    }

    @Override
    public PhieuGhiNhanTinhTrangXe findById(String s) {
        return dao.findById(s).orElseThrow(() -> new AppException(ErrorCode.PHIEUTINHTRANG_NOTFOUND));
    }

    @Override
    public PhieuGhiNhanTinhTrangXe create(PhieuGhiNhanTinhTrangXe entity) {
        return dao.save(entity);
    }

    @Override
    public void update(PhieuGhiNhanTinhTrangXe entity) {
        dao.save(entity);
    }

    @Override
    public void deleteById(String s) {
        dao.deleteById(s);
    }

    @Override
    public boolean exitsById(String s) {
        return dao.existsById(s);
    }

    @Override
    public String generateNewId() {
        // Lấy ID cuối cùng
        String lastId = dao.findLastId();

        // Nếu không có ID, tạo ID đầu tiên
        if (lastId == null) {
            return "PGNX001";
        }

        // Lấy phần số từ ID (bỏ phần "KH") và tăng nó lên
        int number = Integer.parseInt(lastId.substring(4));
        number++;

        // Ghép phần số mới với "KH"
        return String.format("PGNX%03d", number); // Định dạng với 3 chữ số, ví dụ:KH002

    }

    //////////////// Cái này của hathuy đừng có xóa
    @Override
    public PhieuGhiNhanTinhTrangXe getPGNById(String idPhieuGNX) {
        return dao.findByIdPhieuGNX(idPhieuGNX);
    }

    //////////////////////////////////////////////////////
    @Override
    public PhieuGhiNhanTinhTrangXe createPhieuGhiNhanTinhTrangXeRequest(CreateTinhTrangXeRequest request) {
        PhieuGhiNhanTinhTrangXe pttx = mapper.toPhieuTinhTrangXe(request);
        return dao.save(pttx);
    }
    public PhieuGhiNhanTinhTrangXe findPhieuByLichHen(String lichHen){
        return dao.findByLichHen_IdLichHen(lichHen);
    }

    @Override
    public List<PhieuGhiNhanTinhTrangXe> searchByBienSoXeKeyword(String keyword) {
        // TODO Auto-generated method stub
        return dao.searchByBienSoXeKeyword(keyword);
    }
    @Override
    public PhieuGhiNhanTinhTrangXe updateGhiChu(UpdateTinhTrangRequest request){
        PhieuGhiNhanTinhTrangXe entity = dao.findByLichHen_IdLichHen(request.getIdLichHen());
        if (entity == null) {
            throw new RuntimeException("Không tìm thấy phiếu GNX cho lịch hẹn này!");
        }
    
        mapper.updatePhieuTinhTrangXe(request, entity); // Dùng mapper update vào entity cũ
        return dao.save(entity);
    }
   


}
