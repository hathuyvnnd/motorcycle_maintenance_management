package com.example.controller.customer;

import com.example.model.LoaiXe;
import com.example.service.LoaiXeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping  ("/api/khachhang")
public class LoaiXeKHController {
    @Autowired
    LoaiXeService loaiXeService;
    @GetMapping ("/loaixe")
    public List<LoaiXe> getListLoaiXe(){
        return loaiXeService.findAll();
    }

}
