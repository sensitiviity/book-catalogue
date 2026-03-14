package uni.com;

import uni.com.gui.BookGUI;

/**
 * Entry point of the application.
 *
 * <p>This class launches the graphical user interface for the book catalogue system.
 * The GUI allows users to manage books, including adding, deleting, updating, searching, and saving data to a file.</p>
 */
public class Main {
    /**
     * Application starting point.
     *
     * @param args command line arguments (not used)
     */
    public static void main(String[] args) {
       new BookGUI();
    }
}