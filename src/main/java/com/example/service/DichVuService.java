package com.example.service;

import java.util.List;

import com.example.model.DichVu;

public interface DichVuService extends CrudService<DichVu, String> {
List<DichVu> geListDVByKey(String key);
}
