package pl.coderslab.userscrud;

import org.apache.commons.validator.routines.EmailValidator;
import pl.coderslab.userscrud.exceptions.UserDaoException;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;

@WebServlet(name = "Edit", value = "/user/edit")
public class Edit extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        UserDao dao = new UserDao();
        String action = "edit";
        String msg = "";
        try {
            int id = Integer.parseInt(request.getParameter("id"));
            request.setAttribute("user", dao.read(id));
        } catch (NumberFormatException e) {
            msg = "Wrong format of user id!";
        } catch (UserDaoException e) {
            msg = e.getMessage();
        }

        request.setAttribute("msg", msg);
        request.setAttribute("action", action);
        getServletContext().getRequestDispatcher("/users/add.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String msg = "";
        String newUsername = request.getParameter("username");
        String newEmail = request.getParameter("email");
        String newPassword = request.getParameter("password");
        UserDao dao = new UserDao();
        User user;

        try {
            long id = Integer.parseInt(request.getParameter("id"));
            user = dao.read(id);
        } catch (NumberFormatException e) {
            msg="Wrong format of user id!";
        }

        if (newUsername.isBlank() || newEmail.isBlank() || newPassword.isBlank()) {
            msg = "All fields have to be filled!";
        }

        if (!EmailValidator.getInstance().isValid(newEmail)) {
            msg = "Wrong email format!";
        }

        if (dao.emailAlreadyExists(newEmail)) {
            msg = "User with email: '" + newEmail + "' already exists in the database!";
        }

        if (!msg.isBlank()) {
            request.setAttribute("msg", msg);
            getServletContext().getRequestDispatcher("/users/add.jsp").forward(request, response);
        } else {
            user.setUserName(newUsername);
            user.setEmail(newEmail);
            user.setPassword(newPassword);
            dao.update(user);
        response.sendRedirect("/user/list");}
    }
}

