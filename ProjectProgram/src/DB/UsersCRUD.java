package DB;
import java.sql.*;
import java.sql.SQLException;

public class UsersCRUD {

    //Uwierzytelnianie użytkownika
    public boolean authenticateUser(String username, String pin, String role) throws SQLException {
        String sql = "SELECT * FROM uzytkownik WHERE username = ? AND pin = ? AND rola = ?";
        try (Connection conn = DBConnection.getConnection(); PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, username);
            stmt.setString(2, pin);
            stmt.setString(3, role);
            ResultSet rs = stmt.executeQuery();
            return rs.next();
        }
    }

    //Dodanie
    public void addUser(String username, String pin, String imie, String nazwisko) throws SQLException{
        String sql = "INSERT INTO uzytkownik (username, pin, imie, nazwisko, rola) VALUES (?, ?, 0, 'uzytkownik')";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, username);
            stmt.setString(2, pin);
            stmt.setString(3, imie);
            stmt.setString(4, nazwisko);
            stmt.executeUpdate();
            System.out.println("Użytkownik dodany!");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    //Zmiana PIN-u
    public void changePIN(String username, String newPin) throws SQLException{
        String sql = "UPDATE uzytkownik SET pin = ? WHERE username = ?";
        try (Connection conn = DBConnection.getConnection(); PreparedStatement stmt = conn.prepareStatement(sql)){
            stmt.setString(1, newPin);
            stmt.setString(2, username);
            stmt.executeUpdate();
            System.out.println("PIN zmieniono!");
        }
    }



}
