package com.economy.servlets;

import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import com.economy.model.Expense;
import com.economy.service.ExpenseService;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet(name = "ExpenseFormServlet", urlPatterns = {"/users/expenses/new"})
public class ExpenseFormServlet extends BaseSV {

    private static final long serialVersionUID = 1L;
    private final ExpenseService expenseService = new ExpenseService();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Long userId = requireUserId(req, resp);
        if (userId == null) return;

        req.setAttribute("expense", new Expense());
        req.setAttribute("types", Expense.ExpenseType.values());
        req.getRequestDispatcher("/expenses/form.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Long userId = requireUserId(req, resp);
        if (userId == null) return;

        String name = trim(req.getParameter("name"));
        String description = trim(req.getParameter("description"));
        String amountRaw = trim(req.getParameter("amount"));
        String typeRaw = trim(req.getParameter("type"));
        String dateRaw = trim(req.getParameter("date"));

        Expense expense = new Expense();
        expense.setName(name);
        expense.setDescription(description);

        List<String> errors = new ArrayList<>();
        if (name == null || name.isEmpty()) errors.add("El nombre es obligatorio");

        try {
            double amount = Double.parseDouble(amountRaw);
            expense.setAmount(amount);
        } catch (NumberFormatException e) {
            errors.add("El importe debe ser numérico");
        }

        try {
            Expense.ExpenseType type = Expense.ExpenseType.valueOf(typeRaw);
            expense.setType(type);
        } catch (Exception ex) {
            errors.add("Tipo de gasto no válido");
        }

        if (dateRaw != null && !dateRaw.isBlank()) {
            try {
                expense.setDate(LocalDate.parse(dateRaw));
            } catch (Exception e) {
                errors.add("Fecha no válida");
            }
        }

        if (errors.isEmpty()) {
            try {
                expenseService.create(expense, userId);
                resp.sendRedirect(req.getContextPath() + "/users/expenses/new?success=1");
                return;
            } catch (IllegalArgumentException ex) {
                errors.add(ex.getMessage());
            }
        }

        req.setAttribute("errors", errors);
        req.setAttribute("expense", expense);
        req.setAttribute("types", Expense.ExpenseType.values());
        req.getRequestDispatcher("/expenses/form.jsp").forward(req, resp);
    }

    private String trim(String value) {
        return value == null ? null : value.trim();
    }
}
