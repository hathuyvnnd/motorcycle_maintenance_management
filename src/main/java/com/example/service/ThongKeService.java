package com.example.service;

import java.time.LocalDate;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import com.example.dao.ThongKeDAO;
import com.example.dto.ThongKeDTO;

@Service

public class ThongKeService {
    @Autowired
    private ThongKeDAO revenueStatsRepository;

    public Page<ThongKeDTO> getRevenueStats(LocalDate startDate, LocalDate endDate, int page, int size) {
        return revenueStatsRepository.findRevenueStatsByDateRange(startDate, endDate, page, size);
    }
}
