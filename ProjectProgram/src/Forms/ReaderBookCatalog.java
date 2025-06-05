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

public class ReaderBookCatalog extends JFrame{
    private JPanel JPanel1;
    private JTextField textField1;
    private JTable table1;
    private JButton backButton;
    private JButton loanBookButton;
    private JRadioButton titleRadioButton;
    private JRadioButton autorRadioButton;
    private JComboBox comboBox1;
    private JButton searchButton;
    private JRadioButton allRadioButton;
    private DefaultTableModel tableModel;
    BookDAO bookDAO = new BookDAO();

    public ReaderBookCatalog(User reader) throws SQLException {
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
                ReaderForm readerForm = null;
                try {
                    readerForm = new ReaderForm(reader);
                } catch (SQLException ex) {
                    throw new RuntimeException(ex);
                }
                readerForm.setVisible(true);
            }
        });
        searchButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                updateData();
            }
        });
        loanBookButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    int selectedRow = table1.getSelectedRow();
                    String isbn = tableModel.getValueAt(selectedRow, 3).toString();

                    LoanDAO loanDAO = new LoanDAO();
                    loanDAO.addLoan(bookDAO.getBookByISBN(isbn), reader);
                    updateData();
                    JOptionPane.showMessageDialog(null, "Book loan successful");
                }
                catch (Exception e1){
                    JOptionPane.showMessageDialog(null, "No row selected");
                }
            }
        });
    }

    public void showData(List<Book> books) throws SQLException {
        String[] columnNames = new String[] {
                "Title","Author","Category","ISBN","Publication year","Available Copies"
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
                    String.valueOf(b.getPublication_year()),
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
