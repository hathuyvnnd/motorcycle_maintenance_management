package com.example.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name="LoaiPhuTung")
public class LoaiPhuTung {
    @Id
    private String idLoaiPT;
    private String tenLoaiPT;
    private String moTa;
    private String ghiChu;

    @OneToMany(mappedBy = "loaiPT")
    List<PhuTung> phuTungList;
}
