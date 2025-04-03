package com.example.mapper;

import com.example.dto.request.phieudichvu.PhieuDichVuRequest;
import com.example.dto.request.tinhtrangxe.CreateTinhTrangXeRequest;
import com.example.model.NhanVien;
import com.example.model.PhieuDichVu;
import com.example.model.PhieuGhiNhanTinhTrangXe;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

@Mapper(componentModel = "spring")
public interface PhieuDichVuMapper {
    @Mapping(source = "idNhanVien", target = "nhanVien", qualifiedByName = "mapNhanVien")
    PhieuDichVu toPhieuDichVu(PhieuDichVuRequest request);
    @Named("mapNhanVien")
    default NhanVien mapNhanVien(String idNhanVien) {
        if (idNhanVien == null) return null;
        NhanVien nv = new NhanVien();
        nv.setIdNhanVien(idNhanVien);
        return nv;
    }
}
