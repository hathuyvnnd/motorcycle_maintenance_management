package com.example.service_impl;

import com.example.dao.LichHenCTDao;
import com.example.model.LichHenCT;
import com.example.service.LichHenCTService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE,makeFinal = true)
public class LichHenCTServiceImpl implements LichHenCTService {
    LichHenCTDao dao;
    public String generateNewId() {
        // Lấy ID cuối cùng
        String lastId = dao.findLastId();

        // Nếu không có ID, tạo ID đầu tiên
        if (lastId == null) {
            return "LHCT001";
        }

        // Lấy phần số từ ID (bỏ phần "KH") và tăng nó lên
        int number = Integer.parseInt(lastId.substring(4));
        number++;

        // Ghép phần số mới với "KH"
        return String.format("LHCT%03d", number); // Định dạng với 3 chữ số, ví dụ:KH002

    }
    public void saveAll(List<LichHenCT> lichHenCTList) {
        dao.saveAll(lichHenCTList);
    }

    @Override
    public List<LichHenCT> findAll() {
        return LichHenCTService.super.findAll();
    }

    @Override
    public LichHenCT findById(String s) {
        return LichHenCTService.super.findById(s);
    }

    @Override
    public LichHenCT create(LichHenCT entity) {
        return dao.save(entity);
    }

    @Override
    public void update(LichHenCT entity) {
        LichHenCTService.super.update(entity);
    }

    @Override
    public void deleteById(String s) {
        LichHenCTService.super.deleteById(s);
    }

    @Override
    public boolean exitsById(String s) {
        return LichHenCTService.super.exitsById(s);
    }
}
