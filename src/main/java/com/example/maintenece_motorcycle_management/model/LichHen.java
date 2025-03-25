package com.example.maintenece_motorcycle_management.model;

<<<<<<< Updated upstream:src/main/java/com/example/model/LichHen.java
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
=======
import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
>>>>>>> Stashed changes:src/main/java/com/example/maintenece_motorcycle_management/model/LichHen.java
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class LichHen {
    private String idLichHen;
    private Date thoiGian;

    @ManyToOne
    @JoinColumn(name="IdKhachHang")
<<<<<<< Updated upstream:src/main/java/com/example/model/LichHen.java
    private KhachHang idKhachHang;
=======
    @JsonIgnore
    private KhachHang khachHang;
>>>>>>> Stashed changes:src/main/java/com/example/maintenece_motorcycle_management/model/LichHen.java

    @ManyToOne
    @JoinColumn(name="IdLoaiXe")
    @JsonBackReference
    @JsonIgnore
    private LoaiXe idLoaiXe;

    private String trangThai;
    private String bienSoXe;
    private String ghiChu;
    private String dichVu;
}
