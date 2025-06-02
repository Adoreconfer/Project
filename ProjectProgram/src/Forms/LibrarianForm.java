package Forms;

import Class.*;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class LibrarianForm extends JFrame{
    private JLabel welcomeLbl;
    private JButton logoutButton;
    private JPanel JPanel1;
    private JButton bookCatalogButton;
    private JButton viewUserListButton;

    public LibrarianForm(User reader){
        super("Library | Librarian");
        this.setContentPane(JPanel1);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        int width = 300, height = 200;
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
    }
}
