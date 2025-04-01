package com.example.service;

import java.util.List;
import java.util.Optional;

import com.example.model.HoaDon;
import com.example.model.PhieuGhiNhanTinhTrangXe;

public interface PhieuGhiNhanTinhTrangXeService extends CrudService<PhieuGhiNhanTinhTrangXe, String> {

    PhieuGhiNhanTinhTrangXe getPGNByHoaDon(HoaDon hoaDon);
    PhieuGhiNhanTinhTrangXe getPGNById(String idPhieuGNX);

    String generateNewId();

}
