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
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Collections;

@Component
@RequiredArgsConstructor
@Slf4j
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final JwtToken jwtToken;
    private final TaiKhoanService taiKhoanService;

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain)
            throws ServletException, IOException {

        String path = request.getRequestURI();
        if (path.startsWith("/api/dangky") || path.startsWith("/views/")) {
            filterChain.doFilter(request, response);
            return;
        }

        String authHeader = request.getHeader("Authorization");

        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            log.debug("No Authorization header or not Bearer token");
            filterChain.doFilter(request, response);
            return;
        }

        String token = authHeader.substring(7);
        try {
            if (!jwtToken.validateToken(token)) {
                log.warn("Invalid token: {}", token);
                filterChain.doFilter(request, response);
                return;
            }

            Claims claims = jwtToken.getClaimsFromToken(token);
            String idTaiKhoan = claims.getSubject();
            String role = claims.get("vaiTro", String.class);

            if (idTaiKhoan != null && SecurityContextHolder.getContext().getAuthentication() == null) {
                TaiKhoan taiKhoan = taiKhoanService.findById(idTaiKhoan);
                if (taiKhoan != null) {
                    UsernamePasswordAuthenticationToken authToken =
                            new UsernamePasswordAuthenticationToken(
                                    new User(idTaiKhoan, "", Collections.singleton(new SimpleGrantedAuthority(role))),
                                    null,
                                    Collections.singleton(new SimpleGrantedAuthority(role))
                            );
                    authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                    SecurityContextHolder.getContext().setAuthentication(authToken);
                    log.info("Authentication set for user: {} with role: {}", idTaiKhoan, role);
                } else {
                    log.warn("TaiKhoan not found for id: {}", idTaiKhoan);
                }
            }
        } catch (Exception e) {
            log.error("Error processing token: {}", e.getMessage());
        }

        filterChain.doFilter(request, response);
    }
}