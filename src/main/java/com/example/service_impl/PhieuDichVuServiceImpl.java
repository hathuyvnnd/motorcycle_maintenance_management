package com.example.service_impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.dao.PhieuDichVuDAO;
import com.example.model.HoaDon;
import com.example.model.PhieuDichVu;
import com.example.service.PhieuDichVuService;

@Service
public class PhieuDichVuServiceImpl implements PhieuDichVuService{
@Autowired
PhieuDichVuDAO pdvDao;
    @Override
    public List<PhieuDichVu> findByHoaDon(HoaDon hoaDon) {
        // TODO Auto-generated method stub
        return pdvDao.findByHoaDon(hoaDon);
    }
    
}
