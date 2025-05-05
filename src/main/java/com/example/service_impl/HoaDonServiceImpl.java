package com.example.service_impl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.dao.HoaDonDao;
import com.example.dao.KhachHangDao;
import com.example.dto.request.dichvu.PhieuDichVuCreateRequest;
import com.example.dto.request.hoadon.HoaDonRequest;
import com.example.exception.AppException;
import com.example.exception.ErrorCode;
import com.example.mapper.HoaDonMapper;
import com.example.model.HoaDon;
import com.example.model.KhachHang;
import com.example.model.PhieuDichVu;
import com.example.service.HoaDonService;

@Service("hoaDonService")
public class HoaDonServiceImpl implements HoaDonService {
    @Autowired
    HoaDonDao hdDao;

    @Autowired
    HoaDonMapper mapper;

    @Autowired
    KhachHangDao khDao;

    @Override
    public List<HoaDon> hoaDonByKh(KhachHang kh) {
        return hdDao.findByKhachHang(kh);
    }

    @Override
    public List<HoaDon> findAll() {
        return hdDao.findAll();
    }

    @Override
    public HoaDon getHoaDonById(String idHoaDon) {
        return hdDao.getReferenceById(idHoaDon);
    }

    public String generateNewId() {
        // Lấy ID cuối cùng
        String lastId = hdDao.findLastId();

        // Nếu không có ID, tạo ID đầu tiên
        if (lastId == null) {
            return "HD001";
        }

        // Lấy phần số từ ID (bỏ phần "DV") và tăng nó lên
        int number = Integer.parseInt(lastId.substring(2));
        number++;

        // Ghép phần số mới với "DV"
        return String.format("HD%03d", number); // Định dạng với 3 chữ số, ví dụ:DV002

    }

     public HoaDon createHoaDonRequest(HoaDonRequest request) {
        if (exitsById(request.getIdHoaDon()))
            throw new AppException(ErrorCode.LICHHEN_TONTAI);
        HoaDon hd = mapper.toHoaDon(request);
        HoaDon savedhd = hdDao.save(hd);
        System.out.println("HoaDon saved with id: " + savedhd.getIdHoaDon());  // Log the saved ID
        return savedhd;
    }
    @Override
    public HoaDon findById(String s) {
//        return phieuDichVuDAO.findById(s).orElseThrow(() ->
//                new AppException(ErrorCode.PHIEUDICHVU_NOTFOUND));
        System.out.println("Đang tìm hoadonhoadon với ID: " + s);
        return hdDao.findById(s).orElse(null);
    }

    @Override
    public List<HoaDon> searchHoaDonByKeyword(String keyword) {
        return hdDao.searchHoaDonByBienSoXeKeyword(keyword);
    }
    
    
}
