package com.example.service;

import java.util.List;
import java.util.Optional;

import com.example.model.HoaDon;

import com.example.dto.request.lichhen.LichHenCreateRequest;
import com.example.dto.request.tinhtrangxe.CreateTinhTrangXeRequest;
import com.example.model.LichHen;

import com.example.model.PhieuGhiNhanTinhTrangXe;

public interface PhieuGhiNhanTinhTrangXeService extends CrudService<PhieuGhiNhanTinhTrangXe, String> {
    PhieuGhiNhanTinhTrangXe getPGNById(String idPhieuGNX);

    String generateNewId();

    public PhieuGhiNhanTinhTrangXe createPhieuGhiNhanTinhTrangXeRequest(CreateTinhTrangXeRequest request);

}
