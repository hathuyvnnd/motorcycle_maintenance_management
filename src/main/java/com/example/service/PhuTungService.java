package com.example.service;

import java.util.List;
import com.example.model.LoaiPhuTung;
import com.example.model.PhuTung;

public interface PhuTungService extends CrudService<PhuTung, String> {
    List<PhuTung> findByLoaiPT(LoaiPhuTung loaiPT);
    List<PhuTung> geListPTByKey(String key);
  
}
