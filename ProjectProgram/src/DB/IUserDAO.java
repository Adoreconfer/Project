package DB;

import java.sql.SQLException;

public interface IUserDAO {
    boolean authenticateUser(String username, String pin, String role) throws SQLException;
    void addUser(String username, String pin, String imie, String nazwisko, String rola) throws SQLException;
    boolean checkUser(String username) throws SQLException;
    void changePass(String username, String newPass) throws SQLException;
}
