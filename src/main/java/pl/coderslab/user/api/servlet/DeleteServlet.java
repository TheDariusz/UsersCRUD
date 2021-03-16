package pl.coderslab.user.api.servlet;

import pl.coderslab.user.api.UserService;
import pl.coderslab.user.entity.UserDao;
import pl.coderslab.userscrud.exceptions.UserDaoException;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@WebServlet(name = "Delete", value = "/user/delete")
public class DeleteServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        final UserService userService = new UserService();
        userService.deleteUser(request);
        getServletContext().getRequestDispatcher("/user/list").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }
}
