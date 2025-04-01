package com.example.controller.customer;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.service_impl.TimKiemServiceImpl;
@RestController
@RequestMapping
public class TimKiemController {
    @Autowired
    private TimKiemServiceImpl timKiemService;

    @GetMapping("/api/timkiem")
    public ResponseEntity<List<Object>> search(@RequestParam("keyword") String keyword) {
        List<Object> results = timKiemService.timKiem(keyword);
        return ResponseEntity.ok(results);
    }
}
