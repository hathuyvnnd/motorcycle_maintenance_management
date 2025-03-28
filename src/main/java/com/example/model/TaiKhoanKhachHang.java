package com.example.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@SuperBuilder
@DiscriminatorValue("Khách hàng")
public class TaiKhoanKhachHang extends TaiKhoan {
    @OneToOne(mappedBy = "taiKhoanKH", fetch = FetchType.EAGER)
    private KhachHang khachHang;
}
