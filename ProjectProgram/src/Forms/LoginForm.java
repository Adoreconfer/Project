package Forms;
import DB.*;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;

public class LoginForm extends JFrame{
    private JPanel JPanel1;
    private JTextField loginUser;
    private JPasswordField passwordField1;
    private JButton loginButton;

    public LoginForm(){
        super("Login");
        this.setContentPane(JPanel1);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        int width = 300, height = 200;
        this.setSize(width, height);

        UsersCRUD usersCRUD = new UsersCRUD();
        loginButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    String userName = loginUser.getText();
                    String userPass = new String(passwordField1.getPassword());
                    if(usersCRUD.authenticateUser(userName,userPass,"bibliotekarz")){
                        System.out.println("Jestes bibliotekarzem");
                    }
                    if(usersCRUD.authenticateUser(userName,userPass,"uzytkownik")){
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
    }
}
