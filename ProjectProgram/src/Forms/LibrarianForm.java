package Forms;

import Class.*;
import DB.DBConnection;
import DB.Data;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.SQLException;

public class LibrarianForm extends JFrame{
    private JLabel welcomeLbl;
    private JButton logoutButton;
    private JPanel JPanel1;
    private JButton bookCatalogButton;
    private JButton viewUserListButton;
    private JButton exportDataButton;
    private JButton importDataButton;
    private JCheckBox loansCheckBox1;
    private JCheckBox booksCheckBox1;
    private JCheckBox booksCheckBox;
    private JCheckBox loansCheckBox;
    private JCheckBox usersCheckBox;
    private JCheckBox usersCheckBox1;

    public LibrarianForm(User librarian){
        super("Library | Menu");
        this.setContentPane(JPanel1);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        int width = 400, height = 300;
        this.setSize(width, height);
        Image icon = Toolkit.getDefaultToolkit().getImage(LoginForm.class.getResource("/Images/book.png"));
        this.setIconImage(icon);
        this.setLocationRelativeTo(null);

        welcomeLbl.setText("Welcome, "+librarian.getUsername());
        logoutButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
                LoginForm loginForm = new LoginForm();
                loginForm.setVisible(true);
            }
        });
        bookCatalogButton.addActionListener(new ActionListener() {
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
        viewUserListButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
                ViewUserListForm viewUserListForm = null;
                try {
                    viewUserListForm = new ViewUserListForm(librarian);
                } catch (SQLException ex) {
                }
                viewUserListForm.setVisible(true);
            }
        });
        exportDataButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Data data = new Data();
                boolean selected = false;
                try {
                    if(loansCheckBox1.isSelected()){
                        selected = true;
                        data.ExportLoan();
                    }
                    if(booksCheckBox1.isSelected()){
                        selected = true;
                        data.ExportBooks();
                    }
                    if(usersCheckBox1.isSelected()){
                        selected = true;
                        data.ExportUsers();
                    }
                    if(!selected){
                        JOptionPane.showMessageDialog(null, "Nothing selected");
                        return;
                    }
                    JOptionPane.showMessageDialog(null, "Database has been exported");
                }
                catch (IOException | SQLException ex) {
                    JOptionPane.showMessageDialog(null, "Lost connection");
                    throw new RuntimeException(ex);
                }
            }
        });
        importDataButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Data data = new Data();
                boolean selected = false;
                try {
                    if(loansCheckBox.isSelected()){
                        selected = true;
                        data.ImportLoans();
                    }
                    if(booksCheckBox.isSelected()){
                        selected = true;
                        data.ImportBooks();
                    }
                    if(usersCheckBox.isSelected()){
                        selected = true;
                        data.ImportUsers();
                    }
                    if(!selected){
                        JOptionPane.showMessageDialog(null, "Nothing selected");
                        return;
                    }
                    JOptionPane.showMessageDialog(null, "Database has been updated");
                } catch (FileNotFoundException ex) {
                    JOptionPane.showMessageDialog(null, ex.getMessage());
                }
                catch (IOException | SQLException ex) {
                    JOptionPane.showMessageDialog(null, "Lost connection");
                    throw new RuntimeException(ex);
                }
            }
        });
    }
}
