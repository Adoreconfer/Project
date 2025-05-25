package DB;
import java.sql.*;
import java.sql.SQLException;

public class UsersDAO implements IUserDAO{

    //Uwierzytelnianie użytkownika
    public boolean authenticateUser(String username, String pin, String role) throws SQLException {
        String sql = "SELECT * FROM useraccount WHERE username = ? AND password = ? AND role = ?";
        try (Connection conn = DBConnection.getConnection(); PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, username);
            stmt.setString(2, pin);
            stmt.setString(3, role);
            ResultSet rs = stmt.executeQuery();
            return rs.next();
        }
    }

    //Dodanie
    public void addUser(String username, String pin, String imie, String nazwisko, String rola) throws SQLException{
        String sql = "INSERT INTO useraccount (username, password, first_name, last_name, role) VALUES (?, ?, ?, ?, ?)";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, username);
            stmt.setString(2, pin);
            stmt.setString(3, imie);
            stmt.setString(4, nazwisko);
            stmt.setString(5, rola);
            stmt.executeUpdate();
            //System.out.println("Użytkownik dodany!");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    //Sprawdzenie czy istnieje juz taki uzytkownik
    public boolean checkUser(String username) throws SQLException{
        String sql = "SELECT COUNT(*) FROM useraccount WHERE username = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, username);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt(1) > 0;
                }
            }
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    //Zmiana hasla
    public void changePass(String username, String newPass) throws SQLException{
        String sql = "UPDATE useraccount SET password = ? WHERE username = ?";
        try (Connection conn = DBConnection.getConnection(); PreparedStatement stmt = conn.prepareStatement(sql)){
            stmt.setString(1, newPass);
            stmt.setString(2, username);
            stmt.executeUpdate();
            System.out.println("PIN zmieniono!");
        }
    }
}
