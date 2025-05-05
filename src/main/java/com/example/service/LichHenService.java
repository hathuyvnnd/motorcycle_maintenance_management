package com.example.service;

import java.util.List;

import com.example.dto.request.khachhang.LichHenDTO;
import com.example.dto.request.lichhen.LichHenCreateRequest;
import com.example.dto.request.lichhen.LichHenUpdateRequest;
import com.example.model.LichHen;

public interface LichHenService extends CrudService<LichHen, String> {
    public LichHen createLichHenRequest(LichHenCreateRequest request);
    public LichHen updateLichHenRequest(String id, LichHenUpdateRequest request);
    boolean existsByUsername(String name);
    Boolean updateTrangThai(String idLichHen, String trangThai);
    void taoLichHenByKH(LichHenDTO dto);
    List<LichHen> getLichHenChoXacNhan();
}
