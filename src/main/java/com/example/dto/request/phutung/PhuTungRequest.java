package com.example.dto.request.phutung;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PhuTungRequest {
    private String id;
    private Integer soLuong;
    
}
