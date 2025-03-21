package com.example.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "LoaiXe")
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "idLoaiXe")
public class LoaiXe {
    @Id
    @Column(name = "IdLoaiXe")
    private String idLoaiXe;

    @Column(name = "TenLoaiXe")
    private String tenLoaiXe;

    @Column(name = "HinhAnh")
    private String hinhAnh;

    @Column(name = "HangSanXuat")
    private String hangSanXuat;

    @Column(name = "DungTich")
    private String dungTich;

    @Column(name = "MoTa")
    private String moTa;

    @OneToMany(mappedBy = "idLoaiXe")
    @JsonManagedReference
    List<LichHen> lichHenList;
}
