<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="jakarta.tags.core" prefix="c" %>
<%@ taglib uri="jakarta.tags.functions" prefix="fn" %>
<!DOCTYPE html>
<html lang="es">
<head>
    <meta charset="UTF-8">
    <title>Crear cuenta</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/styles.css">
</head>
<body>
    <main class="container">
        <h1>Personal Economy</h1>
        <section class="card">
            <h2>Nuevo usuario</h2>
            <p>Completa el formulario para crear tu cuenta.</p>

            <c:if test="${not empty errors}">
                <ul class="alert alert-error">
                    <c:forEach items="${errors}" var="error">
                        <li>${error}</li>
                    </c:forEach>
                </ul>
            </c:if>

            <form action="${pageContext.request.contextPath}/users/register" method="post" class="form-grid">
                <label for="username">Usuario</label>
                <input type="text" id="username" name="username" value="${fn:escapeXml(username)}" required>

                <label for="email">Correo electrónico</label>
                <input type="email" id="email" name="email" value="${fn:escapeXml(email)}" required>

                <label for="password">Contraseña</label>
                <input type="password" id="password" name="password" required>

                <label for="passwordConfirm">Confirmar contraseña</label>
                <input type="password" id="passwordConfirm" name="passwordConfirm" required>

                <button type="submit">Crear cuenta</button>
            </form>

            <p class="text-small">¿Ya tienes cuenta? <a href="${pageContext.request.contextPath}/users/login">Inicia sesión</a></p>
        </section>
    </main>
</body>
</html>
