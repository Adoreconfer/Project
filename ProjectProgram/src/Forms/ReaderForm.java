package Forms;
import Class.*;
import DB.UsersDAO;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;

public class ReaderForm extends JFrame{
    private JPanel JPanel1;
    private JButton bookCatalogButton;
    private JButton bookLoansButton;
    private JLabel welcomeLbl;
    private JButton logoutButton;

    public ReaderForm(User reader) throws SQLException {
        super("Library | Reader");
        this.setContentPane(JPanel1);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        int width = 400, height = 300;
        this.setSize(width, height);
        Image icon = Toolkit.getDefaultToolkit().getImage(LoginForm.class.getResource("/Images/book.png"));
        this.setIconImage(icon);
        this.setLocationRelativeTo(null);

        welcomeLbl.setText("Welcome, "+reader.getUsername());


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
                ReaderBookCatalog readerBookCatalog = null;
                try {
                    readerBookCatalog = new ReaderBookCatalog(reader);
                } catch (SQLException ex) {
                    throw new RuntimeException(ex);
                }
                readerBookCatalog.setVisible(true);
            }
        });
        bookLoansButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
                ReaderBookLoan readerBookLoan = new ReaderBookLoan(reader);
                readerBookLoan.setVisible(true);
            }
        });
    }

}
