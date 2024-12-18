package com.calendar.filter;

import com.calendar.model.User;
import com.calendar.service.crud.UserService;
import jakarta.servlet.*;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatusCode;

import java.io.IOException;
import java.util.Objects;

@NoArgsConstructor(force = true)
public class AuthFilter implements Filter {

    private final UserService userService;

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws ServletException, IOException {
        HttpServletRequest req = (HttpServletRequest) request;
        HttpServletResponse res = (HttpServletResponse) response;

        if (req.getRequestURI().startsWith("/swagger-ui/") || req.getRequestURI().startsWith("/v3/api-docs/")) {
            chain.doFilter(request, response);
            return;
        }

        Long userId = (Long) req.getSession().getAttribute("userId");
        if(userId != null){
            chain.doFilter(request, response);
            return;
        }

        Cookie[] cookies = req.getCookies();
        if(cookies != null){
            for(Cookie cookie : cookies){
                if("rememberMe".equals(cookie.getName())){
                    Long cookieValue = Long.valueOf(cookie.getValue());
                    User user = Objects.requireNonNull(userService).getUserById(cookieValue);
                    if(user != null){
                        req.getSession().setAttribute("userId", cookieValue);
                    }
                }
            }
        }

        if(req.getRequestURI().startsWith("/sign/login")){
            chain.doFilter(request, response);
            return;
        }

        res.setStatus(HttpStatusCode.valueOf(401).value());
    }
}
