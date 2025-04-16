package com.example.service;

import com.example.model.TaiKhoan;

public interface TaiKhoanService extends CrudService<TaiKhoan, String> {
    public boolean resetPasswordByEmail(String email);
    String randomPassword(int length);
}
