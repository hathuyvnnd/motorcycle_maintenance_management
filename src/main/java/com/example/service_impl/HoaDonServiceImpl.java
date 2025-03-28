package com.example.service_impl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.dao.HoaDonDao;
import com.example.dao.KhachHangDao;
import com.example.model.HoaDon;
import com.example.model.KhachHang;
import com.example.service.HoaDonService;

@Service("hoaDonService")
public class HoaDonServiceImpl implements HoaDonService {
    @Autowired
    HoaDonDao hdDao;

    @Autowired
    KhachHangDao khDao;

    @Override
    public List<HoaDon>hoaDonByKh(KhachHang kh){
        return hdDao.findByKhachHang(kh);
    }
    @Override
    public List<HoaDon>findAll(){
        return hdDao.findAll();
    }

    
}
