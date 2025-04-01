package com.example.service_impl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.dao.PhieuGhiNhanTinhTtangXeDao;
import com.example.model.HoaDon;
import com.example.model.PhieuGhiNhanTinhTrangXe;
import com.example.service.PhieuGhiNhanTinhTrangXeService;

@Service
public class PhieuGNXTinhTrangXeServiceImpl implements PhieuGhiNhanTinhTrangXeService{
@Autowired
PhieuGhiNhanTinhTtangXeDao pgnDao;
    @Override
    public
    PhieuGhiNhanTinhTrangXe getPGNByHoaDon(HoaDon hoaDon){
        return pgnDao.findByHoaDon(hoaDon);
        
    }
    @Override
    public
    PhieuGhiNhanTinhTrangXe getPGNById(String idPhieuGNX){
        return pgnDao.findByIdPhieuGNX(idPhieuGNX);
    }
}
