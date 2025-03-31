package com.example.service;

import com.example.dto.request.lichhen.LichHenCreateRequest;
import com.example.dto.request.tinhtrangxe.CreateTinhTrangXeRequest;
import com.example.model.LichHen;
import com.example.model.PhieuGhiNhanTinhTrangXe;

public interface PhieuGhiNhanTinhTrangXeService extends CrudService<PhieuGhiNhanTinhTrangXe, String> {
    String generateNewId();
    public PhieuGhiNhanTinhTrangXe createPhieuGhiNhanTinhTrangXeRequest(CreateTinhTrangXeRequest request);

}
