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

import java.util.List;
import java.util.Set;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "LoaiPhuTung")
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "idLoaiPT")
public class LoaiPhuTung {
    @Id
    @Column(name = "IdLoaiPT")
    private String idLoaiPT;

    @Column(name = "TenLoaiPT")
    private String tenLoaiPT;

    @Column(name = "MoTa")
    private String moTa;

    @Column(name = "GhiChu")
    private String ghiChu;

    @OneToMany(mappedBy = "loaiPT", fetch = FetchType.LAZY)
    // @JsonManagedReference
    Set<PhuTung> phuTungList;
}
