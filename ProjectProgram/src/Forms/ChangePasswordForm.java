package Forms;

import Class.*;
import DB.UsersDAO;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;

public class ChangePasswordForm extends JFrame{
    private JPanel JPanel1;
    private JPasswordField conpasswordField;
    private JPasswordField passwordField;
    private JButton backButton;
    private JButton changePasswordButton;

    public ChangePasswordForm(User user){
        super("Library | Change Password");
        this.setContentPane(JPanel1);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        int width = 400, height = 300;
        this.setSize(width, height);
        Image icon = Toolkit.getDefaultToolkit().getImage(LoginForm.class.getResource("/Images/chpass.png"));
        this.setIconImage(icon);
        this.setLocationRelativeTo(null);
        backButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
                if(user.getRole().equals("reader")){
                    dispose();
                    ReaderForm readerForm = null;
                    try {
                        readerForm = new ReaderForm(user);
                    } catch (SQLException ex) {
                        throw new RuntimeException(ex);
                    }
                    readerForm.setVisible(true);
                }
                if(user.getRole().equals("librarian")){
                    dispose();
                    LibrarianForm librarianForm = new LibrarianForm(user);
                    librarianForm.setVisible(true);
                }
            }
        });
        changePasswordButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                UsersDAO usersDAO = new UsersDAO();

                String pass = new String(passwordField.getPassword());
                String confirmpass = new String(conpasswordField.getPassword());
                if(pass == null || pass.trim().isEmpty() || confirmpass == null || confirmpass.trim().isEmpty()){
                    JOptionPane.showMessageDialog(null, "Fill all fields");
                    return;
                }
                if(pass.equals(confirmpass)){
                    try {
                        usersDAO.changePass(user.getUsername(), pass);
                        JOptionPane.showMessageDialog(null, "Your password has been successfully changed");
                        passwordField.setText("");
                        conpasswordField.setText("");
                    } catch (SQLException ex) {
                        throw new RuntimeException(ex);
                    }
                }
                else{
                    JOptionPane.showMessageDialog(null, "Passwords do not match!");
                }
            }
        });
    }
}
