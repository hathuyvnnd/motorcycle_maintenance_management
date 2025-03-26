package com.example.service;

import java.util.List;

import com.example.model.LoaiPhuTung;

public interface LoaiPhuTungService extends CrudService<LoaiPhuTung, String> {
    public LoaiPhuTung findById(String idLoaiPT);
}
