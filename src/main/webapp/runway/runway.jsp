<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="jakarta.tags.core" prefix="c" %>
<%@ taglib uri="jakarta.tags.fmt" prefix="fmt" %>
<%@ taglib uri="jakarta.tags.functions" prefix="fn" %>
<!DOCTYPE html>
<html lang="es">
<head>
    <meta charset="UTF-8">
    <title>Runway | Personal Economy</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/styles.css">
</head>
<body>
<header class="topbar">
    <h1>Runway</h1>
    <nav>
        <a href="${pageContext.request.contextPath}/users/home">Volver al inicio</a>
        <a href="${pageContext.request.contextPath}/user/cashflow">Ver cash flow</a>
        <a href="${pageContext.request.contextPath}/user/capital">Registrar capital</a>
    </nav>
</header>
<main class="container">
    <c:if test="${empty report.months}">
        <p>No hay datos todavía. Añade capital mensual y gastos para ver tu runway.</p>
    </c:if>
    <c:forEach var="yearEntry" items="${report.monthsByYear}">
        <section class="report">
            <h2>${yearEntry.key}</h2>
            <table class="report-table">
                <thead>
                    <tr>
                        <th>Concepto</th>
                        <c:forEach var="month" items="${yearEntry.value}">
                            <th>${month.monthLabel}</th>
                        </c:forEach>
                        <th>Total</th>
                        <th>Promedio</th>
                    </tr>
                </thead>
                <tbody>
                    <tr>
                        <th>Cuenta de ahorro</th>
                        <c:set var="sum" value="0"/>
                        <c:forEach var="month" items="${yearEntry.value}">
                            <c:set var="value" value="${month.capitalSnapshot != null ? month.capitalSnapshot.savingsAccount : 0}"/>
                            <td><fmt:formatNumber value="${value}" type="currency" currencySymbol="€"/></td>
                            <c:set var="sum" value="${sum + value}"/>
                        </c:forEach>
                        <td><fmt:formatNumber value="${sum}" type="currency" currencySymbol="€"/></td>
                        <td><fmt:formatNumber value="${sum / fn:length(yearEntry.value)}" type="currency" currencySymbol="€"/></td>
                    </tr>
                    <tr>
                        <th>Hucha</th>
                        <c:set var="sum" value="0"/>
                        <c:forEach var="month" items="${yearEntry.value}">
                            <c:set var="value" value="${month.capitalSnapshot != null ? month.capitalSnapshot.piggyBank : 0}"/>
                            <td><fmt:formatNumber value="${value}" type="currency" currencySymbol="€"/></td>
                            <c:set var="sum" value="${sum + value}"/>
                        </c:forEach>
                        <td><fmt:formatNumber value="${sum}" type="currency" currencySymbol="€"/></td>
                        <td><fmt:formatNumber value="${sum / fn:length(yearEntry.value)}" type="currency" currencySymbol="€"/></td>
                    </tr>
                    <tr>
                        <th>Cuenta corriente</th>
                        <c:set var="sum" value="0"/>
                        <c:forEach var="month" items="${yearEntry.value}">
                            <c:set var="value" value="${month.capitalSnapshot != null ? month.capitalSnapshot.checkingAccount : 0}"/>
                            <td><fmt:formatNumber value="${value}" type="currency" currencySymbol="€"/></td>
                            <c:set var="sum" value="${sum + value}"/>
                        </c:forEach>
                        <td><fmt:formatNumber value="${sum}" type="currency" currencySymbol="€"/></td>
                        <td><fmt:formatNumber value="${sum / fn:length(yearEntry.value)}" type="currency" currencySymbol="€"/></td>
                    </tr>
                    <tr>
                        <th>Cash</th>
                        <c:set var="sum" value="0"/>
                        <c:forEach var="month" items="${yearEntry.value}">
                            <c:set var="value" value="${month.capitalSnapshot != null ? month.capitalSnapshot.cash : 0}"/>
                            <td><fmt:formatNumber value="${value}" type="currency" currencySymbol="€"/></td>
                            <c:set var="sum" value="${sum + value}"/>
                        </c:forEach>
                        <td><fmt:formatNumber value="${sum}" type="currency" currencySymbol="€"/></td>
                        <td><fmt:formatNumber value="${sum / fn:length(yearEntry.value)}" type="currency" currencySymbol="€"/></td>
                    </tr>
                    <tr class="section-total">
                        <th>Total liquidez</th>
                        <c:set var="sum" value="0"/>
                        <c:forEach var="month" items="${yearEntry.value}">
                            <c:set var="value" value="${month.capitalSnapshot != null ? month.capitalSnapshot.totalAssets : 0}"/>
                            <td><fmt:formatNumber value="${value}" type="currency" currencySymbol="€"/></td>
                            <c:set var="sum" value="${sum + value}"/>
                        </c:forEach>
                        <td><fmt:formatNumber value="${sum}" type="currency" currencySymbol="€"/></td>
                        <td><fmt:formatNumber value="${sum / fn:length(yearEntry.value)}" type="currency" currencySymbol="€"/></td>
                    </tr>
                    <tr>
                        <th>Fijos / Obligatorios</th>
                        <c:set var="sum" value="0"/>
                        <c:forEach var="month" items="${yearEntry.value}">
                            <td><fmt:formatNumber value="${month.fixedExpenses}" type="currency" currencySymbol="€"/></td>
                            <c:set var="sum" value="${sum + month.fixedExpenses}"/>
                        </c:forEach>
                        <td><fmt:formatNumber value="${sum}" type="currency" currencySymbol="€"/></td>
                        <td><fmt:formatNumber value="${sum / fn:length(yearEntry.value)}" type="currency" currencySymbol="€"/></td>
                    </tr>
                    <tr>
                        <th>Variables necesarios</th>
                        <c:set var="sum" value="0"/>
                        <c:forEach var="month" items="${yearEntry.value}">
                            <td><fmt:formatNumber value="${month.necessaryExpenses}" type="currency" currencySymbol="€"/></td>
                            <c:set var="sum" value="${sum + month.necessaryExpenses}"/>
                        </c:forEach>
                        <td><fmt:formatNumber value="${sum}" type="currency" currencySymbol="€"/></td>
                        <td><fmt:formatNumber value="${sum / fn:length(yearEntry.value)}" type="currency" currencySymbol="€"/></td>
                    </tr>
                    <tr>
                        <th>Caprichos</th>
                        <c:set var="sum" value="0"/>
                        <c:forEach var="month" items="${yearEntry.value}">
                            <td><fmt:formatNumber value="${month.discretionaryExpenses}" type="currency" currencySymbol="€"/></td>
                            <c:set var="sum" value="${sum + month.discretionaryExpenses}"/>
                        </c:forEach>
                        <td><fmt:formatNumber value="${sum}" type="currency" currencySymbol="€"/></td>
                        <td><fmt:formatNumber value="${sum / fn:length(yearEntry.value)}" type="currency" currencySymbol="€"/></td>
                    </tr>
                    <tr>
                        <th>% Caprichos</th>
                        <c:forEach var="month" items="${yearEntry.value}">
                            <td><fmt:formatNumber value="${month.discretionaryPercentage}" maxFractionDigits="1"/> %</td>
                        </c:forEach>
                        <td colspan="2"></td>
                    </tr>
                    <tr class="section-total">
                        <th>Gasto total</th>
                        <c:set var="sum" value="0"/>
                        <c:forEach var="month" items="${yearEntry.value}">
                            <td><fmt:formatNumber value="${month.totalExpenses}" type="currency" currencySymbol="€"/></td>
                            <c:set var="sum" value="${sum + month.totalExpenses}"/>
                        </c:forEach>
                        <td><fmt:formatNumber value="${sum}" type="currency" currencySymbol="€"/></td>
                        <td><fmt:formatNumber value="${sum / fn:length(yearEntry.value)}" type="currency" currencySymbol="€"/></td>
                    </tr>
                    <tr>
                        <th>Total medio (últimos 12M)</th>
                        <c:forEach var="month" items="${yearEntry.value}">
                            <td><fmt:formatNumber value="${month.trailingExpenseAverage}" type="currency" currencySymbol="€"/></td>
                        </c:forEach>
                        <td colspan="2"></td>
                    </tr>
                    <tr class="highlight">
                        <th>Runway (meses)</th>
                        <c:forEach var="month" items="${yearEntry.value}">
                            <td><fmt:formatNumber value="${month.runwayMonths}" maxFractionDigits="1"/></td>
                        </c:forEach>
                        <td colspan="2"></td>
                    </tr>
                </tbody>
            </table>
        </section>
    </c:forEach>
</main>
</body>
</html>
