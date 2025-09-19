package com.economy.servlets;

import java.io.IOException;

import com.economy.service.CashflowService;
import com.economy.service.CashflowService.CashflowReport;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet(name = "CashflowServlet", urlPatterns = {"/users/cashflow"})
public class CashflowServlet extends BaseSV {

    private static final long serialVersionUID = 1L;
    private final CashflowService cashflowService = new CashflowService();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Long userId = requireUserId(req, resp);
        if (userId == null) return;

        CashflowReport report = cashflowService.buildReport(userId);
        req.setAttribute("report", report);

        req.getRequestDispatcher("/cashflow/cashflow.jsp").forward(req, resp);
    }
}
