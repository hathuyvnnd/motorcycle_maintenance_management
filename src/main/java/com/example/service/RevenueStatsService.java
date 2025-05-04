package com.example.service;

import java.time.LocalDate;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import com.example.dao.RevenueStatsRepository;
import com.example.dto.RevenueStatsDTO;

@Service

public class RevenueStatsService {
    @Autowired
    private RevenueStatsRepository revenueStatsRepository;

    public Page<RevenueStatsDTO> getRevenueStats(LocalDate startDate, LocalDate endDate, int page, int size) {
        return revenueStatsRepository.findRevenueStatsByDateRange(startDate, endDate, page, size);
    }
}
