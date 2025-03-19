package com.example.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name="TaiKhoan")
public class TaiKhoan {
    @Id
    private String idTaiKhoan;
    private String matKhau;
    private Boolean trangThai;
    private String vaiTro;
}
