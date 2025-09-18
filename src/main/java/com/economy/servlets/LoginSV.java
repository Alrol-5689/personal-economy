package com.economy.servlets;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import com.economy.model.User;
import com.economy.service.UserService;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

@WebServlet(name="LoginSV", urlPatterns={"/users/login"})
public class LoginSV extends HttpServlet {

    private static final long serialVersionUID = 1L;
    private final UserService userService = new UserService();

    public LoginSV() { super(); }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) 
            throws ServletException, IOException {

        HttpSession session = req.getSession(false);
        if (session != null && session.getAttribute("userId") != null) {
            resp.sendRedirect(req.getContextPath() + "/users/home");
            return;
        }
        req.getRequestDispatcher("/auth/login.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) 
            throws ServletException, IOException {

        String username = trim(req.getParameter("username"));
        String password = trim(req.getParameter("password"));

        List<String> errors = new ArrayList<>();
        if (isBlank(username) || isBlank(password)) {
            errors.add("Username and password are required.");
        }

        User user = null;

        if (errors.isEmpty()) {
            user = userService.authenticate(trim(username), trim(password));
            if (user == null) errors.add("Invalid username or password.");         
        }
        if (!errors.isEmpty()) {
            req.setAttribute("errors", errors);
            req.getRequestDispatcher("/auth/login.jsp").forward(req, resp);
            return;
        }
        if (user == null) {
            resp.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Authentication failed unexpectedly.");
            return;
        }

        HttpSession session = req.getSession(true);
        session.setAttribute("userId", user.getId());
        session.setAttribute("username", user.getUsername());
        session.setMaxInactiveInterval(30 * 60); 

        resp.sendRedirect(req.getContextPath() + "/users/home");
    }

    // ===== Helpers =====
    private static String trim(String s) {
        return s == null ? null : s.trim();
    }

    private static boolean isBlank(String s) {
        return s == null || s.trim().isEmpty();
    }


    

}
