package com.example.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TaiKhoan {
    private String idTaiKhoan;
    private String matKhau;
    private Boolean trangThai;
    private String vaiTro;
}
