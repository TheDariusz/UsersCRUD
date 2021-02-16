package pl.coderslab.userscrud;

import org.apache.commons.validator.routines.EmailValidator;
import pl.coderslab.userscrud.exceptions.EmailDuplicateException;
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
        long id=0;

        try {
            id = Integer.parseInt(request.getParameter("id"));
        } catch (NumberFormatException e) {
            msg="Wrong format of user id!";
        }

        if (newUsername.isBlank() || newEmail.isBlank() || newPassword.isBlank()) {
            msg = "All fields have to be filled!";
        }

        if (!EmailValidator.getInstance().isValid(newEmail)) {
            msg = "Wrong email format!";
        }

        if (dao.emailAlreadyExists(id, newEmail)) {
            msg = "Email already exists!";
        }

        if (!msg.isBlank()) {
            request.setAttribute("msg", msg);
            doGet(request, response);
        }

        User user = dao.read(id);
        user.setUserName(newUsername);
        user.setEmail(newEmail);
        user.setPassword(newPassword);
        try {
            dao.update(user);
        } catch (UserDaoException e) {
            msg = "Update user process failed!";
            request.setAttribute("msg", msg);
        }
        response.sendRedirect("/user/list");
    }
}

