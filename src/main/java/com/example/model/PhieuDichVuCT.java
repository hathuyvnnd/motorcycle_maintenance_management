package com.example.model;

import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PhieuDichVuCT {
    private String idPhieuDichVuCT;
    private Float giaDichVu;
    private Date ngayThucHien;

    @ManyToOne
    @JoinColumn(name="IdDichVu")
    private DichVu idDichVu;

    @ManyToOne
    @JoinColumn(name="IdPhieuDichVu")
    private DichVu idPhieuDichVu;
}
