import Forms.LoginForm;
import java.io.IOException;
import java.sql.SQLException;

public class Main {
    public static void main(String[] args) throws IOException, SQLException {
        LoginForm loginForm = new LoginForm();
        loginForm.setVisible(true);
    }
}