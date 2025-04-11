package com.example.service_impl;

import com.example.dao.LoaiXeDao;
import com.example.model.LoaiXe;
import com.example.service.LoaiXeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class LoaiXeImpl implements LoaiXeService {
@Autowired
    LoaiXeDao lxDao;
    @Override
    public List<LoaiXe> findAll() {
        return lxDao.findAll();
    }
}
