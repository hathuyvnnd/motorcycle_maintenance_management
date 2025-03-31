package com.example.service_impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.dao.LoaiPhuTungDao;
import com.example.model.LoaiPhuTung;
import com.example.service.LoaiPhuTungService;

@Service("loaiPhuTungService")
public class LoaiPhuTungServiceImpl implements LoaiPhuTungService {
    @Autowired
    LoaiPhuTungDao loaiPTDao;
    List<LoaiPhuTung> listLPT;

    @Override
    public List<LoaiPhuTung> findAll() {
        return loaiPTDao.findAll();

    }

    @Override
    public LoaiPhuTung findById(String idLoaiPT) {
        return loaiPTDao.findById(idLoaiPT).orElse(null);
    }

}
