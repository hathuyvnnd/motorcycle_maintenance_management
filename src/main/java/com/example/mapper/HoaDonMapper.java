package com.example.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

import com.example.dto.request.hoadon.HoaDonRequest;
import com.example.model.HoaDon;
import com.example.model.KhachHang;
import com.example.model.NhanVien;
import com.example.model.PhieuDichVu;

@Mapper(componentModel = "spring")
public interface HoaDonMapper {
    @Mapping(source = "idKhachHang", target = "khachHang", qualifiedByName = "mapKhachHang")
    @Mapping(source = "idNhanVienTao", target = "nhanVien", qualifiedByName = "mapNhanVien")
    @Mapping(source = "idPhieuDichVu", target = "phieuDichVuHD", qualifiedByName = "mapPhieuDV")
    HoaDon toHoaDon(HoaDonRequest request);

    @Named("mapKhachHang")
    default KhachHang mapKhachHang(String idKhachHang) {
        if (idKhachHang == null) return null;
        KhachHang khachHang = new KhachHang();
        khachHang.setIdKhachHang(idKhachHang);
        return khachHang;
    }



    @Named("mapNhanVien")
    default NhanVien mapNhanVien(String idNhanVienTao) {
        if (idNhanVienTao == null) return null;
        NhanVien nhanVien = new NhanVien();
        nhanVien.setIdNhanVien(idNhanVienTao);
        return nhanVien;
    }

    @Named("mapPhieuDV")
    default PhieuDichVu maPhieuDichVu(String idPhieuDichVu) {
        if (idPhieuDichVu == null) return null;
        PhieuDichVu phieuDichVu = new PhieuDichVu();
        phieuDichVu.setIdPhieuDichVu(idPhieuDichVu);
        return phieuDichVu;
    }
}
