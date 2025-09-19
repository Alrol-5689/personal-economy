<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="jakarta.tags.core" prefix="c" %>
<%@ taglib uri="jakarta.tags.fmt" prefix="fmt" %>
<%@ taglib uri="jakarta.tags.functions" prefix="fn" %>
<!DOCTYPE html>
<html lang="es">
<head>
    <meta charset="UTF-8">
    <title>Cash flow | Personal Economy</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/styles.css">
</head>
<body>
<header class="topbar">
    <h1>Cash flow</h1>
    <nav>
        <a href="${pageContext.request.contextPath}/users/home">Volver al inicio</a>
        <a href="${pageContext.request.contextPath}/users/runway">Ver runway</a>
        <a href="${pageContext.request.contextPath}/users/expenses/new">Registrar gasto</a>
    </nav>
</header>
<main class="container">
    <c:if test="${empty report.months}">
        <p>No hay datos todavía. Registra ingresos y gastos para comenzar.</p>
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
                        <th>Activos (salario/s)</th>
                        <c:set var="sum" value="0" scope="page"/>
                        <c:forEach var="month" items="${yearEntry.value}">
                            <td><fmt:formatNumber value="${month.activeIncome}" type="currency" currencySymbol="€"/></td>
                            <c:set var="sum" value="${sum + month.activeIncome}" scope="page"/>
                        </c:forEach>
                        <td><fmt:formatNumber value="${sum}" type="currency" currencySymbol="€"/></td>
                        <td><fmt:formatNumber value="${sum / fn:length(yearEntry.value)}" type="currency" currencySymbol="€"/></td>
                    </tr>
                    <tr>
                        <th>Ganancia de capitales</th>
                        <c:set var="sum" value="0" scope="page"/>
                        <c:forEach var="month" items="${yearEntry.value}">
                            <td><fmt:formatNumber value="${month.capitalGainIncome}" type="currency" currencySymbol="€"/></td>
                            <c:set var="sum" value="${sum + month.capitalGainIncome}" scope="page"/>
                        </c:forEach>
                        <td><fmt:formatNumber value="${sum}" type="currency" currencySymbol="€"/></td>
                        <td><fmt:formatNumber value="${sum / fn:length(yearEntry.value)}" type="currency" currencySymbol="€"/></td>
                    </tr>
                    <tr>
                        <th>Pasivos</th>
                        <c:set var="sum" value="0" scope="page"/>
                        <c:forEach var="month" items="${yearEntry.value}">
                            <td><fmt:formatNumber value="${month.passiveIncome}" type="currency" currencySymbol="€"/></td>
                            <c:set var="sum" value="${sum + month.passiveIncome}" scope="page"/>
                        </c:forEach>
                        <td><fmt:formatNumber value="${sum}" type="currency" currencySymbol="€"/></td>
                        <td><fmt:formatNumber value="${sum / fn:length(yearEntry.value)}" type="currency" currencySymbol="€"/></td>
                    </tr>
                    <tr>
                        <th>Otros ingresos</th>
                        <c:set var="sum" value="0" scope="page"/>
                        <c:forEach var="month" items="${yearEntry.value}">
                            <td><fmt:formatNumber value="${month.otherIncome}" type="currency" currencySymbol="€"/></td>
                            <c:set var="sum" value="${sum + month.otherIncome}" scope="page"/>
                        </c:forEach>
                        <td><fmt:formatNumber value="${sum}" type="currency" currencySymbol="€"/></td>
                        <td><fmt:formatNumber value="${sum / fn:length(yearEntry.value)}" type="currency" currencySymbol="€"/></td>
                    </tr>
                    <tr class="section-total">
                        <th>Ingresos totales</th>
                        <c:set var="sum" value="0" scope="page"/>
                        <c:forEach var="month" items="${yearEntry.value}">
                            <td><fmt:formatNumber value="${month.totalIncome}" type="currency" currencySymbol="€"/></td>
                            <c:set var="sum" value="${sum + month.totalIncome}" scope="page"/>
                        </c:forEach>
                        <td><fmt:formatNumber value="${sum}" type="currency" currencySymbol="€"/></td>
                        <td><fmt:formatNumber value="${sum / fn:length(yearEntry.value)}" type="currency" currencySymbol="€"/></td>
                    </tr>
                    <tr>
                        <th>Fijos / Obligatorios</th>
                        <c:set var="sum" value="0" scope="page"/>
                        <c:forEach var="month" items="${yearEntry.value}">
                            <td><fmt:formatNumber value="${month.fixedExpenses}" type="currency" currencySymbol="€"/></td>
                            <c:set var="sum" value="${sum + month.fixedExpenses}" scope="page"/>
                        </c:forEach>
                        <td><fmt:formatNumber value="${sum}" type="currency" currencySymbol="€"/></td>
                        <td><fmt:formatNumber value="${sum / fn:length(yearEntry.value)}" type="currency" currencySymbol="€"/></td>
                    </tr>
                    <tr>
                        <th>Variables necesarios</th>
                        <c:set var="sum" value="0" scope="page"/>
                        <c:forEach var="month" items="${yearEntry.value}">
                            <td><fmt:formatNumber value="${month.necessaryExpenses}" type="currency" currencySymbol="€"/></td>
                            <c:set var="sum" value="${sum + month.necessaryExpenses}" scope="page"/>
                        </c:forEach>
                        <td><fmt:formatNumber value="${sum}" type="currency" currencySymbol="€"/></td>
                        <td><fmt:formatNumber value="${sum / fn:length(yearEntry.value)}" type="currency" currencySymbol="€"/></td>
                    </tr>
                    <tr>
                        <th>Caprichos</th>
                        <c:set var="sum" value="0" scope="page"/>
                        <c:forEach var="month" items="${yearEntry.value}">
                            <td><fmt:formatNumber value="${month.discretionaryExpenses}" type="currency" currencySymbol="€"/></td>
                            <c:set var="sum" value="${sum + month.discretionaryExpenses}" scope="page"/>
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
                        <c:set var="sum" value="0" scope="page"/>
                        <c:forEach var="month" items="${yearEntry.value}">
                            <td><fmt:formatNumber value="${month.totalExpenses}" type="currency" currencySymbol="€"/></td>
                            <c:set var="sum" value="${sum + month.totalExpenses}" scope="page"/>
                        </c:forEach>
                        <td><fmt:formatNumber value="${sum}" type="currency" currencySymbol="€"/></td>
                        <td><fmt:formatNumber value="${sum / fn:length(yearEntry.value)}" type="currency" currencySymbol="€"/></td>
                    </tr>
                    <tr class="highlight">
                        <th>Saldo neto</th>
                        <c:set var="sum" value="0" scope="page"/>
                        <c:forEach var="month" items="${yearEntry.value}">
                            <td><fmt:formatNumber value="${month.netBalance}" type="currency" currencySymbol="€"/></td>
                            <c:set var="sum" value="${sum + month.netBalance}" scope="page"/>
                        </c:forEach>
                        <td><fmt:formatNumber value="${sum}" type="currency" currencySymbol="€"/></td>
                        <td><fmt:formatNumber value="${sum / fn:length(yearEntry.value)}" type="currency" currencySymbol="€"/></td>
                    </tr>
                    <tr>
                        <th>Saldo neto acumulado</th>
                        <c:forEach var="month" items="${yearEntry.value}">
                            <td><fmt:formatNumber value="${month.cumulativeNet}" type="currency" currencySymbol="€"/></td>
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
