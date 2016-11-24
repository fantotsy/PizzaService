<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE HTML>
<html>
    <head>
        <title>Title</title>
    </head>
    <body>
    Hello!
    <p>
    <table border="1">

        <c:forEach var="pizza" items="${pizzas}">
            <tr>
                <td>
                        ${pizza}
                </td>
                <td>
                    <form action="edit/pizza/${pizza.pizzaId}" method="post">
                        <input type="submit">
                        <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
                    </form>
                </td>
            </tr>
        </c:forEach>
    </table>
    </p>
    ${message}
    <br>
    <a href="create">create</a>

    <p>${pageContext.request.remoteUser}</p>

    <c:url var="logoutUrl" value="/logout"/>
    <form class="form-inline" action="${logoutUrl}" method="post">
        <input type="submit" value="Log out" />
        <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
    </form>

    </body>
</html>