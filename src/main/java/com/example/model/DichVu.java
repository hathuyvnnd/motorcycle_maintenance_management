package com.example.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name="DichVu")
public class DichVu {
    @Id
    private String idDichVu;
    private String tenDichVu;
    private float giaDichVu;
    private String trangThai;
    private String moTa;
    @JsonIgnore  // Chặn vòng lặp vô hạn
    @OneToMany(mappedBy = "dichVu")
    List<PhieuDichVuCT> phieuDichVuCTList;





}
