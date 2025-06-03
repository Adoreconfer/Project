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

public class LibrarianBookCatalog extends JFrame{
    private JPanel JPanel1;
    private JTextField textField1;
    private JButton searchButton;
    private JComboBox comboBox1;
    private JRadioButton autorRadioButton;
    private JRadioButton titleRadioButton;
    private JRadioButton allRadioButton;
    private JTable table1;
    private JButton backButton;
    private JButton addBookButton;
    private JButton editBookButton;
    private JButton deleteBookButton;
    private DefaultTableModel tableModel;
    BookDAO bookDAO = new BookDAO();

    public LibrarianBookCatalog(User librarian) throws SQLException {
        super("Library | Catalog");
        this.setContentPane(JPanel1);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        int width = 500, height = 400;
        this.setSize(width, height);
        Image icon = Toolkit.getDefaultToolkit().getImage(LoginForm.class.getResource("/Images/book.png"));
        this.setIconImage(icon);
        this.setLocationRelativeTo(null);

        comboBox1.setModel(new DefaultComboBoxModel<>(bookDAO.getAllCategory().toArray(new String[0])));

        backButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
                LibrarianForm librarianForm = null;
                librarianForm = new LibrarianForm(librarian);
                librarianForm.setVisible(true);
            }
        });
        searchButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                updateData();
            }
        });
        addBookButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
                LibrarianAddBook librarianAddBook = null;
                try {
                    librarianAddBook = new LibrarianAddBook(librarian);
                } catch (SQLException ex) {
                    throw new RuntimeException(ex);
                }
                librarianAddBook.setVisible(true);
            }
        });
    }

    public void showData(List<Book> books) throws SQLException {
        String[] columnNames = new String[] {
                "Title","Author","Category","ISBN","Available Copies"
        };

        tableModel = new DefaultTableModel(columnNames, 0){
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        table1.setModel(tableModel);

        String[] rowData;
        for (Book b: books){
            rowData = new String[]{
                    b.getTitle(),
                    b.getAuthor(),
                    b.getCategory(),
                    b.getIsbn(),
                    String.valueOf(b.getAvailablecopies())
            };
            tableModel.addRow(rowData);
        }
    }

    public void updateData(){
        try {
            String category = comboBox1.getItemAt(comboBox1.getSelectedIndex()).toString();
            String search = textField1.getText();
            if(category.equals(comboBox1.getItemAt(0))){
                category="";
            }
            if(search==null || search.trim().isEmpty() && !allRadioButton.isSelected()){
                JOptionPane.showMessageDialog(null, "Select 'All' or enter text to search");
            }
            else {
                if (autorRadioButton.isSelected()) {
                    showData(bookDAO.searchBook("", search, category));
                }
                if (titleRadioButton.isSelected()) {
                    showData(bookDAO.searchBook(search, "", category));
                }
                if (allRadioButton.isSelected()) {
                    showData(bookDAO.searchBook("", "", category));
                }
            }
        } catch (SQLException ex) {
            throw new RuntimeException(ex);
        }
    }
}
