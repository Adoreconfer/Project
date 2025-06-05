package DB;
import Class.*;

import java.sql.SQLException;
import java.util.List;

public interface IUserDAO {
    boolean authenticateUser(String username, String pin, String role) throws SQLException;
    void addUser(String username, String pin, String imie, String nazwisko, String rola) throws SQLException;
    boolean checkUser(String username) throws SQLException;
    User getUserInfo(String username) throws SQLException;
    List<User> getAllUsers() throws SQLException;
    List<User> searchUsers(String username) throws SQLException;
    //void changePass(String username, String newPass) throws SQLException;
}
