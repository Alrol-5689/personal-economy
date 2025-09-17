package com.economy.servlets;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import com.economy.model.User;
import com.economy.service.UserService;

import jakarta.persistence.PersistenceException;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet(name = "RegisterSV", urlPatterns = {"/users/register"})
public class RegisterSV extends HttpServlet {

    private static final long serialVersionUID = 1L;
    private final UserService userService = new UserService();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        req.getRequestDispatcher("/auth/register.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        String username = trim(req.getParameter("username"));
        String email = trim(req.getParameter("email"));
        String password = trim(req.getParameter("password"));
        String passwordConfirm = trim(req.getParameter("passwordConfirm"));

        List<String> errors = new ArrayList<>();
        if (isBlank(username)) errors.add("El nombre de usuario es obligatorio.");
        if (isBlank(email)) errors.add("El correo electrónico es obligatorio.");
        if (isBlank(password)) errors.add("La contraseña es obligatoria.");
        if (isBlank(passwordConfirm)) errors.add("Debe confirmar la contraseña.");
        if (!isBlank(password) && !password.equals(passwordConfirm)) {
            errors.add("Las contraseñas no coinciden.");
        }

        if (!errors.isEmpty()) {
            req.setAttribute("errors", errors);
            req.setAttribute("username", username);
            req.setAttribute("email", email);
            req.getRequestDispatcher("/auth/register.jsp").forward(req, resp);
            return;
        }

        User user = new User();
        user.setUsername(username);
        user.setEmail(email);
        user.setPassword(password);

        try {
            userService.createUser(user);
        } catch (IllegalArgumentException | PersistenceException e) {
            errors.add("No pudimos crear el usuario: " + e.getMessage());
            req.setAttribute("errors", errors);
            req.setAttribute("username", username);
            req.setAttribute("email", email);
            req.getRequestDispatcher("/auth/register.jsp").forward(req, resp);
            return;
        }

        resp.sendRedirect(req.getContextPath() + "/users/login?registered=1");
    }

    private static String trim(String value) {
        return value == null ? null : value.trim();
    }

    private static boolean isBlank(String value) {
        return value == null || value.trim().isEmpty();
    }
}
