package com.example.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name="PhieuDichVuCT")
public class PhieuDichVuCT {
    @Id
    private String idPhieuDichVuCT;
    private Float giaDichVu;
    private Date ngayThucHien;

    @ManyToOne
    @JoinColumn(name="IdDichVu")
    private DichVu dichVu;

    @ManyToOne
    @JoinColumn(name="IdPhieuDichVu")
    private PhieuDichVu phieuDichVuCT;
}
