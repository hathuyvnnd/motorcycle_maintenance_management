package com.example.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@DiscriminatorValue("Khách hàng")
public class TaiKhoanKhachHang extends TaiKhoan {
    @OneToOne(mappedBy = "taiKhoanKH", fetch = FetchType.EAGER)
    private KhachHang khachHang;
}
