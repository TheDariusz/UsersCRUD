<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@include file="header.jsp" %>

<!-- Begin Page Content -->
<div class="container-fluid">

    <!-- Page Heading -->
    <div class="d-sm-flex align-items-center justify-content-between mb-4">
        <h1 class="h3 mb-0 text-gray-800">UsersCRUD</h1>
        <a href="<c:url value="/user/add"/>" class="d-none d-sm-inline-block btn btn-sm btn-primary shadow-sm">
            <i class="fas fa-download fa-sm text-white-50"></i>Add new user</a>
    </div>
    <c:if test="${!empty msg}">
        <div class="p-3 bg-gray-300">${msg}</div>
    </c:if>
    <div class="card-header py-3" style="padding: 0">
        <h6 class="m-0 font-weight-bold text-primary">List of all users in database:</h6>
    </div>
    <div class="row">
        <div class="col-sm-12">
            <table class="table table-bordered dataTable" id="dataTable" width="100%" cellspacing="0" role="grid"
                   aria-describedby="dataTable_info" style="width: 100%;">
                <thead>
                <tr role="row">
                    <th class="sorting_asc" tabindex="0" aria-controls="dataTable" rowspan="1" colspan="1"
                        aria-sort="ascending" aria-label="Name: activate to sort column descending"
                        style="width: 5%;">ID
                    </th>
                    <th class="sorting" tabindex="0" aria-controls="dataTable" rowspan="1" colspan="1"
                        aria-label="Position: activate to sort column ascending" style="width: 120px;">Email address
                    </th>
                    <th class="sorting" tabindex="0" aria-controls="dataTable" rowspan="1" colspan="1"
                        aria-label="Office: activate to sort column ascending" style="width: 57px;">Username
                    </th>
                    <th class="sorting" tabindex="0" aria-controls="dataTable" rowspan="1" colspan="1"
                        aria-label="Age: activate to sort column ascending" style="width: 31px;">Action
                    </th>
                </tr>
                </thead>
                <tbody>
                <c:forEach var="user" items="${users}" varStatus="status">
                <tr role="row" class="odd">
                    <td class="sorting_1">${status.count}</td>
                    <td>${user.email}</td>
                    <td>${user.userName}</td>
                    <td>
                        <a href="<c:url value="/user/edit?id=${user.id}"/>">edit</a>
                        <a href="<c:url value="/user/show?id=${user.id}"/>">show</a>
                        <a href="<c:url value="/user/delete?id=${user.id}"/>" onclick="if (! confirm('Are you sure?')) { return false; }">delete</a>
                    </td>
                </tr>
                </c:forEach>
                </tbody>
            </table>
        </div>
    </div>
</div>
<!-- /.container-fluid -->

<%@include file="footer.jsp" %>
