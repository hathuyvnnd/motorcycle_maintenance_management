package com.example.service_impl;

import com.example.dao.LichHenDao;
import com.example.dto.request.lichhen.LichHenCreateRequest;
import com.example.dto.request.lichhen.LichHenUpdateRequest;
import com.example.exception.AppException;
import com.example.exception.ErrorCode;
import com.example.mapper.LichHenMapper;
import com.example.model.LichHen;
import com.example.service.LichHenService;
import jakarta.transaction.Transactional;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE,makeFinal = true)
public class LichHenServiceImpl implements LichHenService {

    LichHenDao dao;
    LichHenMapper mapper;

    @Override
    public LichHen createLichHenRequest(LichHenCreateRequest request) {
        if (exitsById(request.getIdLichHen()))
            throw new AppException(ErrorCode.LICHHEN_TONTAI);
        LichHen lh = mapper.toLichHen(request);
        LichHen savedLichHen = dao.save(lh);
        System.out.println("LichHen saved with id: " + savedLichHen.getIdLichHen());  // Log the saved ID
        return savedLichHen;    }

    @Override
    public LichHen updateLichHenRequest(String id, LichHenUpdateRequest request){
        LichHen user = dao.getReferenceById(id);
        mapper.updateUser(user,request);
//        user.setName(request.getName());
//        user.setEmail(request.getEmail());
//        user.setPhone(request.getPhone());
//        user.setAddress(request.getAddress());
        return dao.save(user);
    }

    @Override
    public boolean existsByUsername(String name) {
        return false;
    }

    @Override
    public Boolean updateTrangThai(String idLichHen, String trangThai) {
        LichHen lh = dao.findById(idLichHen).orElseThrow(()-> new AppException(ErrorCode.LICHHEN_NOTFOUND));
        lh.setTrangThai(trangThai);
        dao.save(lh);
        return true;
    }

    @Override
    public List<LichHen> findAll() {
        return dao.findAll();
    }

    public List<LichHen> getLichHenToday() {
        Date today = new Date();

        return dao.findLichHenToday(today);
    }

    public List<LichHen> searchLichHenByBienSo(String bienSoXe) {
        return dao.findByBienSoXeContaining(bienSoXe);
    }

    @Override
    public LichHen findById(String s) {
        return dao.findById(s).orElseThrow(() -> new AppException(ErrorCode.LICHHEN_NOTFOUND));
    }

    @Override
    public LichHen create(LichHen entity) {
        return dao.save(entity);
    }

    @Override
    public void update(LichHen entity) {
        dao.save(entity);
    }

    @Override
    public void deleteById(String s) {
        dao.deleteById(s);
    }

    @Override
    public boolean exitsById(String s) {
        return dao.existsById(s);
    }

    public String generateNewId() {
        // Lấy ID cuối cùng
        String lastId = dao.findLastId();

        // Nếu không có ID, tạo ID đầu tiên
        if (lastId == null) {
            return "LH001";
        }

        // Lấy phần số từ ID (bỏ phần "KH") và tăng nó lên
        int number = Integer.parseInt(lastId.substring(2));
        number++;

        // Ghép phần số mới với "KH"
        return String.format("LH%03d", number); // Định dạng với 3 chữ số, ví dụ:KH002

    }

    public void updateLichHenTrangThai(String idLicHen) {
        LichHen lh = dao.findById(idLicHen).orElseThrow(() -> new AppException(ErrorCode.LICHHEN_NOTFOUND));
        System.out.println("🔄 Trước cập nhật trạng thái lịch hẹn: " + lh.getTrangThai());
        if (lh != null) {
            switch (lh.getTrangThai()) {
                case "Đã xác nhận":
                    lh.setTrangThai("Đang kiểm tra");
                    break;
                case "Đang kiểm tra":
                    lh.setTrangThai("Đang sửa chữa");
                    break;
                case "Đang sửa chữa":
                    lh.setTrangThai("Đã sửa chữa");
                    break;
                case "Đã sửa chữa":
                lh.setTrangThai("Chờ thanh toán");
                break;
                default:
                    System.out.println("Trạng thái không cần cập nhật.");
                    return;
            }
            dao.save(lh);
            System.out.println("🔄 Đã cập nhật trạng thái lịch hẹn: " + lh.getTrangThai());
        }else{
            System.out.println("Không tìm thấy lịch hẹn để cập nhật!");
        }
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
