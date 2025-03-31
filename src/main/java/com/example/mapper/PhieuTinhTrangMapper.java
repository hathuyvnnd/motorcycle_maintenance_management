package com.example.mapper;

import com.example.dto.reponse.LichHenResponse;
import com.example.dto.reponse.PhieuTinhTrangXeResponse;
import com.example.dto.request.tinhtrangxe.CreateTinhTrangXeRequest;
import com.example.model.LichHen;
import com.example.model.NhanVien;
import com.example.model.PhieuGhiNhanTinhTrangXe;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

@Mapper(componentModel = "spring")
public interface PhieuTinhTrangMapper {
    @Mapping(source = "idNhanVien", target = "nhanVien", qualifiedByName = "mapNhanVien")
    PhieuGhiNhanTinhTrangXe toPhieuTinhTrangXe(CreateTinhTrangXeRequest createTinhTrangXeRequest);
    @Named("mapNhanVien")
    default NhanVien mapNhanVien(String idNhanVien) {
        if (idNhanVien == null) return null;
        NhanVien nv = new NhanVien();
        nv.setIdNhanVien(idNhanVien);
        return nv;
    }
}
