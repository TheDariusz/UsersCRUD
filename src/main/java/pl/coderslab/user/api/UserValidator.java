package pl.coderslab.user.api;

import org.apache.commons.validator.routines.EmailValidator;
import pl.coderslab.user.entity.UserDao;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;

public class UserValidator {

    public boolean validateAddRequest(HttpServletRequest request) {
        final UserDao dao = new UserDao();
        String username = request.getParameter("username");
        String email = request.getParameter("email");
        String password = request.getParameter("password");

        List<String> msg = new ArrayList<>();
        if (username.isBlank() || email.isBlank() || password.isBlank()) {
            msg.add("All fields have to be filled!");
        }

        if (!EmailValidator.getInstance().isValid(email)) {
            msg.add("Wrong email format!");
        }

        if (dao.emailAlreadyExists(email)) {
            msg.add("User with email: '" + email + "' already exists in the database!");
        }

        if (!msg.isEmpty()) {
            request.setAttribute("msg", msg);
            return false;
        }

        return true;
    }
}