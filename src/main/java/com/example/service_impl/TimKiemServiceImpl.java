package com.example.service_impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.model.DichVu;
import com.example.model.PhuTung;
import com.example.service.DichVuService;
import com.example.service.PhuTungService;

@Service
public class TimKiemServiceImpl  {
    @Autowired
    PhuTungService pTungService;

    @Autowired
    DichVuService dVuService;

    public List<Object> timKiem(String key){
        List<DichVu> listDV = dVuService.geListDVByKey(key);
        List<PhuTung> listPT = pTungService.geListPTByKey(key);

        List<Object> listTimKiem = new ArrayList<>();
        listTimKiem.addAll(listDV);
        listTimKiem.addAll(listPT);
        return listTimKiem;
    }
}
