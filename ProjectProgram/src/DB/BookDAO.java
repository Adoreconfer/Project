package DB;
import Class.*;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class BookDAO implements IBookDAO{
    @Override
    public void addBook(Book book) throws SQLException {
        String sql = "INSERT INTO book (title, author, isbn, publication_year, total_copies, category, available_copies) VALUES (?, ?, ?, ?, ?, ?, ?)";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, book.getTitle());
            stmt.setString(2, book.getAuthor());
            stmt.setString(3, book.getIsbn());
            stmt.setInt(4, book.getPublication_year());
            stmt.setInt(5, book.getTotalcopies());
            stmt.setString(6, book.getCategory());
            stmt.setInt(7, book.getAvailablecopies());
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void updateBook(Book book) throws SQLException {
        String sql = "UPDATE book SET title = ?, author = ?, publication_year = ?, total_copies = ?, category = ?, available_copies = ? WHERE id_book = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, book.getTitle());
            stmt.setString(2, book.getAuthor());
            stmt.setInt(3, book.getPublication_year());
            stmt.setInt(4, book.getTotalcopies());
            stmt.setString(5, book.getCategory());
            stmt.setInt(6, book.getAvailablecopies());
            stmt.setInt(7, book.getId());

            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public Book getBookByISBN(String isbn) throws SQLException{
        String sql = "SELECT * FROM book WHERE isbn = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, isbn);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return new Book(rs.getInt("id_book"),
                            rs.getString("title"),
                            rs.getString("author"),
                            rs.getString("isbn"),
                            rs.getInt("publication_year"),
                            rs.getInt("total_copies"),
                            rs.getString("category"),
                            rs.getInt("available_copies"));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public void deleteBook(int id) throws SQLException {
        String sql = "DELETE FROM book WHERE id_book = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, id);
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public List<String> getAllCategory() throws SQLException {
        String sql = "SELECT category FROM book GROUP BY category ASC";
        List<String> category = new ArrayList<>();
        category.add("All");
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    category.add(rs.getString("category"));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return category;
    }

    public List<Book> searchBook(String title, String author, String category) throws SQLException{
        List<Book> books = new ArrayList<>();
        String sql = "SELECT * FROM book WHERE title LIKE ? AND author LIKE ? AND category LIKE ? AND available_copies > 0 ORDER BY title ASC";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, title + "%");
            stmt.setString(2, author + "%");
            stmt.setString(3, category + "%");
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    books.add(new Book(
                            rs.getInt("id_book"),
                            rs.getString("title"),
                            rs.getString("author"),
                            rs.getString("isbn"),
                            rs.getInt("publication_year"),
                            rs.getInt("total_copies"),
                            rs.getString("category"),
                            rs.getInt("available_copies")
                    ));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return books;
    }

}
