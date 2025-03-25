package com.example.maintenece_motorcycle_management.model;

<<<<<<< Updated upstream:src/main/java/com/example/model/LoaiXe.java
=======
import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
>>>>>>> Stashed changes:src/main/java/com/example/maintenece_motorcycle_management/model/LoaiXe.java
import jakarta.persistence.OneToMany;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class LoaiXe {
<<<<<<< Updated upstream:src/main/java/com/example/model/LoaiXe.java
    private String idLoaiXe;
    private String tenLoaiXe;
    private String hinhAnh;
    private String hangSanXuat;
    private String dungTich;
    String moTa;
=======
    @Id
    private String IdLoaiXe;
    private String TenLoaiXe;
    private String HinhAnh;
    private String HangSanXuat;
    private String DungTich;
    private String MoTa;
>>>>>>> Stashed changes:src/main/java/com/example/maintenece_motorcycle_management/model/LoaiXe.java

    @OneToMany(mappedBy = "idLoaiXe")
    @JsonIgnore
    List<LichHen> lichHenList;
}
