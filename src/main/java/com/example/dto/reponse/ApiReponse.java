package com.example.dto.reponse;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL) //Reponse, null thi kh hien thi
@Getter
@Setter
public class ApiReponse<T> {
    private int code = 3206;
    private String message;
    private T result;

}
