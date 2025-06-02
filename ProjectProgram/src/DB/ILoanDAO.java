package DB;
import Class.*;

import java.sql.SQLException;
import java.util.List;

public interface ILoanDAO {
    void addLoan(Book book, User reader) throws SQLException;
    boolean returnLoan(Book book, User reader, int idloan) throws SQLException;
    List<Loan> viewLoans(User reader) throws SQLException;
    double calculateFine(Book book, User reader, int idloan) throws SQLException;
    void checkFine(Book book, User reader, int idloan) throws SQLException;
}
