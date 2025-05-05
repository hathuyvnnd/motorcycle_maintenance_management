package com.example.dto.reponse;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class TaiKhoanDN {
    private String token;
    private String vaiTro;
    private String idTaiKhoan;
}
