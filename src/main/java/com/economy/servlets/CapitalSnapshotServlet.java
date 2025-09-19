package com.economy.servlets;

import java.io.IOException;
import java.time.YearMonth;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import com.economy.model.MonthlyCapital;
import com.economy.service.MonthlyCapitalService;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet(name = "CapitalSnapshotServlet", urlPatterns = {"/users/capital"})
public class CapitalSnapshotServlet extends BaseSV {

    private static final long serialVersionUID = 1L;
    private final MonthlyCapitalService capitalService = new MonthlyCapitalService();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Long userId = requireUserId(req, resp);
        if (userId == null) return;

        req.setAttribute("capital", new MonthlyCapital());
        req.getRequestDispatcher("/runway/capital-form.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Long userId = requireUserId(req, resp);
        if (userId == null) return;

        String monthRaw = trim(req.getParameter("month"));
        String savingsRaw = trim(req.getParameter("savingsAccount"));
        String piggyRaw = trim(req.getParameter("piggyBank"));
        String checkingRaw = trim(req.getParameter("checkingAccount"));
        String cashRaw = trim(req.getParameter("cash"));

        List<String> errors = new ArrayList<>();
        MonthlyCapital capital = new MonthlyCapital();

        YearMonth month = null;
        try {
            month = YearMonth.parse(monthRaw);
            capital.setMonth(month);
        } catch (Exception e) {
            errors.add("Mes no válido (usa el formato YYYY-MM)");
        }

        try { capital.setSavingsAccount(Double.parseDouble(savingsRaw)); }
        catch (NumberFormatException e) { errors.add("Cuenta de ahorro debe ser numérica"); }

        try { capital.setPiggyBank(Double.parseDouble(piggyRaw)); }
        catch (NumberFormatException e) { errors.add("Hucha debe ser numérica"); }

        try { capital.setCheckingAccount(Double.parseDouble(checkingRaw)); }
        catch (NumberFormatException e) { errors.add("Cuenta corriente debe ser numérica"); }

        try { capital.setCash(Double.parseDouble(cashRaw)); }
        catch (NumberFormatException e) { errors.add("Cash debe ser numérico"); }

        if (month != null) {
            Optional<MonthlyCapital> existing = capitalService.findByUserAndMonth(userId, month);
            existing.ifPresent(snapshot -> {
                capital.setId(snapshot.getId());
                capital.setVersion(snapshot.getVersion());
            });
        }

        if (errors.isEmpty()) {
            try {
                capitalService.create(capital, userId);
                resp.sendRedirect(req.getContextPath() + "/users/capital?success=1");
                return;
            } catch (IllegalArgumentException ex) {
                errors.add(ex.getMessage());
            }
        }

        req.setAttribute("errors", errors);
        req.setAttribute("capital", capital);
        req.getRequestDispatcher("/runway/capital-form.jsp").forward(req, resp);
    }

    private String trim(String value) {
        return value == null ? null : value.trim();
    }
}
