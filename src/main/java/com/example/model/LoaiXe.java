package com.example.model;

import jakarta.persistence.OneToMany;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class LoaiXe {
    private String idLoaiXe;
    private String tenLoaiXe;
    private String hinhAnh;
    private String hangSanXuat;
    private String dungTich;
    String moTa;

    @OneToMany(mappedBy = "idLoaiXe")
    List<LichHen> lichHenList;
}
