package com.example.service_impl;

import com.example.dao.DichVuDao;
import com.example.model.DichVu;
import com.example.service.DichVuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Service("dichVuService")
public class DichVuServiceImpl implements DichVuService{
    @Autowired
    DichVuDao dvDao;

    @Override
    public List<DichVu> findAll(){
        return dvDao.findAll();
    }
}
