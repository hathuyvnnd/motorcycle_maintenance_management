package com.example.service;

import com.example.model.PhieuGhiNhanTinhTrangXe;

public interface PhieuGhiNhanTinhTrangXeService extends CrudService<PhieuGhiNhanTinhTrangXe, String> {
    String generateNewId();
}
