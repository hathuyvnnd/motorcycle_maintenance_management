package com.example.service;

import com.example.model.LoaiXe;

import java.util.List;

public interface LoaiXeService extends CrudService<LoaiXe, String> {
    List<LoaiXe> findAll();
}
