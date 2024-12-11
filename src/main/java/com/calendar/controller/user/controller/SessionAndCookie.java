package com.calendar.controller.user.controller;

import com.calendar.controller.user.dto.UserResponseDto;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class SessionAndCookie {

    public static void extracted(HttpServletRequest req, HttpServletResponse res, UserResponseDto user) {
        req.getSession().setAttribute("user", user);
        req.getSession().setMaxInactiveInterval(1800);

        Cookie rememberMeCookie = new Cookie("rememberMe", user.getEmail());
        rememberMeCookie.setMaxAge(7 * 24 * 60 * 60);
        rememberMeCookie.setPath("/");
        res.addCookie(rememberMeCookie);
    }
}
