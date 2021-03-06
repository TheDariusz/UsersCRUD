package pl.coderslab.user.api.servlet;

import pl.coderslab.user.api.UserService;
import pl.coderslab.user.api.UserValidator;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;

@WebServlet(name = "Edit", value = "/user/edit")
public class EditServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        final UserService userService = new UserService();
        final UserValidator userValidator = new UserValidator();

        try {
            userValidator.validateUserId(request);
        } catch (Exception e) {
            response.sendRedirect("/user/list");
            return;
        }

        userService.setEditedUser(request);
        getServletContext().getRequestDispatcher("/users/add.jsp").forward(request, response);

    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        final UserValidator userValidator = new UserValidator();
        final UserService userService = new UserService();

        try {
            userValidator.validateUserId(request);
        } catch (Exception e) {
            response.sendRedirect("/user/edit");
            return;
        }

        final boolean isValid = userValidator.validateFormRequest(request);
        final boolean isMailUnique = userValidator.validateOtherEmails(request);
        if (!isValid || !isMailUnique) {
            doGet(request, response);
            return;
        }

        userService.editUser(request);
        response.sendRedirect("/user/list");
    }
}

