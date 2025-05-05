package com.example.security;

import com.example.model.TaiKhoan;
import com.example.service.TaiKhoanService;
import com.example.util.JwtToken;
import io.jsonwebtoken.Claims;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Collections;

@Component
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final JwtToken jwtToken;
    private final TaiKhoanService taiKhoanService;

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain)
            throws ServletException, IOException {

        // Kiểm tra xem endpoint có yêu cầu bảo mật không
        String path = request.getRequestURI();
        if (path.startsWith("/api/dangky") || path.startsWith("/views/")) {
            // Nếu là các endpoint không yêu cầu bảo mật, bỏ qua bộ lọc JWT
            filterChain.doFilter(request, response);
            return;
        }

        // Kiểm tra header Authorization để lấy token JWT
        String authHeader = request.getHeader("Authorization");

        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            // Nếu không có token, bỏ qua và tiếp tục xử lý các bộ lọc khác
            filterChain.doFilter(request, response);
            return;
        }

        // Lấy token từ header Authorization
        String token = authHeader.substring(7);
        // Giải mã token và kiểm tra tính hợp lệ
        Claims claims = jwtToken.getClaimsFromToken(token);

        String idTaiKhoan = claims.getSubject();
        String role = claims.get("vaiTro", String.class);

        // Xác thực người dùng và thiết lập thông tin vào context của Spring Security
        if (idTaiKhoan != null && SecurityContextHolder.getContext().getAuthentication() == null) {
            TaiKhoan taiKhoan = taiKhoanService.findById(idTaiKhoan);
            if (taiKhoan != null) {
                UsernamePasswordAuthenticationToken authToken =
                        new UsernamePasswordAuthenticationToken(
                                new User(idTaiKhoan, "", Collections.singleton(() -> role)),
                                null,
                                Collections.singleton(() -> role)
                        );
                authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                SecurityContextHolder.getContext().setAuthentication(authToken);
            }
        }

        filterChain.doFilter(request, response);
    }

}
