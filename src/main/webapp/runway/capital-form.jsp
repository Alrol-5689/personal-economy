<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="jakarta.tags.core" prefix="c" %>
<!DOCTYPE html>
<html lang="es">
<head>
    <meta charset="UTF-8">
    <title>Registrar capital | Personal Economy</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/styles.css">
</head>
<body>
<header class="topbar">
    <h1>Capital mensual</h1>
    <nav>
        <a href="${pageContext.request.contextPath}/users/home">Inicio</a>
        <a href="${pageContext.request.contextPath}/users/runway">Runway</a>
    </nav>
</header>
<main class="container">
    <c:if test="${param.success eq '1'}">
        <p class="alert alert-success">Capital guardado correctamente.</p>
    </c:if>
    <c:if test="${not empty errors}">
        <ul class="alert alert-error">
            <c:forEach items="${errors}" var="error">
                <li>${error}</li>
            </c:forEach>
        </ul>
    </c:if>
    <form action="${pageContext.request.contextPath}/users/capital" method="post" class="form-grid">
        <label for="month">Mes</label>
        <input type="month" id="month" name="month" value="${capital.month}">

        <label for="savingsAccount">Cuenta de ahorro (€)</label>
        <input type="number" step="0.01" id="savingsAccount" name="savingsAccount" value="${capital.savingsAccount}" required>

        <label for="piggyBank">Hucha (€)</label>
        <input type="number" step="0.01" id="piggyBank" name="piggyBank" value="${capital.piggyBank}" required>

        <label for="checkingAccount">Cuenta corriente (€)</label>
        <input type="number" step="0.01" id="checkingAccount" name="checkingAccount" value="${capital.checkingAccount}" required>

        <label for="cash">Cash (€)</label>
        <input type="number" step="0.01" id="cash" name="cash" value="${capital.cash}" required>

        <button type="submit">Guardar</button>
    </form>
</main>
</body>
</html>
