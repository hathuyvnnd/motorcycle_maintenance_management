package com.example.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@DiscriminatorValue("Nhân viên")
public class TaiKhoanNhanVien extends TaiKhoan {
    @OneToOne(mappedBy = "taiKhoanNV", fetch = FetchType.EAGER)
    private NhanVien nhanVien;
}
