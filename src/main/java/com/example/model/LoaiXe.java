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
@Table(name="LoaiXe")
public class LoaiXe {
    @Id
    private String idLoaiXe;
    private String tenLoaiXe;
    private String hinhAnh;
    private String hangSanXuat;
    private String dungTich;
    String moTa;

    @OneToMany(mappedBy = "idLoaiXe")
    List<LichHen> lichHenList;
}
