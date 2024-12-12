package com.calendar.common.filter;

import com.calendar.user.model.User;
import com.calendar.user.service.UserService;
import jakarta.servlet.*;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatusCode;

import java.io.IOException;
import java.util.Objects;

@RequiredArgsConstructor
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

        User sessionUser = (User) req.getSession().getAttribute("user");
        if(sessionUser != null){
            chain.doFilter(request, response);
            return;
        }

        Cookie[] cookies = req.getCookies();
        if(cookies != null){
            for(Cookie cookie : cookies){
                if("rememberMe".equals(cookie.getName())){
                    String email = cookie.getValue();
                    User user = Objects.requireNonNull(userService).getUserByEmail(email);
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
