package Class;

public class Librarian extends User{
    public Librarian(){
        this.role = "librarian";
    }

    public Librarian(int id, String username, String password, String firstname, String lastname) {
        super(id, username, password, firstname, lastname, "librarian");
    }
}
