<%--
    Document   : search
    Created on : Feb 8, 2022, 9:45:05 AM
    Author     : Namng
--%>

<%@page contentType="text/html" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>Search</title>
</head>
<body>
<span style="color: red; ">
    Welcome, ${sessionScope.FULLNAME}
</span>
<h1>Search page</h1>
<form action="DispatchController">
    Search value<input type="text" name="txtAddressValue"
                       value="${param.txtAddressValue}"/> <br/>
    <input type="submit" value="Search Student" name="btnAction"/>
</form>
<br/>

<c:set var="searchValue" value="${param.txtAddressValue}"/>
<c:if test="${not empty searchValue}">
    <c:set var="result" value="${requestScope.SEARCH_RESULT}"/>
    <c:if test="${not empty result}">
        <table border="1">
            <thead>
            <tr>
                <th>No.</th>
                <th>ID.</th>
                <th>Full Name.</th>
                <th>Class</th>
                <th>Sex</th>
                <th>Address</th>
                <th>Status</th>
                <th>Delete</th>
            </tr>
            </thead>
            <tbody>
            <c:forEach var="student" items="${result}" varStatus="counter">
                <tr>
                    <td>
                            ${counter.count}
                    </td>
                    <td>
                            ${student.id}
                    </td>
                    <td>
                            ${student.firstName} ${student.middleName} ${student.lastName}
                    </td>
                    <td>
                            ${student.aClass}
                    </td>
                    <td>
                            ${student.sex}
                    </td>
                    <td>
                            ${student.address}
                    </td>
                    <td>
                            ${student.status}
                    </td>
                    <td>
                            <%--                        <c:url var="deleteLink" value="DispatchServlet">--%>
                            <%--                            <c:param name="btnAction" value="delete"/>--%>
                            <%--                            <c:param name="id" value="${student.id}"/>--%>
                            <%--                            <c:param name="lastSearchValue" value="${searchValue}"/>--%>
                            <%--                        </c:url>--%>
                    </td>
                </tr>
            </c:forEach>
            </tbody>
        </table>
    </c:if>
    <c:if test="${empty result}">
        <h2>No result found</h2>
    </c:if>
</c:if>
<c:if test="${empty searchValue}">
    <h2>No search value</h2>
</c:if>

</body>
</html>
