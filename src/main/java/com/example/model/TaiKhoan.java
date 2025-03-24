package com.example.model;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "VaiTro", discriminatorType = DiscriminatorType.STRING, columnDefinition = "NVARCHAR(20)")
@Table(name = "TaiKhoan")
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "idTaiKhoan")
public class TaiKhoan {
    @Id
    @Column(name = "IdTaiKhoan")
    private String idTaiKhoan;

    @Column(name = "MatKhau")
    private String matKhau;

    @Column(name = "TrangThai")
    private Boolean trangThai;

    @Column(name = "VaiTro", insertable = false, updatable = false, columnDefinition = "NVARCHAR(20)")
    private String vaiTro;
}
