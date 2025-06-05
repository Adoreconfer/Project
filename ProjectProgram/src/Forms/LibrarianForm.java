package Forms;

import Class.*;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
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

    public LibrarianForm(User librarian){
        super("Library | Librarian");
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
                    throw new RuntimeException(ex);
                }
                viewUserListForm.setVisible(true);
            }
        });
    }
}
