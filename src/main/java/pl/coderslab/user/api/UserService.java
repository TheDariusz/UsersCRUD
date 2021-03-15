package pl.coderslab.user.api;

import pl.coderslab.user.api.request.UserDto;
import pl.coderslab.user.entity.User;
import pl.coderslab.user.entity.UserDao;
import pl.coderslab.user.entity.UserMapper;
import pl.coderslab.userscrud.exceptions.EmailDuplicateException;
import pl.coderslab.userscrud.exceptions.UserDaoException;
import pl.coderslab.utils.DateUtil;
import pl.coderslab.utils.DbUtil;

import javax.servlet.http.HttpServletRequest;
import java.util.List;


public class UserService {

    public void addUser(HttpServletRequest request) {
        UserDto userDto = mapFromRequest(request);
        UserDao dao = new UserDao();
        final UserMapper mapper = new UserMapper();
        final User user = mapper.mapToEntity(userDto);

        try {
            dao.create(user);
        } catch (UserDaoException | EmailDuplicateException e) {
            DbUtil.writeLogToDatabase(request.getHeader("User-Agent"), DateUtil.getCurrentTime(), "error", e.getMessage());
        }
    }

    public void listOfUsers(HttpServletRequest request) {
        UserDao dao = new UserDao();
        List<String> msg = (List<String>) request.getAttribute("msg");
        User[] allUsers = new User[0];
        try {
            allUsers = dao.findALl();
        } catch (UserDaoException e) {
            msg.add("Cannot get list of users!");
            DbUtil.writeLogToDatabase(request.getHeader("User-Agent"), DateUtil.getCurrentTime(), "error", e.getMessage());
        }
        request.setAttribute("msg", msg);
        request.setAttribute("users", allUsers);
    }

    private UserDto mapFromRequest(HttpServletRequest request) {
        String username = request.getParameter("username");
        String email = request.getParameter("email");
        String password = request.getParameter("password");
        return new UserDto(username, email, password);
    }


}