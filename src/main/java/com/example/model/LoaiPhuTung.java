package com.example.model;

import jakarta.persistence.OneToMany;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class LoaiPhuTung {
    private String idLoaiPT;
    private String tenLoaiPT;
    private String moTa;
    private String ghiChu;

    @OneToMany(mappedBy = "idLoaiPT")
    List<PhuTung> phuTungList;
}
