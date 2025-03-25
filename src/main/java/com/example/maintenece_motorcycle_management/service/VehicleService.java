package com.example.maintenece_motorcycle_management.service;

import com.example.maintenece_motorcycle_management.dao.LoaiXeDao;
import com.example.maintenece_motorcycle_management.model.LoaiXe;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class VehicleService {
    private final LoaiXeDao vehicleService;

    public List<LoaiXe> findAll() {
        return vehicleService.findAll();
    }

    public Optional<LoaiXe> findById(String id) {
        return vehicleService.findById(id);
    }

}
