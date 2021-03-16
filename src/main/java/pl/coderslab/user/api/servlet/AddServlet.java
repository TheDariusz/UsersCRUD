package pl.coderslab.user.api.servlet;

import pl.coderslab.user.api.UserService;
import pl.coderslab.user.api.UserValidator;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(name = "Add", value = "/user/add")
public class AddServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setAttribute("msgAction", "Add new user:");
        request.setAttribute("btnAction", "create new user");

        getServletContext().getRequestDispatcher("/users/add.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        final UserValidator userValidator = new UserValidator();
        final boolean isFormValid = userValidator.validateFormRequest(request);
        final boolean isMailUnique = userValidator.validateUniqueEmail(request);
        if (!isFormValid || !isMailUnique) {
            doGet(request, response);
            return;
        }

        final UserService userService = new UserService();
        userService.addUser(request);
        response.sendRedirect("/user/list");
    }
}
