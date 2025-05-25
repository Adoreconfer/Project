package DB;
import Class.*;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

public class LoanDAO implements ILoanDAO{
    @Override
    public void addLoan(Book book, Reader reader) throws SQLException {
        String sql = "INSERT INTO loan (id_book, id_user, due_date) VALUES (?, ?, CURDATE() + INTERVAL 3 MONTH)";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, book.getId());
            stmt.setInt(2, reader.getId());
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public boolean returnLoan(Book book, Reader reader) throws SQLException {
        checkFine(book, reader);
        if(calculateFine(book, reader) == 0.0){
            String sql = "DELETE FROM loan WHERE id_book = ? AND id_user = ?";
            try (Connection conn = DBConnection.getConnection();
                 PreparedStatement stmt = conn.prepareStatement(sql)) {
                stmt.setInt(1, book.getId());
                stmt.setInt(2, reader.getId());
                stmt.executeUpdate();
                return true;
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        else{
            return false;
        }
        return false;
    }

    @Override
    public void checkFine(Book book, Reader reader) throws SQLException {
        String sql = "UPDATE loan SET fine = ? WERE id_book = ? AND id_user = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setDouble(1, calculateFine(book, reader));
            stmt.setInt(2, book.getId());
            stmt.setInt(3, reader.getId());
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public double calculateFine(Book book, Reader reader){
        String sql = "SELECT due_date FROM loan WHERE id_book = ? AND id_user = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, book.getId());
            stmt.setInt(2, reader.getId());

            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    LocalDate dueDate = rs.getDate("due_date").toLocalDate();
                    long daysLate = ChronoUnit.DAYS.between(dueDate, LocalDate.now());
                    if(daysLate > 0) return daysLate * 0.50;
                    else return 0.0;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0.0;
    }
}
