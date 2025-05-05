package com.example.controller.admin;

import java.time.LocalDate;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.dto.RevenueStatsDTO;
import com.example.service.RevenueStatsService;

@RestController
@RequestMapping("/api/revenue-stats")
public class RevenueStatsController {

    @Autowired
    private RevenueStatsService revenueStatsService;

    @GetMapping
    public Page<RevenueStatsDTO> getRevenueStats(
            @RequestParam("startDate") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startDate,
            @RequestParam("endDate") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate endDate,
            @RequestParam(value = "page", defaultValue = "1") int page,
            @RequestParam(value = "size", defaultValue = "10") int size) {
        return revenueStatsService.getRevenueStats(startDate, endDate, page, size);
    }
}