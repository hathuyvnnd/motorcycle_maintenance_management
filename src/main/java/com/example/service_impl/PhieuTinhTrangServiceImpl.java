package com.example.service_impl;

import com.example.dao.PhieuGhiNhanTinhTrangXeDao;
import com.example.exception.AppException;
import com.example.exception.ErrorCode;
import com.example.model.PhieuGhiNhanTinhTrangXe;
import com.example.service.PhieuGhiNhanTinhTrangXeService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE,makeFinal = true)
@Service
public class PhieuTinhTrangServiceImpl implements PhieuGhiNhanTinhTrangXeService {
    PhieuGhiNhanTinhTrangXeDao dao;

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
            return "PHNX001";
        }

        // Lấy phần số từ ID (bỏ phần "KH") và tăng nó lên
        int number = Integer.parseInt(lastId.substring(2));
        number++;

        // Ghép phần số mới với "KH"
        return String.format("PGNX%03d", number); // Định dạng với 3 chữ số, ví dụ:KH002

    }
}
