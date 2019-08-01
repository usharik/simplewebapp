<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!doctype html>
<html lang="en">

<jsp:include page="head.jsp">
    <jsp:param name="title" value="${title}"/>
</jsp:include>

<body>
    <nav class="navbar navbar-light bg-light">
        <a class="navbar-brand" href="#">User edit form</a>
    </nav>

    <div class="container">
        <c:url value="/users" var="userPostUrl"/>
        <form action="${userPostUrl}">
            <input type="hidden" name="id" value="${user.id}">
            <div class="form-group">
                <label for="exampleInputUsername">User name</label>
                <input type="text" class="form-control" id="exampleInputUsername" placeholder="Enter username"
                       name="username" value="${user.login}">
            </div>
            <div class="form-group">
                <label for="exampleInputPassword">Password</label>
                <input type="password" class="form-control" id="exampleInputPassword" placeholder="Password"
                       name="password" value="${user.password}">
            </div>
            <button type="submit" formmethod="post" class="btn btn-primary">Submit</button>
        </form>
    </div>

<!-- Optional JavaScript -->
<!-- jQuery first, then Popper.js, then Bootstrap JS -->
<script src="https://code.jquery.com/jquery-3.3.1.slim.min.js"
        integrity="sha384-q8i/X+965DzO0rT7abK41JStQIAqVgRVzpbzo5smXKp4YfRvH+8abtTE1Pi6jizo"
        crossorigin="anonymous"></script>
<script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.14.7/umd/popper.min.js"
        integrity="sha384-UO2eT0CpHqdSJQ6hJty5KVphtPhzWj9WO1clHTMGa3JDZwrnQq4sF86dIHNDz0W1"
        crossorigin="anonymous"></script>
<script src="https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/js/bootstrap.min.js"
        integrity="sha384-JjSmVgyd0p3pXB1rRibZUAYoIIy6OrQ6VrjIEaFf/nJGzIxFDsf4x0xIM+B07jRM"
        crossorigin="anonymous"></script>
</body>
</html>
