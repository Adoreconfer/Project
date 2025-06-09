package Forms;

import DB.UsersDAO;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;

public class Registration extends JFrame{
    private JPanel JPanel1;
    private JTextField firstNameField;
    private JTextField lastNameField;
    private JTextField userNameField;
    private JPasswordField passwordField;
    private JButton registrationButton;
    private JButton closeButton;

    public Registration(){
        super("Library | Register");
        this.setContentPane(JPanel1);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        int width = 400, height = 300;
        this.setSize(width, height);
        Image icon = Toolkit.getDefaultToolkit().getImage(LoginForm.class.getResource("/Images/reg.png"));
        this.setIconImage(icon);
        this.setLocationRelativeTo(null);

        UsersDAO usersCRUD = new UsersDAO();
        LoginForm loginForm = new LoginForm();

        registrationButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String userName = userNameField.getText();
                String userPassword = new String(passwordField.getPassword());
                String userFirstName = firstNameField.getText();
                String userLastName = lastNameField.getText();
                if (userName == null || userName.trim().isEmpty() ||
                        userPassword == null || userPassword.trim().isEmpty() ||
                        userFirstName == null || userFirstName.trim().isEmpty() ||
                        userLastName == null || userLastName.trim().isEmpty()) {
                    JOptionPane.showMessageDialog(null, "Fill all fields");
                }
                if(userName.matches(".*[,.!?].*") || userFirstName.matches(".*[,.!?].*") || userLastName.matches(".*[,.!?].*")){
                    JOptionPane.showMessageDialog(null, "Do not enter the characters , . ! ?");
                }
                else{
                    try {
                        if(usersCRUD.checkUser(userName)){
                            userNameField.setText("");
                            JOptionPane.showMessageDialog(null, "This username already exists");
                        }
                        else{
                            usersCRUD.addUser(userName, userPassword, userFirstName,userLastName, "reader");
                            JOptionPane.showMessageDialog(null, "Registered");
                            dispose();
                            loginForm.setVisible(true);
                        }
                    } catch (SQLException ex) {
                        throw new RuntimeException(ex);
                    }
                }
            }
        });
        closeButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
                loginForm.setVisible(true);
            }
        });
    }
}
