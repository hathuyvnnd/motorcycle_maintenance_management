package com.example.service_impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.dao.PhieuDichVuCTDao;
import com.example.model.PhieuDichVu;
import com.example.model.PhieuDichVuCT;
import com.example.service.PhieuDichVuCTService;


@Service("phieuDichVuCT")
public class PhieuDichVuCTServiceImpl implements PhieuDichVuCTService {
@Autowired
PhieuDichVuCTDao pdvctDao;

    @Override
    public List<PhieuDichVuCT> getPhieuDichVuCTByPDV(PhieuDichVu phieuDichVu){
        return pdvctDao.findByPhieuDichVu(phieuDichVu);
    }

}
