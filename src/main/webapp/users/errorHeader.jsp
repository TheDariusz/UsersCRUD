<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<c:if test="${!empty msg}">
    <c:forEach var="msg" items="${msg}">
        <div class="p-3 bg-gray-300">${msg}</div>
    </c:forEach>
</c:if>
