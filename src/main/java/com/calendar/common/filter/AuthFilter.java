package com.calendar.common.filter;

import com.calendar.controller.user.dto.UserResponseDto;
import com.calendar.controller.user.service.UserService;
import jakarta.servlet.*;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatusCode;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.Objects;

@Component
@RequiredArgsConstructor
@NoArgsConstructor(force = true)
public class AuthFilter implements Filter {

    private final UserService userService;

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws ServletException, IOException {
        HttpServletRequest req = (HttpServletRequest) request;
        HttpServletResponse res = (HttpServletResponse) response;

        UserResponseDto sessionUser = (UserResponseDto) req.getSession().getAttribute("user");
        if(sessionUser != null){
            chain.doFilter(request, response);
            return;
        }

        Cookie[] cookies = req.getCookies();
        if(cookies != null){
            for(Cookie cookie : cookies){
                if("rememberMe".equals(cookie.getName())){
                    String email = cookie.getValue();
                    UserResponseDto user = Objects.requireNonNull(userService).getUserByEmail(email);
                    if(user != null){
                        req.getSession().setAttribute("user", user);
                    }
                }
            }
        }

        if(req.getRequestURI().startsWith("/auth/login")){
            chain.doFilter(request, response);
            return;
        }

        res.setStatus(HttpStatusCode.valueOf(401).value());
    }
}
