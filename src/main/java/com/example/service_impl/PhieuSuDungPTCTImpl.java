package com.example.service_impl;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.dao.PhieuSuDungPhuTungCTDao;
import com.example.dto.request.phutung.PhuTungRequest;
import com.example.model.PhieuDichVu;
import com.example.model.PhieuDichVuCT;
import com.example.model.PhieuSuDungPhuTungCT;
import com.example.model.PhuTung;
import com.example.service.PhieuSuDungPhuTungCTService;

@Service
public class PhieuSuDungPTCTImpl implements PhieuSuDungPhuTungCTService{
    @Autowired
    PhieuSuDungPhuTungCTDao ptctDao;
    @Autowired
    PhuTungServiceImpl ptDao;
    @Autowired
    PhieuDichVuServiceImpl pdvsv;
    @Override
    public List<PhieuSuDungPhuTungCT> findByPDV(PhieuDichVu phieuDichVu){
        return ptctDao.findByPhieuDichVu(phieuDichVu);
    }

    public String generateNewId() {
        // Lấy ID cuối cùng
        String lastId = ptctDao.findLastId();

        // Nếu không có ID, tạo ID đầu tiên
        if (lastId == null) {
            return "PSTCT001";
        }

        // Lấy phần số từ ID (bỏ phần "KH") và tăng nó lên
        int number = Integer.parseInt(lastId.substring(5));
        number++;

        // Ghép phần số mới với "KH"
        return String.format("PSTCT%03d", number); // Định dạng với 3 chữ số, ví dụ:KH002

    }
     @Override
    public PhieuSuDungPhuTungCT create(PhieuSuDungPhuTungCT entity) {
        return ptctDao.save(entity);
    }


    public void updateChiTietPhieuDichVu(String idPhieuDichVu, List<PhuTungRequest> danhSachPhuTung) {
    List<PhieuSuDungPhuTungCT> danhSachTrongDB = ptctDao.findByPhieuDichVu_IdPhieuDichVu(idPhieuDichVu);
    PhieuDichVu pdv = pdvsv.findById(idPhieuDichVu);

    // Map từ ID phụ tùng -> Entity trong DB
    Map<String, PhieuSuDungPhuTungCT> mapTrongDB = danhSachTrongDB.stream()
            .collect(Collectors.toMap(pt -> pt.getPhuTung().getIdPhuTung(), pt -> pt));

    // Duyệt danh sách client gửi lên
    for (PhuTungRequest request : danhSachPhuTung) {
        String idPhuTung = request.getId();
        Integer soLuongMoi = request.getSoLuong();

        if (mapTrongDB.containsKey(idPhuTung)) {
            // Đã tồn tại: kiểm tra số lượng khác thì update
            PhieuSuDungPhuTungCT entity = mapTrongDB.get(idPhuTung);
            if (!entity.getSoLuong().equals(soLuongMoi)) {
                entity.setSoLuong(soLuongMoi);
                ptctDao.save(entity);
            }
            // Đã xử lý, bỏ khỏi map để biết cái nào chưa xử lý (tức là bị xóa)
            mapTrongDB.remove(idPhuTung);
        } else {
            // Chưa có: tạo mới
            PhieuSuDungPhuTungCT newEntity = new PhieuSuDungPhuTungCT();
            newEntity.setIdPhieuSuDungPhuTungCT(generateNewId());
            newEntity.setSoLuong(soLuongMoi);
            newEntity.setPhuTung(ptDao.findById(request.getId()));
            newEntity.setPhieuDichVu(pdv);
            ptctDao.save(newEntity);
        }
    }

    // Những cái còn lại trong map là bị xóa
    for (PhieuSuDungPhuTungCT toDelete : mapTrongDB.values()) {
        ptctDao.delete(toDelete);
    }
}
}
