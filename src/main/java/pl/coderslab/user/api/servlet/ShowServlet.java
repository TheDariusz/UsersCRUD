package pl.coderslab.user.api.servlet;

import pl.coderslab.user.api.UserService;
import pl.coderslab.user.entity.UserDao;
import pl.coderslab.userscrud.exceptions.UserDaoException;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;

@WebServlet(name = "Show", value = "/user/show")
public class ShowServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        final UserService userService = new UserService();
        userService.showUser(request);
        getServletContext().getRequestDispatcher("/users/view.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }
}
