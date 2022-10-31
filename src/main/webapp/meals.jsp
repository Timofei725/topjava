<%--
  Created by IntelliJ IDEA.
  User: Тимофей
  Date: 15.10.2022
  Time: 21:37
  To change this template use File | Settings | File Templates.
--%>


<%@ page language="java" contentType="text/html; charset=EUC-KR" pageEncoding="EUC-KR"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">

<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=EUC-KR">
    <title>Show All Meals</title>
</head>
<body>
<table border=1>
    <thead>
    <tr>
        <th>Date</th>
        <th>Description</th>
        <th>Calories</th>
        <th colspan=2>Action</th>
    </tr>
    </thead>
    <tbody>
    <jsp:useBean id="meals" scope="request" type="java.util.List"/>
    <c:forEach items="${meals}" var="meal">
        <tr >
            <c:if test="${meal.excess==true}">
            <td style="color: red"><fmt:parseDate  value="${ meal.dateTime }" pattern="yyyy-MM-dd'T'HH:mm" var="parsedDateTime" type="both" />
                <fmt:formatDate  pattern="dd.MM.yyyy HH:mm" value="${ parsedDateTime }" /></td>
            <td style="color: red"><c:out value="${meal.description}" /></td>
            <td style="color: red" ><c:out value="${meal.calories}" /></td>
            </c:if>
            <c:if test="${meal.excess==false}">
                <td style="color: green"><fmt:parseDate  value="${ meal.dateTime }" pattern="yyyy-MM-dd'T'HH:mm" var="parsedDateTime" type="both" />
                    <fmt:formatDate  pattern="dd.MM.yyyy HH:mm" value="${ parsedDateTime }" /></td>
                <td style="color: green"><c:out value="${meal.description}" /></td>
                <td style="color: green" ><c:out value="${meal.calories}" /></td>
            </c:if>
            <td><a href="${pageContext.request.contextPath}/meals?action=edit&mealId=<c:out value="${meal.id}"/>">Update</a></td>
            <td><a href="${pageContext.request.contextPath}/meals?action=delete&mealId=<c:out value="${meal.id}"/>">Delete</a></td>
        </tr>
    </c:forEach>
    </tbody>
</table>
<p><a href="${pageContext.request.contextPath}/meals?action=insert">Add Meals</a></p>
</body>
</html>
