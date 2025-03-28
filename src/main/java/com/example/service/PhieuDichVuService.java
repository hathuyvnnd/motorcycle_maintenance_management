package com.example.service;

import java.util.List;

import com.example.model.HoaDon;
import com.example.model.PhieuDichVu;

public interface PhieuDichVuService extends CrudService<PhieuDichVu, String> {
List<PhieuDichVu> findByHoaDon(HoaDon hoaDon);
}
