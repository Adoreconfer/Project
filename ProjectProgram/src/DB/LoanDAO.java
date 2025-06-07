package DB;
import Class.*;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;

public class LoanDAO implements ILoanDAO{
    @Override
    public void addLoan(Book book, User reader) throws SQLException {
        String sql = "INSERT INTO loan (id_book, id_user, due_date) VALUES (?, ?, CURDATE() + INTERVAL 3 MONTH)";
        String sqlset = "UPDATE book SET available_copies = available_copies-1 WHERE id_book = ?";
        try (Connection conn = DBConnection.getConnection()) {
            try (PreparedStatement insertStmt = conn.prepareStatement(sql);
                 PreparedStatement updateStmt = conn.prepareStatement(sqlset)) {
                insertStmt.setInt(1, book.getId());
                insertStmt.setInt(2, reader.getId());
                insertStmt.executeUpdate();

                updateStmt.setInt(1, book.getId());
                updateStmt.executeUpdate();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public boolean returnLoan(Book book, User reader, int idloan) throws SQLException {
            String sql = "UPDATE loan SET status = 'returned', return_date = CURDATE() WHERE id_book = ? AND id_user = ? AND id_loan = ?";
            String sqlset = "UPDATE book SET available_copies = available_copies+1 WHERE id_book = ?";
            try (Connection conn = DBConnection.getConnection()){
                 try(PreparedStatement delStmt = conn.prepareStatement(sql);
                     PreparedStatement updateStmt = conn.prepareStatement(sqlset)) {
                     delStmt.setInt(1, book.getId());
                     delStmt.setInt(2, reader.getId());
                     delStmt.setInt(3, idloan);
                     delStmt.executeUpdate();

                     updateStmt.setInt(1, book.getId());
                     updateStmt.executeUpdate();
                     return true;
                 }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        return false;
    }

    public List<Loan> viewLoans(User reader) throws SQLException{
        List<Loan> loan = new ArrayList<>();
        String sql = "SELECT l.id_loan, b.title, b.author, b.isbn, l.loan_date, l.due_date, l.fine FROM loan l JOIN book b ON l.id_book = b.id_book WHERE l.id_user = ? AND l.status = 'loaned'";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)){
             stmt.setInt(1, reader.getId());
             try(ResultSet rs = stmt.executeQuery()){
                while (rs.next()) {
                    loan.add(new Loan(rs.getInt("id_loan") ,new Book(rs.getString("title"),
                            rs.getString("author"),
                            rs.getString("isbn")),
                            rs.getString("loan_date"),
                            rs.getString("due_date"),
                            rs.getDouble("fine")));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return loan;
    }


    @Override
    public void checkFine(Book book, User reader, int idloan) throws SQLException {
        String sql = "UPDATE loan SET fine = ? WHERE id_book = ? AND id_user = ? AND id_loan = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setDouble(1, calculateFine(book, reader, idloan));
            stmt.setInt(2, book.getId());
            stmt.setInt(3, reader.getId());
            stmt.setInt(4, idloan);
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public double calculateFine(Book book, User reader, int idloan){
        String sql = "SELECT due_date FROM loan WHERE id_book = ? AND id_user = ? AND id_loan = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, book.getId());
            stmt.setInt(2, reader.getId());
            stmt.setInt(3, idloan);

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
