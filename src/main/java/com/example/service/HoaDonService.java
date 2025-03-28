package com.example.service;

import java.util.List;

import org.springframework.data.jpa.repository.Query;

import com.example.model.HoaDon;
import com.example.model.KhachHang;

public interface HoaDonService extends CrudService<HoaDon, String> {
List<HoaDon> hoaDonByKh(KhachHang kh);
}
