<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!doctype html>
<html lang="en">

<jsp:include page="head.jsp">
    <jsp:param name="title" value="${title}"/>
</jsp:include>

<body>

<c:choose>
    <c:when test="${empty sessionCnt}">
        <c:set var="sessionCnt" value="${1}" scope="session"/>
    </c:when>
    <c:otherwise>
        <c:set var="sessionCnt" value="${sessionCnt+1}" scope="session"/>
    </c:otherwise>
</c:choose>

<nav class="navbar navbar-light bg-light">
    <a class="navbar-brand" href="#">Users list</a>
    <p><c:out value="${sessionCnt}"/></p>
</nav>

<div class="container">

    <table class="table">
        <thead class="thead-dark">
        <tr>
            <th>Id</th>
            <th>Name</th>
        </tr>
        </thead>

        <c:forEach var="user" items="${requestScope.users}">
            <tr>
                <td><a href="${pageContext.request.contextPath}/users?id=${user.id}"><c:out value="${user.id}"/></a>
                </td>
                <td><c:url value="/users" var="userUrl">
                    <c:param name="id" value="${user.id}"/>
                </c:url>
                    <a href="${userUrl}"><c:out value="${user.login}"/></a>
                </td>
            </tr>
        </c:forEach>
    </table>
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
