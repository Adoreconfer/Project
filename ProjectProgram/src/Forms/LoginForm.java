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
        int width = 300, height = 200;
        this.setSize(width, height);
        Image icon = Toolkit.getDefaultToolkit().getImage(LoginForm.class.getResource("/Images/login.png"));
        this.setIconImage(icon);
        this.setLocationRelativeTo(null);


        UsersDAO usersCRUD = new UsersDAO();
        loginButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    String userName = loginUser.getText();
                    String userPass = new String(passwordField1.getPassword());
                    if(usersCRUD.authenticateUser(userName,userPass,"librarian")){
                        System.out.println("Jestes bibliotekarzem");
                    }
                    if(usersCRUD.authenticateUser(userName,userPass,"reader")){
                        System.out.println("Jestes uzytkownikiem");
                    }
                } catch (SQLException ex) {
                    throw new RuntimeException(ex);
                }
                finally {
                    loginUser.setText("");
                    passwordField1.setText("");
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
