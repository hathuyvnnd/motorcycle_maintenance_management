package com.example.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.Set;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "DichVu")
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "idDichVu")
public class DichVu {
    @Id
    @Column(name = "IdDichVu")
    private String idDichVu;

    @Column(name = "TenDichVu")
    private String tenDichVu;

    @Column(name = "GiaDichVu")
    private float giaDichVu;

    @Column(name = "TrangThai")
    private String trangThai;

    @Column(name = "MoTa")
    private String moTa;

    @OneToMany(mappedBy = "dichVu", fetch = FetchType.LAZY)
    // @JsonManagedReference
    Set<PhieuDichVuCT> phieuDichVuList;

}
