package com.example.mapper;

import com.example.dto.request.dichvu.PhieuDichVuCreateRequest;
import com.example.dto.request.lichhen.LichHenCreateRequest;
import com.example.dto.request.phieudichvu.PhieuDichVuRequest;
import com.example.model.*;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

@Mapper(componentModel = "spring")
public interface PhieuDichVuMapper {
    @Mapping(source = "idNhanVienTaoPhieu", target = "idNhanVienTaoPhieu", qualifiedByName = "mapNhanVien")
    @Mapping(source = "idPhieuGNX", target = "phieuGNX", qualifiedByName = "mapPhieuGNX")
    PhieuDichVu toPhieuDichVu(PhieuDichVuCreateRequest request);

    @Named("mapNhanVien")
    default NhanVien mapNhanVien(String idNhanVienTaoPhieu) {
        if (idNhanVienTaoPhieu == null) return null;
        NhanVien nhanVien = new NhanVien();
        nhanVien.setIdNhanVien(idNhanVienTaoPhieu);
        return nhanVien;
    }

    @Named("mapPhieuGNX")
    default PhieuGhiNhanTinhTrangXe mapKhachHang(String idPhieuGNX) {
        if (idPhieuGNX == null) return null;
        PhieuGhiNhanTinhTrangXe phieuGhiNhanTinhTrangXe = new PhieuGhiNhanTinhTrangXe();
        phieuGhiNhanTinhTrangXe.setIdPhieuGNX(idPhieuGNX);
        return phieuGhiNhanTinhTrangXe;
    }
}
