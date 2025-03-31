package com.example.service;

import com.example.model.KhachHang;

public interface KhachHangService extends CrudService<KhachHang, String> {
    KhachHang findTopBySoDienThoai(String sodienthoai);
}
