<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="jakarta.tags.core" prefix="c" %>
<%@ taglib uri="jakarta.tags.fmt" prefix="fmt" %>
<!DOCTYPE html>
<html lang="es">
<head>
    <meta charset="UTF-8">
    <title>Registrar gasto | Personal Economy</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/styles.css">
</head>
<body>
<header class="topbar">
    <h1>Registrar gasto</h1>
    <nav>
        <a href="${pageContext.request.contextPath}/users/home">Volver al inicio</a>
        <a href="${pageContext.request.contextPath}/users/cashflow">Cash flow</a>
    </nav>
</header>
<main class="container">
    <c:if test="${param.success eq '1'}">
        <p class="alert alert-success">Gasto registrado correctamente.</p>
    </c:if>
    <c:if test="${not empty errors}">
        <ul class="alert alert-error">
            <c:forEach items="${errors}" var="error">
                <li>${error}</li>
            </c:forEach>
        </ul>
    </c:if>
    <form action="${pageContext.request.contextPath}/users/expenses/new" method="post" class="form-grid">
        <label for="name">Nombre</label>
        <input type="text" id="name" name="name" value="${expense.name}" required>

        <label for="description">Descripción</label>
        <textarea id="description" name="description">${expense.description}</textarea>

        <label for="amount">Importe (€)</label>
        <input type="number" step="0.01" id="amount" name="amount" value="${expense.amount}" required>

        <label for="type">Tipo</label>
        <select id="type" name="type" required>
            <option value="">Selecciona un tipo</option>
                <c:forEach var="type" items="${types}">
                    <option value="${type}" <c:if test="${expense.type == type}">selected</c:if>>${type}</option>
                </c:forEach>
        </select>

        <label for="date">Fecha</label>
        <input type="date" id="date" name="date" value="${expense.date}">

        <button type="submit">Guardar</button>
    </form>
</main>
</body>
</html>
