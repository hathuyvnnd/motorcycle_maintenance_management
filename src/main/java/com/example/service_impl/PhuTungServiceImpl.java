package com.example.service_impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.dao.PhuTungDao;
import com.example.model.LoaiPhuTung;
import com.example.model.PhuTung;
import com.example.service.PhuTungService;

import java.util.List;
@Service("phuTungService")
public class PhuTungServiceImpl implements PhuTungService{
    @Autowired
    private PhuTungDao pTungDao;


@Override
public List<PhuTung> findByLoaiPT(LoaiPhuTung loaiPT) {
    System.out.println("🔍 Đang tìm phụ tùng theo loại: " +loaiPT.getIdLoaiPT());
    List<PhuTung> list = pTungDao.findByLoaiPT(loaiPT);
    System.out.println("✅ Số phụ tùng tìm thấy: " + list.size());
    return list;
}

}

