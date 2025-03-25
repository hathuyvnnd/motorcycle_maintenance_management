package com.example.service_impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.dao.PhuTungDao;
import com.example.model.PhuTung;
import com.example.service.PhuTungService;

import java.util.List;
@Service("PhuTungService")
public class PhuTungServiceImpl implements PhuTungService{
    @Autowired
    private PhuTungDao pTungDao;

    public List<PhuTung> findAll(){
        return pTungDao.findAll();
    }
}
