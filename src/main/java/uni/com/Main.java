package uni.com;

import uni.com.model.Book;
import uni.com.service.Catalogue;
import uni.com.model.Publication;

public class Main {

    public static void main(String[] args) throws Exception {

        Catalogue catalogue = new Catalogue();

        Book book1 = new Book("Dune", 1965, "Frank Herbert", "Chilton Books", "Sci-Fi");

        Book book2 = new Book("1984", 1949, "George Orwell", "Secker & Warburg", "Dystopia");

        catalogue.addPublication(book1);
        catalogue.addPublication(book2);

        System.out.println(catalogue.findPublicationByTitle("Dune"));

        catalogue.removePublicationByTitle("1984");

        catalogue.saveToFile("books.txt");

        System.out.println("Saved successfully");

        catalogue = new Catalogue();

        catalogue.loadFromFile("books.txt");

        System.out.println("After loading:");

        for (Publication p : catalogue.getAllPublications()) {
            System.out.println(p);
        }
    }
}
