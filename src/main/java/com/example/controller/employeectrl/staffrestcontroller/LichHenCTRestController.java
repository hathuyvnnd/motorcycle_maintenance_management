package com.example.controller.employeectrl.staffrestcontroller;

import com.example.dao.LichHenCTDao;
import com.example.dto.reponse.ApiReponse;
import com.example.dto.reponse.LichHenResponse;
import com.example.model.DichVu;
import com.example.model.LichHen;
import com.example.model.LichHenCT;
import com.example.service_impl.LichHenCTServiceImpl;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@FieldDefaults(level = AccessLevel.PRIVATE,makeFinal = true)
@RequiredArgsConstructor
@RequestMapping("/api/staff/lich-hen-chi-tiet")
@CrossOrigin("*")
@RestController
public class LichHenCTRestController {
    LichHenCTServiceImpl lichHenCTService;
    LichHenCTDao dao;
    @GetMapping
    ApiReponse<List<LichHenCT>> getAllLichHen() {
        List<LichHenCT> lslh = lichHenCTService.findAll();
        ApiReponse<List<LichHenCT>> apiReponse = new ApiReponse<>();
        apiReponse.setResult(lslh);
        return apiReponse;
    }
    @GetMapping("/byidlichhen")
    ApiReponse<List<DichVu>> getAllLichHenById(@RequestParam String idLichHen) {
        List<LichHenCT> lslh = dao.findByIdLichHen_IdLichHen(idLichHen);
        ApiReponse<List<DichVu>> apiReponse = new ApiReponse<>();
        List<DichVu> dichVuList = lslh.stream()
                .map(LichHenCT::getIdDichVu)
                .collect(Collectors.toList());

        apiReponse.setResult(dichVuList);
        return apiReponse;
    }
}
