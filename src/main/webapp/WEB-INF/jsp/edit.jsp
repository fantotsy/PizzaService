<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html" charset="UTF-8"/>
    <title>Pizzas</title>
</head>
    <body>
        <form action="add-new" method="post">
            Name: <input type="text" name = "name"/>
            Type: <input type="text" name = "type"/>
            Price: <input type="text" name = "price"/>
            <input type="submit" value="Add"/>
        </form>
    </body>
</html>