package com.example.dto.request.hoadon;

import java.util.Date;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class HoaDonRequest {
    String idHoaDon;
    String idKhachHang;
    String idPhieuDichVu;
    String idLichHen;
    String idNhanVienTao;
    String phuongThucThanhToan;
    float tongTien;
    Date ngayTao;
    boolean trangThaiThanhToan;
}
