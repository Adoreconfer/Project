package DB;

import java.io.*;

import java.sql.*;
import java.util.Locale;


public class Data {
    private String path = new File("src/DataCSV").getAbsolutePath();

    public void ExportBooks() throws IOException, SQLException{
        String csvFile = path+"/books.csv";

        String sql = "SELECT * FROM book";

        try (Connection conn = DBConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql);
             PrintWriter writer = new PrintWriter(new FileWriter(csvFile))) {

            writer.println("id_book,title,author,isbn,total_copies,category,available_copies,publication_year");

            while (rs.next()) {
                int id = rs.getInt("id_book");
                String title = rs.getString("title");
                String author = rs.getString("author");
                String isbn = rs.getString("isbn");
                int total = rs.getInt("total_copies");
                String category = rs.getString("category");
                int available = rs.getInt("available_copies");
                int publication_year = rs.getInt("publication_year");

                writer.printf("%d,%s,%s,%s,%d,%s,%d,%d%n",
                        id, title, author, isbn, total, category, available, publication_year);
            }

        }
    }

    public void ImportBooks() throws IOException, SQLException{
        String csvFile = path+"/books.csv";
        String line;
        String cvsSplitBy = ",";

        File file = new File(path+"/books.csv");
        if (!file.exists()) {
            throw new FileNotFoundException("File "+file.getAbsolutePath()+" not found");
        }

        try (BufferedReader br = new BufferedReader(new FileReader(csvFile))) {
            Connection conn = DBConnection.getConnection();
            String sql = "INSERT INTO book (id_book, title, author, isbn, total_copies, category, available_copies, publication_year) " +
                    "VALUES (?, ?, ?, ?, ?, ?, ?, ?) " +
                    "ON DUPLICATE KEY UPDATE title=VALUES(title), author=VALUES(author), isbn=VALUES(isbn), " +
                    "total_copies=VALUES(total_copies), category=VALUES(category), available_copies=VALUES(available_copies), publication_year=VALUES(publication_year)";
            PreparedStatement stmt = conn.prepareStatement(sql);

            br.readLine();


            while ((line = br.readLine()) != null) {
                String[] data = line.split(cvsSplitBy);

                stmt.setInt(1, Integer.parseInt(data[0].trim()));
                stmt.setString(2, data[1].trim());
                stmt.setString(3, data[2].trim());
                stmt.setString(4, data[3].trim());
                stmt.setInt(5, Integer.parseInt(data[4].trim()));
                stmt.setString(6, data[5].trim());
                stmt.setInt(7, Integer.parseInt(data[6].trim()));
                stmt.setInt(8, Integer.parseInt(data[7].trim()));

                stmt.executeUpdate();

            }

        }
    }

    public void ExportUsers() throws IOException, SQLException{
        String csvFile = path+"/users.csv";

        String sql = "SELECT * FROM useraccount";

        try (Connection conn = DBConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql);
             PrintWriter writer = new PrintWriter(new FileWriter(csvFile))) {

            writer.println("id_user,username,password,first_name,last_name,role");

            while (rs.next()) {
                int id = rs.getInt("id_user");
                String username = rs.getString("username");
                String password = rs.getString("password");
                String first_name = rs.getString("first_name");
                String last_name = rs.getString("last_name");
                String role = rs.getString("role");

                writer.printf("%d,%s,%s,%s,%s,%s%n",
                        id, username, password, first_name, last_name, role);
            }

        }
    }

    public void ImportUsers() throws IOException, SQLException{
        String csvFile = path+"/users.csv";
        String line;
        String cvsSplitBy = ",";

        File file = new File(path+"/users.csv");
        if (!file.exists()) {
            throw new FileNotFoundException("File "+file.getAbsolutePath()+" not found");
        }

        try (BufferedReader br = new BufferedReader(new FileReader(csvFile))) {
            Connection conn = DBConnection.getConnection();
            String sql = "INSERT INTO useraccount (id_user,username,password,first_name,last_name,role) " +
                    "VALUES (?, ?, ?, ?, ?, ?) " +
                    "ON DUPLICATE KEY UPDATE username=VALUES(username), password=VALUES(password), first_name=VALUES(first_name), " +
                    "last_name=VALUES(last_name), role=VALUES(role)";
            PreparedStatement stmt = conn.prepareStatement(sql);

            br.readLine();


            while ((line = br.readLine()) != null) {
                String[] data = line.split(cvsSplitBy);

                stmt.setInt(1, Integer.parseInt(data[0].trim()));
                stmt.setString(2, data[1].trim());
                stmt.setString(3, data[2].trim());
                stmt.setString(4, data[3].trim());
                stmt.setString(5, data[4].trim());
                stmt.setString(6, data[5].trim());

                stmt.executeUpdate();

            }

        }
    }

    public void ExportLoan() throws IOException, SQLException{
        String csvFile = path+"/loans.csv";

        String sql = "SELECT * FROM loan";

        try (Connection conn = DBConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql);
             PrintWriter writer = new PrintWriter(new FileWriter(csvFile))) {

            writer.println("id_loan,id_book,id_user,due_date,fine,loan_date,return_date,status");

            while (rs.next()) {
                int id = rs.getInt("id_loan");
                int id_book = rs.getInt("id_book");
                int id_user = rs.getInt("id_user");
                String due_date = rs.getString("due_date");
                double fine = rs.getDouble("fine");
                String loan_date = rs.getString("loan_date");
                String return_date = rs.getString("return_date");
                String status = rs.getString("status");

                String fineFormatted = String.format(Locale.US, "%.2f", fine);
                writer.printf("%d,%d,%d,%s,%s,%s,%s,%s%n",
                        id, id_book, id_user, due_date, fineFormatted, loan_date, return_date, status);
            }

        }
    }

    public void ImportLoans() throws IOException, SQLException{
        String csvFile = path+"/loans.csv";
        String line;
        String cvsSplitBy = ",";

        File file = new File(path+"/loans.csv");
        if (!file.exists()) {
            throw new FileNotFoundException("File "+file.getAbsolutePath()+" not found");
        }

        try (BufferedReader br = new BufferedReader(new FileReader(csvFile))) {
            Connection conn = DBConnection.getConnection();
            String sql = "INSERT INTO loan (id_loan,id_book,id_user,due_date,fine,loan_date,return_date,status) " +
                    "VALUES (?, ?, ?, ?, ?, ?, ?, ?) " +
                    "ON DUPLICATE KEY UPDATE id_book=VALUES(id_book), id_user=VALUES(id_user), due_date=VALUES(due_date), " +
                    "fine=VALUES(fine), loan_date=VALUES(loan_date), return_date=VALUES(return_date), status=VALUES(status)";
            PreparedStatement stmt = conn.prepareStatement(sql);

            br.readLine();


            while ((line = br.readLine()) != null) {
                String[] data = line.split(cvsSplitBy);

                stmt.setInt(1, Integer.parseInt(data[0].trim()));
                stmt.setInt(2, Integer.parseInt(data[1].trim()));
                stmt.setInt(3, Integer.parseInt(data[2].trim()));
                stmt.setString(4, data[3].trim());
                stmt.setDouble(5, Double.parseDouble(data[4].trim()));
                stmt.setString(6, data[5].trim());
                stmt.setString(7, data[6].trim());
                stmt.setString(8, data[7].trim());

                stmt.executeUpdate();

            }

        }
    }
}
