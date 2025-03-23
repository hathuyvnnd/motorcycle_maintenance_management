package com.example.mapper;

import com.example.dto.reponse.LichHenResponse;
import com.example.dto.request.lichhen.LichHenCreateRequest;
import com.example.dto.request.lichhen.LichHenUpdateRequest;
import com.example.model.KhachHang;
import com.example.model.LichHen;
import com.example.model.LoaiXe;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.Named;


import java.util.List;

@Mapper(componentModel = "spring")
public interface LichHenMapper {
    @Mapping(source = "idLoaiXe.idLoaiXe", target = "idLoaiXe")
    @Mapping(source = "idKhachHang.idKhachHang", target = "idKhachHang")
    LichHenResponse toLichHenResponse(LichHen lichHen);

    @Mapping(source = "idLoaiXe.idLoaiXe", target = "idLoaiXe")
    @Mapping(source = "idKhachHang.idKhachHang", target = "idKhachHang")
    List<LichHenResponse> toLichHenReponse(List<LichHen> lichHen);

    @Mapping(source = "idLoaiXe", target = "idLoaiXe", qualifiedByName = "mapLoaiXe")
    @Mapping(source = "idKhachHang", target = "idKhachHang", qualifiedByName = "mapKhachHang")
    LichHen toLichHen(LichHenCreateRequest request);

    @Mapping(source = "idLoaiXe", target = "idLoaiXe", qualifiedByName = "mapLoaiXe")
    @Mapping(source = "idKhachHang", target = "idKhachHang", qualifiedByName = "mapKhachHang")
    void updateUser(@MappingTarget LichHen lichHen, LichHenUpdateRequest request);

    @Named("mapLoaiXe")
    default LoaiXe mapLoaiXe(String idLoaiXe) {
        if (idLoaiXe == null) return null;
        LoaiXe loaiXe = new LoaiXe();
        loaiXe.setIdLoaiXe(idLoaiXe);
        return loaiXe;
    }

    @Named("mapKhachHang")
    default KhachHang mapKhachHang(String idKhachHang) {
        if (idKhachHang == null) return null;
        KhachHang khachHang = new KhachHang();
        khachHang.setIdKhachHang(idKhachHang);
        return khachHang;
    }

}