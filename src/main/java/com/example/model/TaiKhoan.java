package com.example.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "TaiKhoan")
@Entity
public class TaiKhoan {
    @Id
    private String idTaiKhoan;

    private String matKhau;
    private Boolean trangThai;
    private String vaiTro;
    @OneToOne(mappedBy = "taiKhoan")
    private NhanVien nhanVien;
}
