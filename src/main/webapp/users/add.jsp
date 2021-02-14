<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@include file="header.jsp" %>

<!-- Begin Page Content -->
<div class="container-fluid">

    <!-- Page Heading -->
    <div class="d-sm-flex align-items-center justify-content-between mb-4">
        <h1 class="h3 mb-0 text-gray-800">UsersCRUD</h1>
        <a href="<c:url value="/user/list"/>" class="d-none d-sm-inline-block btn btn-sm btn-primary shadow-sm">
            <i class="fas fa-download fa-sm text-white-50"></i>Users list</a>
    </div>
    <div class="card-header py-3" style="padding: 0">
        <h6 class="m-0 font-weight-bold text-primary">Add new user:</h6>
    </div>
    <form class="user" method="post" style="padding: 20px 0px">
        <div class="form-group">
            <label>User name:
                <input type="text" class="form-control bg-light border-1 small" style="width: 200%" name="username">
            </label>
        </div>
        <div class="form-group">
            <label>User email:
                <input type="text" class="form-control bg-light border-1 small" style="width: 200%" name="email">
            </label>
        </div>
        <div class="form-group">
            <label>Password:
                <input type="password" class="form-control bg-light border-1 small" style="width: 200%" name="password">
            </label>
        </div>
        <div class="input-group-append">
            <input class="btn btn-primary" type="submit" value="create new user">
        </div>
    </form>
    <c:if test="${!empty error}">
        <div class="p-3 bg-gray-300">${error}</div>
    </c:if>
</div>
<!-- /.container-fluid -->

<%@include file="footer.jsp" %>