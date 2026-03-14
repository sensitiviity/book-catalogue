package uni.com.model;

public class Book extends Publication {
    private String author;
    private String publisher;
    private String genre;

    //constructors
    public Book() {
    }

    public Book(String title, int year, String author, String publisher, String genre) {
        super(title, year);
        this.author = author;
        this.publisher = publisher;
        this.genre = genre;
    }

    //getters
    public String getAuthor() {
        return author;
    }

    public String getPublisher() {
        return publisher;
    }

    public String getGenre() {
        return genre;
    }

    //setters
    public void setAuthor(String author) {
        this.author = author;
    }

    public void setPublisher(String publisher) {
        this.publisher = publisher;
    }

    public void setGenre(String genre) {
        this.genre = genre;
    }

    @Override
    public String toString() {
        return "Book: " + "title='" + getTitle() + "', year=" + getYear() + ", author='" + author + "', publisher='" + publisher + "', genre='" + genre + "'";
    }
}
