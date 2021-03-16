package pl.coderslab.user.api;

import org.apache.commons.validator.routines.EmailValidator;
import pl.coderslab.user.entity.UserDao;
import pl.coderslab.utils.DateUtil;
import pl.coderslab.utils.DbUtil;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;

public class UserValidator {

    public boolean validateFormRequest(HttpServletRequest request) {
        final String username = request.getParameter("username");
        final String email = request.getParameter("email");
        final String password = request.getParameter("password");

        List<String> msg = new ArrayList<>();
        if (username.isBlank() || email.isBlank() || password.isBlank()) {
            msg.add("All fields have to be filled!");
        }

        if (!EmailValidator.getInstance().isValid(email)) {
            msg.add("Wrong email format!");
        }

        if (!msg.isEmpty()) {
            request.setAttribute("msg", msg);
            return false;
        }

        return true;
    }

    public long validateUserId(HttpServletRequest request) {
        long id=0;
        try {
            id = Long.parseLong(request.getParameter("id"));
            if (id<0) {
                return 0;
            }
        } catch (NumberFormatException e) {
            DbUtil.writeLogToDatabase(request.getHeader("User-Agent"), DateUtil.getCurrentTime(),
                    "error", e.getMessage());
        }
        return id;
    }

    public boolean validateUniqueEmail(HttpServletRequest request) {
        final UserDao dao = new UserDao();
        final String email = request.getParameter("email");

        if (dao.emailAlreadyExists(email)) {
            List<String> msg = new ArrayList<>();
            msg.add("User with email: '" + email + "' already exists in the database!");
            request.setAttribute("msg", msg);
            return false;
        }
        return true;
    }

    public boolean validateOtherEmails(HttpServletRequest request) {
        final UserDao dao = new UserDao();
        final String email = request.getParameter("email");
        long id = 0;
        try {
            id = Long.parseLong(request.getParameter("userid"));
        } catch (NumberFormatException e) {
            e.printStackTrace();
        }

        if (dao.emailAlreadyExists(id, email)) {
            List<String> msg = new ArrayList<>();
            msg.add("User with email: '" + email + "' already exists in the database!");
            request.setAttribute("msg", msg);
            return false;
        }
        return true;
    }

}