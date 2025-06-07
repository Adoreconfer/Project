package Class;

public class Loan{
    private int id;
    private int idbook;
    private int iduser;
    private String duedate;
    private String loandate;
    private String returndate;
    private double fine;
    private Book book;
    private String username;
    private String status;

    public Loan(int id, Book book, String loandate, String duedate, double fine){
        this.id = id;
        this.book = book;
        this.loandate = loandate;
        this.duedate = duedate;
        this.fine = fine;
    }

    public Loan(int id, int idbook, int iduser, String duedate, String loandate, String returndate, double fine) {
        this.id = id;
        this.idbook = idbook;
        this.iduser = iduser;
        this.duedate = duedate;
        this.loandate = loandate;
        this.returndate = returndate;
        this.fine = fine;
    }

    public Book getBook() {
        return book;
    }

    public String getUser() {
        return username;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setIdbook(int idbook) {
        this.idbook = idbook;
    }

    public void setIduser(int iduser) {
        this.iduser = iduser;
    }

    public void setDuedate(String duedate) {
        this.duedate = duedate;
    }

    public void setLoandate(String loandate) {
        this.loandate = loandate;
    }

    public void setReturndate(String returndate) {
        this.returndate = returndate;
    }

    public void setFine(double fine) {
        this.fine = fine;
    }

    public int getId() {
        return id;
    }

    public int getIdbook() {
        return idbook;
    }

    public int getIduser() {
        return iduser;
    }

    public String getDuedate() {
        return duedate;
    }

    public String getLoandate() {
        return loandate;
    }

    public String getReturndate() {
        return returndate;
    }

    public double getFine() {
        return fine;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getStatus() {
        return status;
    }

}
