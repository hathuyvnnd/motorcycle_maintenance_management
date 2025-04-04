package com.example.service;

import java.util.List;

import com.example.model.PhieuDichVu;
import com.example.model.PhieuSuDungPhuTungCT;

public interface PhieuSuDungPhuTungCTService extends CrudService<PhieuSuDungPhuTungCT, String> {
List<PhieuSuDungPhuTungCT> findByPDV(PhieuDichVu phieuDichVu);
}
