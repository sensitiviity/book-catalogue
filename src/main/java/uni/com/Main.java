package uni.com;

import uni.com.model.Book;
import uni.com.model.Publication;
import uni.com.service.Catalogue;
import uni.com.exception.BookNotFoundException;

public class Main {
    public static void main(String[] args) {
        Catalogue catalogue = new Catalogue();
        try {
            System.out.println("=== ADD BOOKS ===");

            Book book1 = new Book("Dune", 1965, "Frank Herbert", "Chilton Books", "Sci-Fi");
            Book book2 = new Book("1984", 1949, "George Orwell", "Secker & Warburg", "Dystopia");

            catalogue.addPublication(book1);
            catalogue.addPublication(book2);

            for (Publication p : catalogue.getAllPublications()) {
                System.out.println(p);
            }

            System.out.println("\n=== SEARCH BOOK ===");

            Publication found = catalogue.findPublicationByTitle("Dune");

            if (found != null) {
                System.out.println("Found: " + found);
            } else {
                System.out.println("Book not found");
            }

            System.out.println("\n=== REMOVE BOOK ===");

            catalogue.removePublicationByTitle("Dune");

            System.out.println("Current catalogue:");

            for (Publication p : catalogue.getAllPublications()) {
                System.out.println(p);
            }

            System.out.println("\n=== TRY REMOVE NON EXISTING BOOK ===");

            try {
                catalogue.removePublicationByTitle("Harry Potter");
            } catch (BookNotFoundException e) {
                System.out.println(e.getMessage());
            }

            System.out.println("\n=== SAVE TO FILE ===");

            catalogue.saveToFile("books.dat");

            System.out.println("\n=== LOAD FROM FILE ===");

            Catalogue loadedCatalogue = new Catalogue();

            loadedCatalogue.loadFromFile("books.dat");

            System.out.println("Loaded catalogue:");

            for (Publication p : loadedCatalogue.getAllPublications()) {
                System.out.println(p);
            }

            System.out.println("\n=== SEARCH NON EXISTING BOOK ===");

            Publication notFound = loadedCatalogue.findPublicationByTitle("Lord of the Rings");

            if (notFound == null) {
                System.out.println("Book not found");
            }

        } catch (Exception e) {
            System.out.println("Unexpected error:");
        }
    }
}