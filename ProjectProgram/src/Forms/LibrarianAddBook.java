package Forms;

import Class.*;
import DB.BookDAO;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.util.List;

public class LibrarianAddBook extends JFrame{
    private JPanel JPanel1;
    private JTextField titleField;
    private JTextField authorField;
    private JTextField categoryField;
    private JPasswordField isbnField;
    private JButton addBookButton;
    private JButton closeButton;
    private JSpinner spinner1;
    private JComboBox comboBox1;
    private DefaultTableModel tableModel;
    BookDAO bookDAO = new BookDAO();

    public LibrarianAddBook(User librarian) throws SQLException {
        super("Library | Add book");
        this.setContentPane(JPanel1);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        int width = 500, height = 400;
        this.setSize(width, height);
        Image icon = Toolkit.getDefaultToolkit().getImage(LoginForm.class.getResource("/Images/book.png"));
        this.setIconImage(icon);
        this.setLocationRelativeTo(null);

        List<String> category = bookDAO.getAllCategory();
        category.set(0, "New");
        comboBox1.setModel(new DefaultComboBoxModel<>(category.toArray(new String[0])));

        closeButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
                LibrarianBookCatalog librarianBookCatalog = null;
                try {
                    librarianBookCatalog = new LibrarianBookCatalog(librarian);
                } catch (SQLException ex) {
                    throw new RuntimeException(ex);
                }
                librarianBookCatalog.setVisible(true);
            }
        });
    }
}
