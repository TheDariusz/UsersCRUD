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
import java.util.ArrayList;
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

    public void setEditedUser(HttpServletRequest request) {
        UserDao dao = new UserDao();
        request.setAttribute("msgAction", "Edit user:");
        request.setAttribute("btnAction", "save changes");
        List<String> msg = (List<String>) request.getAttribute("msg");
        try {
            int id = Integer.parseInt(request.getParameter("id"));
            request.setAttribute("user", dao.read(id));
        } catch (UserDaoException e) {
            msg.add("Cannot get user from database!");
            DbUtil.writeLogToDatabase(request.getHeader("User-Agent"), DateUtil.getCurrentTime(),
                    "error", e.getMessage());
        }

        request.setAttribute("msg", msg);
    }

    public void editUser(HttpServletRequest request) {
        UserDto userDto = mapFromRequest(request);
        UserDao dao = new UserDao();
        List<String> msg = new ArrayList<>();
        final UserMapper mapper = new UserMapper();
        final User user = mapper.mapToEntity(userDto);

        try {
            dao.update(user);
        } catch (UserDaoException e) {
            msg.add("Update user process failed!");
            request.setAttribute("msg", msg);
            DbUtil.writeLogToDatabase(request.getHeader("User-Agent"), DateUtil.getCurrentTime(),
                    "error", e.getMessage());
        }
    }

    public void listOfUsers(HttpServletRequest request) {
        UserDao dao = new UserDao();
        List<String> msg = (List<String>) request.getAttribute("msg");
        if(msg==null) {
            msg = new ArrayList<>();
        }
        List<User> allUsers = new ArrayList<>();
        try {
            allUsers = dao.findAll();
        } catch (UserDaoException e) {
            msg.add("Cannot get list of users!");
            DbUtil.writeLogToDatabase(request.getHeader("User-Agent"), DateUtil.getCurrentTime(), "error", e.getMessage());
        }
        request.setAttribute("msg", msg);
        request.setAttribute("users", allUsers);
    }

    public void showUser(HttpServletRequest request) {
        final UserDao dao = new UserDao();
        List<String> msg = new ArrayList<>();
        try {
            final int id = Integer.parseInt(request.getParameter("id"));
            request.setAttribute("user", dao.read(id));
        } catch (NumberFormatException e) {
            msg.add("Wrong format of user id!");
        } catch (UserDaoException e) {
            msg.add(e.getMessage());
            DbUtil.writeLogToDatabase(request.getHeader("User-Agent"),
                    DateUtil.getCurrentTime(), "error", e.getMessage());
        }
        request.setAttribute("msg", msg);
    }

    public void deleteUser(HttpServletRequest request) {
        final UserDao dao = new UserDao();
        List<String> msg = new ArrayList<>();
        try {
            final int id = Integer.parseInt(request.getParameter("id"));
            dao.delete(id);
            msg.add("User with id:" + id + " was deleted");
        } catch (NumberFormatException e) {
            msg.add("Wrong format of user id!");
        } catch (UserDaoException e) {
            msg.add(e.getMessage());
        }
        request.setAttribute("msg", msg);
    }

    private UserDto mapFromRequest(HttpServletRequest request) {
        final String username = request.getParameter("username");
        final String email = request.getParameter("email");
        final String password = request.getParameter("password");
        final String userId = request.getParameter("userid");
        final String id = userId.isEmpty() ? "0" : userId;
        return new UserDto(id, username, email, password);
    }


}