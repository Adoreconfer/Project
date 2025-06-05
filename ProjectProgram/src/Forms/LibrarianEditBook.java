package Forms;

import DB.BookDAO;

import Class.*;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.util.List;

public class LibrarianEditBook extends JFrame{
    private JPanel JPanel1;
    private JTextField titleField;
    private JTextField authorField;
    private JTextField categoryField;
    private JSpinner spinner1;
    private JComboBox comboBox1;
    private JTextField isbnField;
    private JButton editBookButton;
    private JButton closeButton;
    private DefaultTableModel tableModel;
    BookDAO bookDAO = new BookDAO();

    public LibrarianEditBook(User librarian, Book book) throws SQLException {
        super("Library | Edit book");
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

        titleField.setText(book.getTitle());
        authorField.setText(book.getAuthor());
        categoryField.setText(book.getCategory());
        isbnField.setText(book.getIsbn());
        spinner1.setValue(book.getTotalcopies());

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
        editBookButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String title = titleField.getText();
                String author = authorField.getText();
                String category;
                String isbn = isbnField.getText().trim();
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
                if(total_copies<=0) {
                    JOptionPane.showMessageDialog(null, "Set a number greater than 0");
                    return;
                }
                if (isbn == null || !isbn.matches("\\d{13}")) {
                    JOptionPane.showMessageDialog(null, "ISBN must be a 13-digit number");
                    return;
                }
                try {
                    bookDAO.deleteBook(book.getId());
                } catch (SQLException ex) {
                    throw new RuntimeException(ex);
                }
                try {
                    if(bookDAO.getBookByISBN(isbn)!=null){
                        JOptionPane.showMessageDialog(null, "This ISBN already exists");
                        bookDAO.addBook(new Book(book.getId(), book.getTitle(), book.getAuthor(), book.getIsbn(), book.getTotalcopies(), book.getCategory(), book.getAvailablecopies()));
                        return;
                    }
                } catch (SQLException ex) {
                    throw new RuntimeException(ex);
                }
                try {
                    if(title.equals(book.getTitle()) && author.equals(book.getAuthor()) &&
                    isbn.equals(book.getIsbn()) && category.equals(book.getCategory()) && total_copies == book.getTotalcopies()) {
                        JOptionPane.showMessageDialog(null, "Nothing was edited");
                        return;
                    }
                    else
                        bookDAO.addBook(new Book(book.getId(), title, author, isbn, total_copies, category, book.getAvailablecopies()));
                } catch (SQLException ex) {
                    throw new RuntimeException(ex);
                }
                JOptionPane.showMessageDialog(null, "Book has been edited successfully!");
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
