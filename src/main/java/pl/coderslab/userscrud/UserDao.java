package pl.coderslab.userscrud;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.mindrot.jbcrypt.BCrypt;
import pl.coderslab.userscrud.exceptions.EmailDuplicateException;
import pl.coderslab.userscrud.exceptions.UserDaoException;
import pl.coderslab.utils.DbUtil;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Arrays;

public class UserDao {
  private static final Logger logger = LoggerFactory.getLogger(UserDao.class);

  public User create(User user) {
    if (user == null) {
      return null;
    }

    if (emailAlreadyExists(user)) {
      throw new EmailDuplicateException("User with email " + user.getEmail() + " already exists!");
    }

    String createUserQuery = "INSERT INTO users(username, email, password) VALUES (?, ?, ?)";
    try (Connection conn = DbUtil.getConnection();
         PreparedStatement stmt =
            conn.prepareStatement(createUserQuery, Statement.RETURN_GENERATED_KEYS)) {
      setCreateStatement(user, stmt);
      stmt.executeUpdate();

      ResultSet rs = stmt.getGeneratedKeys();
      if (rs.next()) {
        user.setId(rs.getInt(1));
      }
      logger.info("User {id: {}, email: {}} created in DB!", user.getId(), user.getEmail());
      rs.close();
      return user;
    } catch (SQLException e) {
      throw new UserDaoException("Create user query failed!", e);
    }
  }

  public void update(User user) {
    if (emailAlreadyExists(user)) {
      throw new EmailDuplicateException("User with email " + user.getEmail() + " already exists!");
    }

    String updateUserQuery = "UPDATE users SET username=?, email=?, password=? WHERE id=?";
    try (Connection conn = DbUtil.getConnection();
        PreparedStatement stmt = conn.prepareStatement(updateUserQuery)) {
      setUpdateStatement(user, stmt);
      stmt.executeUpdate();
      logger.info("User {id: {}, email: {}} updated in DB!", user.getId(), user.getEmail());
    } catch (SQLException e) {
      throw new UserDaoException("Update user query failed!", e);
    }
  }

  public User read(long userId) {
    if (userId <= 0) {
      logger.info("User id should be greater than 0!");
      return null;
    }

    String selectUserIdQuery = "SELECT * FROM users WHERE id=?";
    try (Connection conn = DbUtil.getConnection();
        PreparedStatement stmt = conn.prepareStatement(selectUserIdQuery)) {
      stmt.setLong(1, userId);
      ResultSet rs = stmt.executeQuery();
      if (rs.next()) {
        return getUser(rs);
      } else {
        rs.close();
        throw new UserDaoException("User with id:" + userId + " does not exists in DB!");
      }
    } catch (SQLException e) {
      throw new UserDaoException("User select query failed!", e);
    }
  }

  public User read(String userEmail) {
    String selectUserEmailQuery = "SELECT * FROM users WHERE email=?";
    try (Connection conn = DbUtil.getConnection();
        PreparedStatement stmt = conn.prepareStatement(selectUserEmailQuery)) {
      stmt.setString(1, userEmail);
      ResultSet rs = stmt.executeQuery();
      if (rs.next()) {
        return getUser(rs);
      } else {
        logger.info("User with email:{} does not exists in DB!", userEmail);
      }
      rs.close();
    } catch (SQLException e) {
      throw new UserDaoException("User select query failed!", e);
    }
    return null;
  }

  public void delete(int userId) {
    if (userId <= 0) {
      logger.info("User id should be greater than 0!");
      return;
    }

    String deleteUserIdQuery = "DELETE FROM users WHERE id=?;";
    try (Connection conn = DbUtil.getConnection();
        PreparedStatement stmt = conn.prepareStatement(deleteUserIdQuery)) {
      stmt.setInt(1, userId);
      boolean isOneRow = stmt.executeUpdate() == 1;
      if (isOneRow) {
        logger.info("User with id:{} was deleted in DB!", userId);
      } else {
        throw new UserDaoException("User does not exists!");
      }
    } catch (SQLException e) {
      throw new UserDaoException("Delete user query failed!", e);
    }
  }

