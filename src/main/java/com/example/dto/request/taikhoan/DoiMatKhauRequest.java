package com.example.dto.request.taikhoan;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class DoiMatKhauRequest {
    private String id;
    private String matKhauCu;
    private String matKhauMoi;

}
