package Forms;

import Class.*;
import DB.UsersDAO;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.util.List;

public class ViewUserListForm extends JFrame {
    private JPanel JPanel1;
    private JTextField textField1;
    private JButton searchButton;
    private JTable table1;
    private JButton backButton;
    private JButton moreInfoButton;
    private JRadioButton allRadioButton;
    private DefaultTableModel tableModel;
    UsersDAO usersDAO = new UsersDAO();

    public ViewUserListForm(User librarian) throws SQLException {
        super("Library | Users");
        this.setContentPane(JPanel1);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        int width = 500, height = 400;
        this.setSize(width, height);
        Image icon = Toolkit.getDefaultToolkit().getImage(LoginForm.class.getResource("/Images/book.png"));
        this.setIconImage(icon);
        this.setLocationRelativeTo(null);

        backButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
                LibrarianForm librarianForm = new LibrarianForm(librarian);
                librarianForm.setVisible(true);
            }
        });
        searchButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String search = textField1.getText();
                try {
                    if(!allRadioButton.isSelected() && (search == null || search.trim().isEmpty())){
                        JOptionPane.showMessageDialog(null, "Select 'All' or enter text to search");
                    }
                    else {
                        showData(usersDAO.searchUsers(search));
                    }
                } catch (SQLException ex) {
                    throw new RuntimeException(ex);
                }
            }
        });
        moreInfoButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

            }
        });
    }

    public void showData(List<User> users) throws SQLException {
        String[] columnNames = new String[] {
                "Id","First name","Last name","Username","Role"
        };

        tableModel = new DefaultTableModel(columnNames, 0){
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        table1.setModel(tableModel);

        String[] rowData;
        for (User u: users){
            rowData = new String[]{
                    String.valueOf(u.getId()),
                    u.getFirstname(),
                    u.getLastname(),
                    u.getUsername(),
                    u.getRole(),

            };
            tableModel.addRow(rowData);
        }
    }
}
