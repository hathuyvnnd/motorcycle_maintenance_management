package com.example.dto.request.khachhang;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.util.Date;
import java.util.List;


    @Data
    public class LichHenDTO {
        private String idLichHen;
        @JsonFormat (pattern = "yyyy-MM-dd")
        private Date thoiGian;
        private String idKhachHang;
        private String idLoaiXe;
        private String bienSoXe;
        private String trangThai;
        private List<LichHenCTDTO> lichHenCTList;
    }




