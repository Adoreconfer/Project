package Class;

public class Loan {
    private int id;
    private int idloan;
    private int iduser;
    private String duedate;
    private String loandate;
    private String returndate;
    private double fine;

    public Loan(){}

    public Loan(int id, int idloan, int iduser, String duedate, String loandate, String returndate, double fine) {
        this.id = id;
        this.idloan = idloan;
        this.iduser = iduser;
        this.duedate = duedate;
        this.loandate = loandate;
        this.returndate = returndate;
        this.fine = fine;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setIdloan(int idloan) {
        this.idloan = idloan;
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

    public int getIdloan() {
        return idloan;
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
}
