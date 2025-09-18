package com.economy.servlets;

import java.io.IOException;

import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

public abstract class BaseSV extends HttpServlet {

    private static final long serialVersionUID = 1L;

    protected Long requireUserId(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        HttpSession session = req.getSession(false);
        if (session == null) {
            redirectToLogin(req, resp);
            return null;
        }
        Object userIdAttr = session.getAttribute("userId");
        if (userIdAttr instanceof Long userId) {
            return userId;
        }
        redirectToLogin(req, resp);
        return null;
    }

    protected void redirectToLogin(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        resp.sendRedirect(req.getContextPath() + "/users/login");
    }
}
