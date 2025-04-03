package com.example.service_impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.example.dao.HoaDonDao;
import com.example.model.HoaDon;
import com.example.service.HoaDonService;

@Service
public class HoaDonServiceImpl implements HoaDonService {
    @Autowired
    private HoaDonDao hoaDonDao;

    @Override
    public List<HoaDon> findAll() {
        return hoaDonDao.findAll();
    }
}
