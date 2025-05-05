package com.example.model;

import com.fasterxml.jackson.annotation.*;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.List;
import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "LichHen", indexes = @Index(name = "idx_trangThai", columnList = "trangThai"))
// @JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class,
// property = "idLichHen")
public class LichHen {
    @Id
    @Column(name = "IdLichHen")
    private String idLichHen;
    @Temporal(value = TemporalType.DATE)
    private Date thoiGian;

    @ManyToOne
    @JoinColumn(name = "IdKhachHang")
    // @JsonBackReference
    @JsonIdentityReference(alwaysAsId = true)
    private KhachHang idKhachHang;

    @ManyToOne
    @JoinColumn(name = "IdLoaiXe")
    // @JsonBackReference
    @JsonIdentityReference(alwaysAsId = true)
    private LoaiXe idLoaiXe;

    @Column(name = "TrangThai")
    private String trangThai;

    @Column(name = "BienSoXe")
    private String bienSoXe;

    @OneToMany(mappedBy = "idLichHen", fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JsonBackReference
    Set<LichHenCT> lichHenCTList;

    @OneToOne(mappedBy = "lichHen")
    private PhieuGhiNhanTinhTrangXe phieuGNX;
}
