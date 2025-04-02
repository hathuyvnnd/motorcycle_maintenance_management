package com.example.dto.request.tinhtrangxe;

import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class CreateTinhTrangXeRequest {
    String idPhieuGNX;
    Date ngayNhan;
    String moTaTinhTrangXe;
    String bienSoXe;
    String idNhanVien;
}