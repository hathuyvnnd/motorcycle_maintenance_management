package com.example.service_impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.dao.LoaiPhuTungDao;
import com.example.model.LoaiPhuTung;
import com.example.model.PhuTung;
import com.example.service.LoaiPhuTungService;

@Service("loaiPhuTungService")
public class LoaiPhuTungServiceImpl implements LoaiPhuTungService {
  @Autowired
  LoaiPhuTungDao loaiPTDao;
  List<LoaiPhuTung> listLPT;

  @Override
  public List<LoaiPhuTung> findAll() {
    return loaiPTDao.findAll();

  }

  @Override
  public LoaiPhuTung findById(String idLoaiPT) {
    return loaiPTDao.findById(idLoaiPT).orElse(null);
  }

  @Override
  public LoaiPhuTung create(LoaiPhuTung loaiPhuTung) {

    // Sinh ID tự động
    String newId = generateNewId();
    loaiPhuTung.setIdLoaiPT(newId);
    ;
    return loaiPTDao.save(loaiPhuTung);
  }

  // Hàm sinh ID mới
  private String generateNewId() {
    // Lấy ID cuối cùng
    String lastId = loaiPTDao.findLastId();

    // Nếu không có ID, tạo ID đầu tiên
    if (lastId == null) {
      return "LPT001";
    }

    // Lấy phần số từ ID (bỏ phần "LPT") và tăng nó lên
    int number = Integer.parseInt(lastId.substring(2));
    number++;

    // Ghép phần số mới với "LPT"
    return String.format("LPT%03d", number); // Định dạng với 3 chữ số, ví dụ:LPT002

  }

  @Override
  public void update(LoaiPhuTung loaiPhuTung) {
    // Thông thường, với JpaRepository, update = save (nếu ID đã tồn tại)
    if (loaiPTDao.existsById(loaiPhuTung.getIdLoaiPT())) {
      loaiPTDao.save(loaiPhuTung);
    }
  }

  @Override
  public void deleteById(String id) {
    loaiPTDao.deleteById(id);
  }

  @Override
  public boolean exitsById(String id) {
    return loaiPTDao.existsById(id);
  }
}
