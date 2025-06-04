package Class;

public class Book {
    private int id;
    private String title;
    private String author;
    private String isbn;
    private int totalcopies;
    private String category;
    private int availablecopies;

    public Book(String title, String author, String isbn){
        this.title = title;
        this.author = author;
        this.isbn = isbn;
    }

    public Book(String title, String author, String isbn, int totalcopies, String category, int availablecopies) {
        this.title = title;
        this.author = author;
        this.isbn = isbn;
        this.totalcopies = totalcopies;
        this.category = category;
        this.availablecopies = availablecopies;
    }

    public Book(int id, String title, String author, String isbn, int totalcopies, String category, int availablecopies) {
        this.id = id;
        this.title = title;
        this.author = author;
        this.isbn = isbn;
        this.totalcopies = totalcopies;
        this.category = category;
        this.availablecopies = availablecopies;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public void setIsbn(String isbn) {
        this.isbn = isbn;
    }

    public void setTotalcopies(int totalcopies) {
        this.totalcopies = totalcopies;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public void setAvailablecopies(int availablecopies) {
        this.availablecopies = availablecopies;
    }

    public int getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getAuthor() {
        return author;
    }

    public String getIsbn() {
        return isbn;
    }

    public int getTotalcopies() {
        return totalcopies;
    }

    public String getCategory() {
        return category;
    }

    public int getAvailablecopies() {
        return availablecopies;
    }
}
