package pl.coderslab.user.api.request;

public class UserDto {
    private final String id;
    private final String userName;
    private final String email;
    private final String password;


    public UserDto(String userName, String email, String password) {
        this.id = null;
        this.userName = userName;
        this.email = email;
        this.password = password;
    }

    public UserDto(String id, String userName, String email, String password) {
        this.id = id;
        this.userName = userName;
        this.email = email;
        this.password = password;
    }

    public String getUserName() {
        return userName;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

    public String getId() {
        return id;
    }

}