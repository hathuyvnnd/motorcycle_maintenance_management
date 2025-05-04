package com.example.dao;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import com.example.dto.RevenueStatsDTO;

import jakarta.persistence.EntityManager;
import jakarta.persistence.ParameterMode;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.Query;
import jakarta.persistence.StoredProcedureQuery;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Repository
public class RevenueStatsRepository {

    @PersistenceContext
    private EntityManager entityManager;

    @SuppressWarnings("unchecked")
    public Page<RevenueStatsDTO> findRevenueStatsByDateRange(LocalDate startDate, LocalDate endDate, int page,
            int size) {
        // Tạo StoredProcedureQuery
        StoredProcedureQuery query = entityManager.createStoredProcedureQuery("ThongKeDoanhThu");
        query.registerStoredProcedureParameter("TuNgay", LocalDate.class, ParameterMode.IN);
        query.registerStoredProcedureParameter("DenNgay", LocalDate.class, ParameterMode.IN);
        query.registerStoredProcedureParameter("Page", Integer.class, ParameterMode.IN);
        query.registerStoredProcedureParameter("Size", Integer.class, ParameterMode.IN);
        query.registerStoredProcedureParameter("TotalRecords", Integer.class, ParameterMode.OUT);
        query.setParameter("TuNgay", startDate);
        query.setParameter("DenNgay", endDate);
        query.setParameter("Page", page);
        query.setParameter("Size", size);

        // Thực thi query
        query.execute();

        // Lấy danh sách hóa đơn
        List<Object[]> resultList = query.getResultList();

        // Ánh xạ kết quả sang DTO
        List<RevenueStatsDTO> dtos = resultList.stream().map(result -> new RevenueStatsDTO(
                (String) result[0], // IdHoaDon
                (String) result[3], // HoTen
                ((java.sql.Date) result[1]).toLocalDate(), // NgayTao
                ((Number) result[4]).doubleValue() // TongTien
        )).toList();

        // Lấy tổng số bản ghi từ tham số đầu ra
        Integer totalRecords = (Integer) query.getOutputParameterValue("TotalRecords");
        long total = (totalRecords != null) ? totalRecords.longValue() : 0;

        // Trả về đối tượng Page
        return new PageImpl<>(dtos, Pageable.ofSize(size).withPage(page - 1), total);
    }
}
