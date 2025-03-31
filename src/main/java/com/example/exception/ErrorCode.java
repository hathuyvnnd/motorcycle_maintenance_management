package com.example.exception;

import lombok.Getter;

@Getter
public enum ErrorCode {
    INVALID_KEY(2,"Invalid key enum."),
    USER_EXISTED(1001,"User existed."),
    LICHHEN_TONTAI(1002,"Lich hen da ton tai."),
    PHIEUTINHTRANG_TONTAI(1003,"Phieu da ton tai."),
    USER_NOTFOUND(3001,"User not found."),
    PHIEUTINHTRANG_NOTFOUND(3003,"Khong tim thay phieu"),
    USERNAME_NOTFOUND(3002,"Username not found"),
    UNCATEGORIZED_EXCEPTION(1,"Uncategorized exception."),
    UNAUTHENTICATED(2,"Authentication fail"),
    USERNAME_INVALID(2002,"Username invalid."),
    PASSWORD_INVALID(2001,"Password invalid.")
    ;
    private int code;
    private String message;

    ErrorCode(int code, String message) {
        this.code = code;
        this.message = message;
    }
}
