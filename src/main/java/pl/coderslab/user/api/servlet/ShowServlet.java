package pl.coderslab.user.api.servlet;

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
        UserDao dao = new UserDao();
        String msg = (String) request.getAttribute("msg");
        try {
            int id = Integer.parseInt(request.getParameter("id"));
            request.setAttribute("user", dao.read(id));
        } catch (NumberFormatException e) {
            msg = "Wrong format of user id!";
        } catch (UserDaoException e) {
            msg = e.getMessage();
        }
        request.setAttribute("msg", msg);
        getServletContext().getRequestDispatcher("/users/view.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }
}
