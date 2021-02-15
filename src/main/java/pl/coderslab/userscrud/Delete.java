package pl.coderslab.userscrud;

import pl.coderslab.userscrud.exceptions.UserDaoException;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;

@WebServlet(name = "Delete", value = "/user/delete")
public class Delete extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        UserDao dao = new UserDao();
        String msg = "";
        try {
            int id = Integer.parseInt(request.getParameter("id"));
            dao.delete(id);
            msg = "User with id:" + id + " was deleted";
        } catch (NumberFormatException e) {
            msg = "Wrong format of user id!";
        } catch (UserDaoException e) {
            msg = e.getMessage();
        }

        request.setAttribute("msg", msg);
        getServletContext().getRequestDispatcher("/user/list").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }
}
