package com.example.controller.employeectrl.staffrestcontroller;

import com.example.dto.reponse.ApiReponse;
import com.example.dto.reponse.LichHenResponse;
import com.example.dto.request.lichhen.LichHenCreateRequest;
import com.example.dto.request.lichhen.LichHenUpdateRequest;
import com.example.mapper.LichHenMapper;
import com.example.model.LichHen;
import com.example.service_impl.LichHenServiceImpl;
import jakarta.validation.Valid;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@FieldDefaults(level = AccessLevel.PRIVATE,makeFinal = true)
@RequiredArgsConstructor
@RequestMapping("/api/lich-hen")
@CrossOrigin("*")
@RestController
public class LichHenController {

     LichHenServiceImpl lichHenService;

     LichHenMapper mapper;
    // Lấy tất cả lịch hẹn
        @GetMapping
        ApiReponse<List<LichHenResponse>> getAllLichHen() {
            List<LichHen> lslh = lichHenService.findAll();
            List<LichHenResponse> lhrp = mapper.toLichHenReponse(lslh);
            ApiReponse<List<LichHenResponse>> apiReponse = new ApiReponse<>();

            apiReponse.setResult(lhrp);
            return apiReponse;
        }
        @GetMapping("/today")
        ApiReponse<List<LichHenResponse>> getLichHenToday() {
            List<LichHen> lslh = lichHenService.getLichHenToday();
            List<LichHenResponse> lhrp = mapper.toLichHenReponse(lslh);
            ApiReponse<List<LichHenResponse>> apiReponse = new ApiReponse<>();
            apiReponse.setResult(lhrp);
            return apiReponse;
        }

        @GetMapping("/search")
        public ApiReponse<List<LichHenResponse>> searchLichHen(@RequestParam String bienSoXe) {
            List<LichHen> result = lichHenService.searchLichHenByBienSo(bienSoXe);
            System.out.println("testt: "+result);
            List<LichHenResponse> lhrp = mapper.toLichHenReponse(result);
            ApiReponse<List<LichHenResponse>> apiReponse = new ApiReponse<>();
            apiReponse.setResult(lhrp);
            return apiReponse;
        }

        @PostMapping
        ApiReponse<LichHen> createUser(@RequestBody @Valid LichHenCreateRequest request){
            ApiReponse<LichHen> apiReponse = new ApiReponse<>();
            apiReponse.setResult(lichHenService.createLichHenRequest(request));
            return apiReponse;
        }
        @PutMapping("/{id}")
        ApiReponse<LichHen> updateUser(@PathVariable String id,@RequestBody LichHenUpdateRequest request){
            ApiReponse<LichHen> apiReponse = new ApiReponse<>();
            apiReponse.setResult(lichHenService.updateLichHenRequest(id, request));
            return apiReponse;
        }


//    @GetMapping("/{id}")
//    public ResponseEntity<LichHenResponse> getLichHenById(@PathVariable String id) {
//        return ResponseEntity.ok(lichHenService.getLichHenById(id));
//    }
//
//    @PostMapping
//    public ResponseEntity<LichHenResponse> createLichHen(@RequestBody LichHenRequest requestDTO) {
//        return ResponseEntity.ok(lichHenService.createLichHen(requestDTO));
//    }
//
//    @PutMapping("/{id}")
//    public ResponseEntity<LichHenResponse> updateLichHen(@PathVariable String id, @RequestBody LichHenRequest requestDTO) {
//        return ResponseEntity.ok(lichHenService.updateLichHen(id, requestDTO));
//    }
//
//    @DeleteMapping("/{id}")
//    public ResponseEntity<Void> deleteLichHen(@PathVariable String id) {
//        lichHenService.deleteLichHen(id);
//        return ResponseEntity.noContent().build();
//    }
}
