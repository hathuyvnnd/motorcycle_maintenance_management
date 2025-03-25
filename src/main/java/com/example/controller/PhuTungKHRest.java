package com.example.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;

import com.example.service.PhuTungService;


@RequestMapping
public class PhuTungKHRest {
    @Autowired
    PhuTungService pTungService;
}
