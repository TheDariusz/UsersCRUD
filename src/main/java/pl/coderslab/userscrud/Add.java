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
        String action = "add";
        request.setAttribute("action", action);
        getServletContext().getRequestDispatcher("/users/add.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String username = request.getParameter("username");
        String email = request.getParameter("email");
        String password = request.getParameter("password");
        String msg = "";
        UserDao dao = new UserDao();

        if (username.isBlank() || email.isBlank() || password.isBlank()) {
            msg = "All fields have to be filled!";
        }

        if (!EmailValidator.getInstance().isValid(email)) {
            msg = "Wrong email format!";
        }

        if (dao.emailAlreadyExists(email)) {
            msg = "User with email: '" + email + "' already exists in the database!";
        }

        if (!msg.isBlank()) {
            request.setAttribute("msg", msg);
            doGet(request, response);
        }

        dao.create(new User(username, email, password)); //@todo add exception handling
        response.sendRedirect("/user/list");
    }
}
