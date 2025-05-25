package Class;

public class Reader extends User{
    public Reader(){
        this.role = "reader";
    }

    public Reader(int id, String username, String password, String firstname, String lastname) {
        super(id, username, password, firstname, lastname, "reader");
    }
}
