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
        <h6 class="m-0 font-weight-bold text-primary">Details of selected user:</h6>
    </div>
    <div class="row">
        <div class="col-sm-12">
            <table class="table table-bordered dataTable" id="dataTable" width="100%" cellspacing="0" role="grid"
                   aria-describedby="dataTable_info" style="width: 100%;">
                <tbody>
                    <tr role="row" class="odd">
                        <td class="sorting_1"><strong>ID</strong></td>
                        <td>${user.id}</td>
                    </tr>
                    <tr role="row" class="odd">
                        <td><strong>User name</strong></td>
                        <td>${user.userName}</td>
                    </tr>
                    <tr role="row" class="odd">
                        <td><strong>User email</strong></td>
                        <td>${user.email}</td>
                    </tr>
                </tbody>
            </table>
        </div>
    </div>
<%@include file="errorHeader.jsp"%>
</div>
<!-- /.container-fluid -->

<%@include file="footer.jsp" %>