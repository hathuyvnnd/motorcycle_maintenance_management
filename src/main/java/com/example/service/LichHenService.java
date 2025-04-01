package com.example.service;

import com.example.dto.request.lichhen.LichHenCreateRequest;
import com.example.dto.request.lichhen.LichHenUpdateRequest;
import com.example.model.LichHen;

public interface LichHenService extends CrudService<LichHen, String> {
    public LichHen createLichHenRequest(LichHenCreateRequest request);
    public LichHen updateLichHenRequest(String id, LichHenUpdateRequest request);
    boolean existsByUsername(String name);
}
