package com.example.model;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;

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
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "VaiTro", discriminatorType = DiscriminatorType.STRING, columnDefinition = "NVARCHAR(20)")
@Table(name = "TaiKhoan")
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "idTaiKhoan")
public class TaiKhoan {
    @Id
    @Column(name = "IdTaiKhoan")
    private String idTaiKhoan;

    @Column(name = "MatKhau", length = 100)
//    @JsonIgnore
    private String matKhau;

    @Column(name = "TrangThai")
    private Boolean trangThai;

    @Column(name = "VaiTro", insertable = false, updatable = false, columnDefinition = "NVARCHAR(20)")
    private String vaiTro;
}
