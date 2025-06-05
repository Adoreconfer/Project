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
    private JTextField isbnField;
    private JButton addBookButton;
    private JButton closeButton;
    private JSpinner spinner1;
    private JComboBox comboBox1;
    private JTextField publicationYearField;
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
        addBookButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String title = titleField.getText();
                String author = authorField.getText();
                String category;
                String isbn = isbnField.getText().trim();
                int publication_year = Integer.parseInt(publicationYearField.getText());
                int total_copies = (int) spinner1.getValue();
                if (title == null || title.trim().isEmpty() ||
                        author == null || author.trim().isEmpty()) {
                    JOptionPane.showMessageDialog(null, "Fill all fields");
                    return;
                }
                String selectedCategory = comboBox1.getItemAt(comboBox1.getSelectedIndex()).toString();
                if (selectedCategory.equals("New")) {
                    category = categoryField.getText().trim();
                    if (category.isEmpty()) {
                        JOptionPane.showMessageDialog(null, "Enter a category in the text field or select one from the list.");
                        return;
                    }
                } else {
                    category = selectedCategory;
                }
                if(total_copies<=0 || publication_year<0) {
                    JOptionPane.showMessageDialog(null, "Set a number greater than 0");
                    return;
                }
                if (!isbn.matches("\\d{13}")) {
                    JOptionPane.showMessageDialog(null, "ISBN must be a 13-digit number");
                    return;
                }
                try {
                    if(bookDAO.getBookByISBN(isbn)!=null){
                        JOptionPane.showMessageDialog(null, "This ISBN already exists");
                        return;
                    }
                } catch (SQLException ex) {
                    throw new RuntimeException(ex);
                }
                try {
                    bookDAO.addBook(new Book(title, author, isbn, publication_year,total_copies, category, total_copies));
                } catch (SQLException ex) {
                    throw new RuntimeException(ex);
                }
                titleField.setText("");
                authorField.setText("");
                categoryField.setText("");
                isbnField.setText("");
                publicationYearField.setText("");
                comboBox1.setSelectedIndex(0);
                spinner1.setValue(0);
                JOptionPane.showMessageDialog(null, "Book has been added successfully!");
            }
        });
    }
}
