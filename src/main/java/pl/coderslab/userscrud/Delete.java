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
        String error = "";
        try {
            int id = Integer.parseInt(request.getParameter("id"));
            try {
                dao.delete(id);
            } catch (UserDaoException e) {
                error = e.getMessage();
            }
        } catch (NumberFormatException e) {
            error="Wrong format of user id!";
        }
        request.setAttribute("error", error); //@todo error passing not working!
        response.sendRedirect("/user/list");
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }
}
