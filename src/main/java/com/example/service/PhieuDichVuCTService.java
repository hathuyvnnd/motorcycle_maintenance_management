package com.example.service;

import java.util.List;

import com.example.model.PhieuDichVu;
import com.example.model.PhieuDichVuCT;

public interface PhieuDichVuCTService extends CrudService<PhieuDichVuCT, String> {
public List<PhieuDichVuCT> getPhieuDichVuCTByPDV(PhieuDichVu phieuDichVu);
    
}
