package com.example.dto;

import java.time.LocalDate;

public class ThongKeDTO {
    private String idHoaDon;
    private String hoTen;
    private LocalDate ngayTao;
    private Double tongTien;

    public ThongKeDTO(String idHoaDon, String hoTen, LocalDate ngayTao, Double tongTien) {
        this.idHoaDon = idHoaDon;
        this.hoTen = hoTen;
        this.ngayTao = ngayTao;
        this.tongTien = tongTien;
    }

    // Getters and Setters
    public String getIdHoaDon() {
        return idHoaDon;
    }

    public void setIdHoaDon(String idHoaDon) {
        this.idHoaDon = idHoaDon;
    }

    public String getHoTen() {
        return hoTen;
    }

    public void setHoTen(String hoTen) {
        this.hoTen = hoTen;
    }

    public LocalDate getNgayTao() {
        return ngayTao;
    }

    public void setNgayTao(LocalDate ngayTao) {
        this.ngayTao = ngayTao;
    }

    public Double getTongTien() {
        return tongTien;
    }

    public void setTongTien(Double tongTien) {
        this.tongTien = tongTien;
    }
}
