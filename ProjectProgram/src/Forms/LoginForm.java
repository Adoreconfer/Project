package Forms;
import DB.*;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;


public class LoginForm extends JFrame{
    private JPanel JPanel1;
    private JTextField loginUser;
    private JPasswordField passwordField1;
    private JButton loginButton;
    private JButton registrationButton;

    public LoginForm(){
        super("Login");
        this.setContentPane(JPanel1);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        int width = 400, height = 300;
        this.setSize(width, height);
        Image icon = Toolkit.getDefaultToolkit().getImage(LoginForm.class.getResource("/Images/login.png"));
        this.setIconImage(icon);
        this.setLocationRelativeTo(null);


        UsersDAO usersDAO = new UsersDAO();
        loginButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    String userName = loginUser.getText();
                    String userPass = new String(passwordField1.getPassword());
                    if(usersDAO.authenticateUser(userName,userPass,"banned")){
                        JOptionPane.showMessageDialog(null, "This account has been disabled.\nContact support for more information.");
                    }
                    else if (usersDAO.authenticateUser(userName, userPass, "librarian")) {
                            dispose();
                            LibrarianForm librarianForm = new LibrarianForm(usersDAO.getUserInfo(userName));
                            librarianForm.setVisible(true);
                    }
                    else if (usersDAO.authenticateUser(userName, userPass, "reader")) {
                            dispose();
                            ReaderForm readerForm = new ReaderForm(usersDAO.getUserInfo(userName));
                            readerForm.setVisible(true);
                    }
                    else{
                        JOptionPane.showMessageDialog(null, "Not logged in");
                    }
                } catch (SQLException ex) {
                    throw new RuntimeException(ex);
                }
            }
        });
        registrationButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
                Registration registration = new Registration();
                registration.setVisible(true);
            }
        });
    }
}
