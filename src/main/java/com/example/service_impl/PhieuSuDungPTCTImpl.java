package com.example.service_impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.dao.PhieuSuDungPhuTungCTDao;
import com.example.model.PhieuDichVu;
import com.example.model.PhieuSuDungPhuTungCT;
import com.example.service.PhieuSuDungPhuTungCTService;

@Service
public class PhieuSuDungPTCTImpl implements PhieuSuDungPhuTungCTService{
    @Autowired
    PhieuSuDungPhuTungCTDao ptctDao;
    @Override
    public List<PhieuSuDungPhuTungCT> findByPDV(PhieuDichVu phieuDichVu){
        return ptctDao.findByPhieuDichVu(phieuDichVu);
    }
}
