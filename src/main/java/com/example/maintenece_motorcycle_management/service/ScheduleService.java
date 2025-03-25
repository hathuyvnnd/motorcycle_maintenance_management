package com.example.maintenece_motorcycle_management.service;

import com.example.maintenece_motorcycle_management.dao.LichHenDao;
import com.example.maintenece_motorcycle_management.model.LichHen;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ScheduleService {

    private final LichHenDao lichHenDao;

    public LichHen save(LichHen lichHen) {
        return lichHenDao.save(lichHen);
    }
}
