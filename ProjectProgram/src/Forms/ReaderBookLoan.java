package Forms;

import Class.*;
import DB.BookDAO;
import DB.LoanDAO;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.util.List;

public class ReaderBookLoan extends JFrame{
    private JPanel JPanel1;
    private JTable table1;
    private JButton backButton;
    private JButton returnBookButton;
    private DefaultTableModel tableModel;
    LoanDAO loanDAO = new LoanDAO();
    BookDAO bookDAO = new BookDAO();

    public ReaderBookLoan(User reader, User user){
        super("Library | Loan");
        this.setContentPane(JPanel1);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        int width = 500, height = 400;
        this.setSize(width, height);
        Image icon = Toolkit.getDefaultToolkit().getImage(LoginForm.class.getResource("/Images/book.png"));
        this.setIconImage(icon);
        this.setLocationRelativeTo(null);

        if(user.getRole().equals("librarian")){
            returnBookButton.setVisible(true);
        }
        else{
            returnBookButton.setVisible(false);
        }

        try {
            showData(loanDAO.viewLoans(reader), reader);
        } catch (SQLException ex) {
            throw new RuntimeException(ex);
        }
        backButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
                if(user.getRole().equals("reader")) {
                    ReaderForm readerForm = null;
                    try {
                        readerForm = new ReaderForm(reader);
                    } catch (SQLException ex) {
                        throw new RuntimeException(ex);
                    }
                    readerForm.setVisible(true);
                }
                else{
                    ViewUserListForm viewUserListForm = null;
                    try {
                        viewUserListForm = new ViewUserListForm(user);
                    } catch (SQLException ex) {
                        throw new RuntimeException(ex);
                    }
                    viewUserListForm.setVisible(true);
                }
            }
        });
        returnBookButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    int selectedRow = table1.getSelectedRow();
                    String isbn = tableModel.getValueAt(selectedRow, 3).toString();
                    int id = Integer.parseInt(tableModel.getValueAt(selectedRow, 0).toString());
                    LoanDAO loanDAO = new LoanDAO();
                    loanDAO.returnLoan(bookDAO.getBookByISBN(isbn), reader, id);
                    tableModel.removeRow(selectedRow);
                    JOptionPane.showMessageDialog(null, "Book returned successfully");
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(null, "No row selected");
                }

            }
        });
    }

    public void showData(List<Loan> loans, User reader) throws SQLException {
        String[] columnNames = new String[] {
                "Id","Title","Author","ISBN","Loan date","Due date","Fine"
        };

        tableModel = new DefaultTableModel(columnNames, 0){
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        table1.setModel(tableModel);

        String[] rowData;
        for (Loan l: loans){
            loanDAO.checkFine(bookDAO.getBookByISBN(l.getBook().getIsbn()), reader, l.getId());
            rowData = new String[]{
                    String.valueOf(l.getId()),
                    l.getBook().getTitle(),
                    l.getBook().getAuthor(),
                    l.getBook().getIsbn(),
                    l.getLoandate(),
                    l.getDuedate(),
                    String.valueOf(l.getFine())
            };
            tableModel.addRow(rowData);
        }
    }
}
