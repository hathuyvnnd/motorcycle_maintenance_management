package com.example.controller.employeectrl.staffrestcontroller;

import java.text.SimpleDateFormat;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Calendar;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.dao.LichHenDao;
import com.example.model.LichHen;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;

@FieldDefaults(level = AccessLevel.PRIVATE,makeFinal = true)
@RequiredArgsConstructor
@RequestMapping("/api/staff/thong-ke")
@CrossOrigin("*")
@RestController
public class ThongKeController {
    LichHenDao lichHenRepository;
    @GetMapping()
public ResponseEntity<Map<String, Long>> thongKeLichHenTheoTuan() {
    LocalDate today = LocalDate.now();
    LocalDate startOfWeek = today.with(DayOfWeek.MONDAY);
    LocalDate endOfWeek = today.with(DayOfWeek.SUNDAY);

    // Chuyển về Date để query
    Date startDate = Date.from(startOfWeek.atStartOfDay(ZoneId.systemDefault()).toInstant());
    Date endDate = Date.from(endOfWeek.atTime(23, 59, 59).atZone(ZoneId.systemDefault()).toInstant());

    List<LichHen> lichHenTuanNay = lichHenRepository.findByThoiGianBetween(startDate, endDate);

    // Gom nhóm theo ngày (Date) đã bỏ giờ
    Map<Date, Long> thongKe = lichHenTuanNay.stream()
        .collect(Collectors.groupingBy(
            lh -> truncateTime(lh.getThoiGian()),
            Collectors.counting()
        ));

    // Bổ sung ngày không có lịch hẹn
    Calendar cal = Calendar.getInstance();
    cal.setTime(startDate);
    for (int i = 0; i < 7; i++) {
        Date date = truncateTime(cal.getTime());
        thongKe.putIfAbsent(date, 0L);
        cal.add(Calendar.DATE, 1);
    }

    // Optional: sắp xếp theo ngày
    Map<String, Long> result = thongKe.entrySet().stream()
        .sorted(Map.Entry.comparingByKey())
        .collect(Collectors.toMap(
            e -> new SimpleDateFormat("dd/MM/yyyy").format(e.getKey()),
            Map.Entry::getValue,
            (e1, e2) -> e1,
            LinkedHashMap::new
        ));

    return ResponseEntity.ok(result);
}

// Hàm bỏ phần giờ/phút/giây của Date
private Date truncateTime(Date date) {
    Calendar cal = Calendar.getInstance();
    cal.setTime(date);
    cal.set(Calendar.HOUR_OF_DAY, 0);
    cal.set(Calendar.MINUTE, 0);
    cal.set(Calendar.SECOND, 0);
    cal.set(Calendar.MILLISECOND, 0);
    return cal.getTime();
}

    
}
