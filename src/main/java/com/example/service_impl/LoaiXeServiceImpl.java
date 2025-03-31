package com.example.service_impl;

import com.example.dao.LoaiXeDao;
import com.example.model.LoaiXe;
import com.example.service.LoaiXeService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class LoaiXeServiceImpl implements LoaiXeService {

    private final LoaiXeDao loaiXeDao;

    @Override
    public List<LoaiXe> findAll() {
        return loaiXeDao.findAll();
    }

    @Override
    public LoaiXe findById(String s) {
        return loaiXeDao.findById(s).orElse(null);
    }
}
