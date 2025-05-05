package com.example.service;

import java.util.List;



import com.example.model.HoaDon;
import com.example.model.KhachHang;

public interface HoaDonService extends CrudService<HoaDon, String> {
List<HoaDon> hoaDonByKh(KhachHang kh);
HoaDon getHoaDonById(String idHoaDon);
List<HoaDon> searchHoaDonByKeyword(String keyword);
}
