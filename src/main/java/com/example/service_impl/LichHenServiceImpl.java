package com.example.service_impl;

import com.example.dao.LichHenDao;
import com.example.dto.request.lichhen.LichHenCreateRequest;
import com.example.dto.request.lichhen.LichHenUpdateRequest;
import com.example.exception.AppException;
import com.example.exception.ErrorCode;
import com.example.mapper.LichHenMapper;
import com.example.model.LichHen;
import com.example.service.LichHenService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE,makeFinal = true)
public class LichHenServiceImpl implements LichHenService {

    LichHenDao lichHenRepository;
    LichHenMapper mapper;

    @Override
    public LichHen createLichHenRequest(LichHenCreateRequest request) {
        if (exitsById(request.getIdLichHen()))
            throw new AppException(ErrorCode.LICHHEN_TONTAI);
        LichHen lh = mapper.toLichHen(request);
        return lichHenRepository.save(lh);
    }

    @Override
    public LichHen updateLichHenRequest(String id, LichHenUpdateRequest request){
        LichHen user = lichHenRepository.getReferenceById(id);
        mapper.updateUser(user,request);
//        user.setName(request.getName());
//        user.setEmail(request.getEmail());
//        user.setPhone(request.getPhone());
//        user.setAddress(request.getAddress());
        return lichHenRepository.save(user);
    }

    @Override
    public boolean existsByUsername(String name) {
        return false;
    }

    @Override
    public List<LichHen> findAll() {
        return lichHenRepository.findAll();
    }

    public List<LichHen> getLichHenToday() {
        LocalDate today = LocalDate.now();
        LocalDateTime startOfDay = today.atStartOfDay();
        LocalDateTime endOfDay = today.atTime(23, 59, 59);

        return lichHenRepository.findLichHenToday(startOfDay, endOfDay);
    }

    public List<LichHen> searchLichHenByBienSo(String bienSoXe) {
        return lichHenRepository.findByBienSoXeContaining(bienSoXe);
    }

    @Override
    public LichHen findById(String s) {
        return LichHenService.super.findById(s);
    }

    @Override
    public LichHen create(LichHen entity) {
        return LichHenService.super.create(entity);
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


//    @Override
//    public LichHenResponse getLichHenById(String id) {
//        return lichHenRepository.findById(id)
//                .map(lichHenMapper::toResponseDTO)
//                .orElse(null);
//    }
//
//    @Override
//    public LichHenResponse createLichHen(LichHenRequest requestDTO) {
//        LichHen lichHen = lichHenMapper.toEntity(requestDTO);
//        LichHen savedLichHen = lichHenRepository.save(lichHen);
//        return lichHenMapper.toResponseDTO(savedLichHen);
//    }
//
//    @Override
//    public LichHenResponse updateLichHen(String id, LichHenRequest requestDTO) {
//        if (lichHenRepository.existsById(id)) {
//            LichHen lichHen = lichHenMapper.toEntity(requestDTO);
//            lichHen.setIdLichHen(id); // Đảm bảo giữ nguyên ID khi cập nhật
//            LichHen updatedLichHen = lichHenRepository.save(lichHen);
//            return lichHenMapper.toResponseDTO(updatedLichHen);
//        }
//        return null;
//    }
//
//    @Override
//    public void deleteLichHen(String id) {
//        lichHenRepository.deleteById(id);
//    }
}
