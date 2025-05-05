package com.example.service_impl;

import com.example.dao.*;
import com.example.dto.request.khachhang.LichHenCTDTO;
import com.example.dto.request.khachhang.LichHenDTO;
import com.example.dto.request.lichhen.LichHenCreateRequest;
import com.example.dto.request.lichhen.LichHenUpdateRequest;
import com.example.exception.AppException;
import com.example.exception.ErrorCode;
import com.example.mapper.LichHenMapper;
import com.example.model.KhachHang;
import com.example.model.LichHen;
import com.example.model.LichHenCT;
import com.example.service.LichHenService;
import jakarta.transaction.Transactional;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE,makeFinal = true)
public class LichHenServiceImpl implements LichHenService {

    LichHenDao dao;
    LichHenMapper mapper;
    LichHenChiTietDao lhctDao;
    KhachHangDao khDao;
    LoaiXeDao loaiXeDao;
    DichVuDao dvDao;

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
    public String taoIdByLHCT() {
        // Lấy ID cuối cùng
        String lastId = dao.findLastId();

        // Nếu không có ID, tạo ID đầu tiên
        if (lastId == null) {
            return "LHCT001";
        }

        // Lấy phần số từ ID (bỏ phần "KH") và tăng nó lên
        int number = Integer.parseInt(lastId.substring(2));
        number++;

        // Ghép phần số mới với "KH"
        return String.format("LHCT%03d", number); // Định dạng với 3 chữ số, ví dụ:KH002

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

    @Override
    public void taoLichHenByKH(LichHenDTO dto) {
        if (dao.existsByThoiGianAndBienSoXe(dto.getThoiGian(), dto.getBienSoXe())) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "❗ Bạn đã có lịch cho xe vào ngày này.");
        }
        LichHen lh = new LichHen();
        lh.setIdLichHen(generateNewId());
        lh.setThoiGian(dto.getThoiGian());
        lh.setBienSoXe(dto.getBienSoXe());
        lh.setTrangThai(dto.getTrangThai());
        lh.setIdKhachHang(khDao.findById(dto.getIdKhachHang()).orElseThrow());
        lh.setIdLoaiXe(loaiXeDao.findById(dto.getIdLoaiXe()).orElseThrow());

        dao.save(lh);

        for (LichHenCTDTO ctDto : dto.getLichHenCTList()) {
            LichHenCT ct = new LichHenCT();
            ct.setIdLichHenCT(taoIdByLHCT());
            ct.setIdLichHen(lh);
            ct.setIdDichVu(dvDao.findById(ctDto.getIdDichVu()).orElseThrow());
            ct.setGhiChu(ctDto.getGhiChu());
            lhctDao.save(ct);
        }
    }

    @Override
    public List<LichHen> getLichHenChoXacNhan() {
        Date now = new Date();
    // return dao.findByTrangThaiAndThoiGianAfter("Chờ xác nhận", now);
    return dao.findByTrangThai("Chờ Xác Nhận");
    }



    public List<LichHen> layTatCaLichHenNgoaiTrangThaiChinh() {
        List<String> excludedStatuses = Arrays.asList("Chờ xác nhận", "Đã xác nhận", "Hoàn tất","Hoàn thành");
    List<LichHen> lichHens = dao.findByTrangThaiNotIn(excludedStatuses);

    // List<LichHen> filteredLichHen = lslh.stream()
    //         .filter(lichHen -> {
    //             String trangThai = lichHen.getTrangThai();
    //             return !"Chờ xác nhận".equalsIgnoreCase(trangThai)
    //                 && !"Đã xác nhận".equalsIgnoreCase(trangThai)
    //                 && !"Hoàn tất".equalsIgnoreCase(trangThai);
    //         })
    //         .collect(Collectors.toList());

    return lichHens;
}
public Boolean updateNgay(String id, Date ngay){
    LichHen lh = dao.findById(id).orElseThrow(() -> new AppException(ErrorCode.LICHHEN_NOTFOUND));
    lh.setThoiGian(ngay);
    dao.save(lh);
    return true;
}

    @Override
    public List<LichHen> getLichHenByKh(KhachHang kh) {
        return dao.findLichHenByIdKhachHang(kh);
    }

    @Override
    public LichHen getLichHenById(String id) {
        return dao.findLichHenByIdLichHen(id);
    }

}
