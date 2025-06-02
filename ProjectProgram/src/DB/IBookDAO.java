package DB;
import Class.*;

import java.sql.SQLException;
import java.util.List;

public interface IBookDAO {
    void addBook(Book book) throws SQLException;
    List<Book> getAllBooks() throws SQLException;
    Book getBookById(int id) throws SQLException;
    Book getBookByISBN(String isbn) throws SQLException;
    void deleteBook(int id) throws SQLException;
    void updateBook(Book book) throws SQLException;
    List<String> getAllCategory() throws SQLException;
    List<Book> searchBook(String title, String author, String category) throws SQLException;
}
