<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html" charset="UTF-8"/>
        <title>Pizzas</title>
    </head>
    <body>
        <table>
            <tbody>
            <tr>
                <th>Id</th>
                <th>Name</th>
                <th>Type</th>
                <th>Price</th>
                <th>Action</th>
            </tr>
            <c:forEach items="${requestScope.pizzas}" var="pizza">
                <tr>
                    <td>${pizza.pizzaId}</td>
                    <td>${pizza.name}</td>
                    <td>${pizza.type}</td>
                    <td>${pizza.price}</td>
                    <td>
                        <form action="${pizza.pizzaId}">
                            <input type="submit" value="Edit">
                        </form>
                    </td>
                </tr>
            </c:forEach>
            </tbody>
        </table>
    </body>
</html>