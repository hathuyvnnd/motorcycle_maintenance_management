package com.example.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.*;

import java.util.List;

@Getter
@Setter
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
    @JsonIgnore  // Chặn vòng lặp vô hạn
    @OneToMany(mappedBy = "loaiPT")
    
    List<PhuTung> phuTungList;
}
