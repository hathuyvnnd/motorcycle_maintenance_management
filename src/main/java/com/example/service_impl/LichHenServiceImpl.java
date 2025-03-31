package com.example.service_impl;

import com.example.dao.LichHenDao;
import com.example.model.LichHen;
import com.example.service.LichHenService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class LichHenServiceImpl implements LichHenService {

    private final LichHenDao lichHenDao;
    @Override
    public List<LichHen> findAll() {
        return lichHenDao.findAll();
    }

    @Override
    public LichHen findById(String s) {
        return LichHenService.super.findById(s);
    }

    @Override
    public LichHen create(LichHen entity) {
        return lichHenDao.save(entity);
    }

    @Override
    public void update(LichHen entity) {
        LichHenService.super.update(entity);
    }

    @Override
    public void deleteById(String s) {
        LichHenService.super.deleteById(s);
    }

    @Override
    public boolean exitsById(String s) {
        return LichHenService.super.exitsById(s);
    }
}
