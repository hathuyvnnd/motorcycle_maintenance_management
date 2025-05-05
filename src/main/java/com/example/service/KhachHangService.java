package com.example.service;

import com.example.dto.request.khachhang.KhachHangDTO;
import com.example.model.KhachHang;
import com.example.model.TaiKhoan;

public interface KhachHangService extends CrudService<KhachHang, String> {
    KhachHang findById(String s);
    String findIdKhachHangByTaiKhoanKH_IdTaiKhoan(String idTaiKhoan);
    KhachHang dangKyKhachHang(String soDienThoai, String matKhau, String hoTen, String diaChi, String email);
    KhachHang getByEmail(String email);
    KhachHang getByTaiKhoan (TaiKhoan tk);
}
