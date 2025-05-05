package com.example.dto.reponse;

import java.sql.Date;
import java.time.LocalDateTime;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class FindHoaDonResponse {
     private String idHoaDon;
    private String khachHang;
    private String nhanVien;
    private String phieuDichVuHD;
    private Date ngayTao;
    private boolean phuongThucThanhToan;
    private boolean trangThaiThanhToan;
    private double tongTien;
    private String bienSoXe;  
}
