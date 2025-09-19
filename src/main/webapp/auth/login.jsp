<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="jakarta.tags.core" prefix="c" %>
<!DOCTYPE html>
<html lang="es">
<head>
    <meta charset="UTF-8">
    <title>Iniciar sesión</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/styles.css">
</head>
<body>
    <main class="container">
        <h1>Personal Economy</h1>
        <section class="card">
            <h2>Acceder</h2>
            <c:if test="${param.registered == '1'}">
                <div class="alert alert-success">Tu cuenta se creó correctamente. Ahora puedes iniciar sesión.</div>
            </c:if>
            <c:if test="${not empty errors}">
                <ul class="alert alert-error">
                    <c:forEach items="${errors}" var="error">
                        <li>${error}</li>
                    </c:forEach>
                </ul>
            </c:if>
            <form action="${pageContext.request.contextPath}/users/login" method="post" class="form-grid">
                <label for="username">Usuario</label>
                <input type="text" id="username" name="username" required>

                <label for="password">Contraseña</label>
                <input type="password" id="password" name="password" required>

                <button type="submit">Entrar</button>
            </form>
            <p class="text-small">¿Aún no tienes cuenta? 
                <a href="${pageContext.request.contextPath}/users/register">Crear nuevo usuario</a>
            </p>
        </section>
    </main>
</body>
</html>
