<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="jakarta.tags.core" prefix="c" %>
<%@ taglib uri="jakarta.tags.fmt" prefix="fmt" %>
<!DOCTYPE html>
<html lang="es">
<head>
    <meta charset="UTF-8">
    <title>Inicio | Personal Economy</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/styles.css">
</head>
<body>
<header class="topbar">
    <h1>Personal Economy</h1>
    <nav>
        <a href="${pageContext.request.contextPath}/users/home">Resumen</a>
        <a href="${pageContext.request.contextPath}/user/cashflow">Cash flow</a>
        <a href="${pageContext.request.contextPath}/user/runway">Runway</a>
        <a href="${pageContext.request.contextPath}/user/expenses/new">Registrar gasto</a>
    </nav>
</header>
<main class="container">
    <section class="card">
        <h2>Resumen rápido</h2>
        <c:choose>
            <c:when test="${not empty latest}">
                <p><strong>Mes:</strong> ${latest.month}</p>
                <p><strong>Saldo neto:</strong>
                    <fmt:formatNumber value="${latest.netBalance}" type="currency" currencySymbol="€"/>
                </p>
                <p><strong>Cash disponible:</strong>
                    <fmt:formatNumber value="${latest.capitalSnapshot != null ? latest.capitalSnapshot.totalAssets : 0}" type="currency" currencySymbol="€"/>
                </p>
                <p><strong>Runway estimado:</strong>
                    <fmt:formatNumber value="${latest.runwayMonths}" maxFractionDigits="1" minFractionDigits="0"/>
                    meses
                </p>
            </c:when>
            <c:otherwise>
                <p>Aún no registraste movimientos. Empieza creando un gasto o ingreso.</p>
            </c:otherwise>
        </c:choose>
    </section>

    <section class="grid">
        <div class="card">
            <h3>Cash flow</h3>
            <p>Consulta el detalle mensual de ingresos, gastos y saldo acumulado.</p>
            <a class="btn" href="${pageContext.request.contextPath}/user/cashflow">Ver cash flow</a>
        </div>
        <div class="card">
            <h3>Runway</h3>
            <p>Analiza tu liquidez y cuánto tiempo puedes mantener tus gastos actuales.</p>
            <a class="btn" href="${pageContext.request.contextPath}/user/runway">Ver runway</a>
        </div>
        <div class="card">
            <h3>Registrar gasto</h3>
            <p>Registra tus gastos para mantener actualizado tu flujo de caja.</p>
            <a class="btn" href="${pageContext.request.contextPath}/user/expenses/new">Registrar gasto</a>
        </div>
        <div class="card">
            <h3>Capital mensual</h3>
            <p>Guarda el efectivo disponible por mes para calcular tu runway.</p>
            <a class="btn" href="${pageContext.request.contextPath}/user/capital">Registrar capital</a>
        </div>
    </section>
</main>
</body>
</html>
