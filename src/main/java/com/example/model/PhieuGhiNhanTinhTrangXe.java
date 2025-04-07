package com.example.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "PhieuGhiNhanTinhTrangXe")
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "idPhieuGNX")
public class PhieuGhiNhanTinhTrangXe {
    @Id
    @Column(name = "IdPhieuGNX")
    private String idPhieuGNX;

    @Column(name = "NgayNhan")
    @Temporal(value = TemporalType.DATE)
    private Date ngayNhan;

    @Column(name = "MoTaTinhTrangXe")
    private String moTaTinhTrangXe;

    @ManyToOne
    @JoinColumn(name = "IdNhanVien")
    // @JsonBackReference
    private NhanVien nhanVien;
    @OneToOne(mappedBy = "phieuGNX")
    private PhieuDichVu phieuDichVu;

    @OneToOne
    @JoinColumn(name = "IdLichHen")
    private LichHen lichHen;
}
