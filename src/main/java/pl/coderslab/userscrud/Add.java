package pl.coderslab.userscrud;

import org.apache.commons.validator.routines.EmailValidator;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;

@WebServlet(name = "Add", value = "/user/add")
public class Add extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        getServletContext().getRequestDispatcher("/users/add.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String username = request.getParameter("username");
        String email = request.getParameter("email");
        String password = request.getParameter("password");
        String error = "";
        UserDao dao = new UserDao();

        if (username.isBlank() || email.isBlank() || password.isBlank()) {
            error = "All fields have to be filled!";
        }

        if (!EmailValidator.getInstance().isValid(email)) {
            error = "Wrong email format!";
        }

        if (dao.emailAlreadyExists(email)) {
            error = "User with email: '" + email + "' already exists in the database!";
        }

        if (!error.isBlank()) {
            request.setAttribute("error", error);
            doGet(request, response);
        }

        dao.create(new User(username, email, password));
        response.sendRedirect("/user/list");
    }
}
