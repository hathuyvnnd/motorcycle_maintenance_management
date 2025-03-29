package com.example.service;

import com.example.model.KhachHang;

public interface KhachHangService extends CrudService<KhachHang, String> {
    KhachHang findById(String s);
    String findIdKhachHangByTaiKhoanKH_IdTaiKhoan(String idTaiKhoan);


}
