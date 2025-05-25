package DB;
import Class.*;

import java.sql.SQLException;

public interface ILoanDAO {
    void addLoan(Book book, Reader reader) throws SQLException;
    boolean returnLoan(Book book, Reader reader) throws SQLException;
    double calculateFine(Book book, Reader reader) throws SQLException;
    void checkFine(Book book, Reader reader) throws SQLException;
}