  public User[] findALl() {
    User[] users = new User[0];

    String selectAllQuery = "SELECT * FROM users";
    try (Connection conn = DbUtil.getConnection();
        PreparedStatement stmt = conn.prepareStatement(selectAllQuery)) {

      ResultSet rs = stmt.executeQuery();
      while (rs.next()) {
        User user = getUser(rs);
        users = addToArray(user, users);
      }
      rs.close();
    } catch (SQLException e) {
      logger.error("Select all users query failed!", e);
    }
    return users;
  }

  public boolean emailAlreadyExists(User user) {
    String checkEmailQuery = "SELECT users.id FROM users WHERE email=? and id<>?";
    try (Connection conn = DbUtil.getConnection();
        PreparedStatement stmt = conn.prepareStatement(checkEmailQuery)) {
      setCheckEmailStatement(user.getId(), user.getEmail(), stmt);
      try (ResultSet rs = stmt.executeQuery()) {
        return rs.next();
      }
    } catch (SQLException e) {
      throw new UserDaoException("Check email query failed!");
    }
  }

  public boolean emailAlreadyExists(String email) {
    String checkEmailQuery = "SELECT users.id FROM users WHERE email=?";
    try (Connection conn = DbUtil.getConnection();
         PreparedStatement stmt = conn.prepareStatement(checkEmailQuery)) {
      stmt.setString(1, email);
      try (ResultSet rs = stmt.executeQuery()) {
        return rs.next();
      }
    } catch (SQLException e) {
      throw new UserDaoException("Check email query failed!");
    }
  }

  public boolean emailAlreadyExists(long id, String email) {
    String checkEmailQuery = "SELECT users.id FROM users WHERE email=? and id<>?";
    try (Connection conn = DbUtil.getConnection();
         PreparedStatement stmt = conn.prepareStatement(checkEmailQuery)) {
      stmt.setString(1, email);
      stmt.setLong(2, id);
      try (ResultSet rs = stmt.executeQuery()) {
        return rs.next();
      }
    } catch (SQLException e) {
      throw new UserDaoException("Check email query failed!");
    }
  }

  private String hashPassword(String password) {
    return BCrypt.hashpw(password, BCrypt.gensalt());
  }

  private User[] addToArray(User u, User[] users) {
    User[] tmpUsers = Arrays.copyOf(users, users.length + 1);
    tmpUsers[users.length] = u;
    return tmpUsers;
  }

  private User getUser(ResultSet rs) throws SQLException {
    User user =
        new User(
            rs.getString(UserTable.USERNAME_COL),
            rs.getString(UserTable.EMAIL_COL),
            rs.getString(UserTable.PASSWORD_COL));
    user.setId(rs.getLong(UserTable.ID_COL));
    return user;
  }

  private void setCheckEmailStatement(long id, String email, PreparedStatement stmt)
      throws SQLException {
    stmt.setString(1, email);
    stmt.setLong(2, id);
  }

  private void setUpdateStatement(User user, PreparedStatement stmt) throws SQLException {
    stmt.setString(1, user.getUserName());
    stmt.setString(2, user.getEmail());
    stmt.setString(3, hashPassword(user.getPassword()));
    stmt.setLong(4, user.getId());
  }

  private void setCreateStatement(User user, PreparedStatement stmt) throws SQLException {
    stmt.setString(1, user.getUserName());
    stmt.setString(2, user.getEmail());
    stmt.setString(3, hashPassword(user.getPassword()));
  }

  static class UserTable {
    private UserTable() {
      throw new IllegalStateException("Inner class!");
    }

    public static final String ID_COL = "id";
    public static final String USERNAME_COL = "username";
    public static final String EMAIL_COL = "email";
    public static final String PASSWORD_COL = "password";
  }
}
