package com.calendar.service.sign;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Service;

@Service
public class SessionAndCookieSettingService {

    //cookie&session 에 userId
    public void remember(HttpServletRequest req, HttpServletResponse res, Long userId) {
        req.getSession().setAttribute("userId", userId);
        req.getSession().setMaxInactiveInterval(1800);

        Cookie rememberMeCookie = new Cookie("rememberMe", String.valueOf(userId));
        rememberMeCookie.setSecure(true);
        rememberMeCookie.setHttpOnly(true);
        rememberMeCookie.setMaxAge(7 * 24 * 60 * 60);
        rememberMeCookie.setPath("/");
        res.addCookie(rememberMeCookie);
    }

    //cookie&session 삭제
    public void delete(HttpServletRequest req, HttpServletResponse res) {
        req.getSession().invalidate();
        Cookie rememberMeCookie = new Cookie("rememberMe", null);
        rememberMeCookie.setMaxAge(0);
        rememberMeCookie.setPath("/");
        res.addCookie(rememberMeCookie);
    }
}
