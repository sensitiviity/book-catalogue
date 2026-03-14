package uni.com.gui;

import uni.com.model.Book;
import uni.com.model.Publication;
import uni.com.service.Catalogue;
import uni.com.exception.BookNotFoundException;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.io.IOException;
import java.util.List;

public class BookGUI extends JFrame {
    private Catalogue catalogue;

    private JTable table;
    private DefaultTableModel tableModel;

    private JTextField titleField, authorField, publisherField, yearField, genreField, searchField;
    private JButton addButton, deleteButton, updateButton, saveButton, loadButton, searchButton, resetSearchButton;

    public BookGUI() {
        catalogue = new Catalogue();

        initComponents();
        initLayout();
        initFrameSettings();
        addActionListeners();
        refreshTable();

        setVisible(true);
    }

    private void initComponents() {
        titleField = new JTextField(30);
        authorField = new JTextField(30);
        publisherField = new JTextField(30);
        yearField = new JTextField(5);
        genreField = new JTextField(30);
        searchField = new JTextField(30);

        addButton = new JButton("Add");
        deleteButton = new JButton("Delete");
        updateButton = new JButton("Update");
        saveButton = new JButton("Save");
        loadButton = new JButton("Load");
        searchButton = new JButton("Search");
        resetSearchButton = new JButton("Reset");

        tableModel = new DefaultTableModel(new Object[]{"Title", "Author", "Publisher", "Year", "Genre"}, 0);
        table = new JTable(tableModel);
        table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
    }

    private void initLayout() {
        JPanel mainPanel = new JPanel(new BorderLayout(10, 10));

        JPanel formPanel = new JPanel();
        formPanel.setLayout(new GridLayout(5, 2, 5, 5));
        formPanel.add(new JLabel("Title:"));
        formPanel.add(titleField);
        formPanel.add(new JLabel("Author:"));
        formPanel.add(authorField);
        formPanel.add(new JLabel("Publisher:"));
        formPanel.add(publisherField);
        formPanel.add(new JLabel("Year:"));
        formPanel.add(yearField);
        formPanel.add(new JLabel("Genre:"));
        formPanel.add(genreField);

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 10, 5));
        buttonPanel.add(addButton);
        buttonPanel.add(deleteButton);
        buttonPanel.add(updateButton);
        buttonPanel.add(saveButton);
        buttonPanel.add(loadButton);

        JPanel searchPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 5));
        searchPanel.add(new JLabel("Search by title:"));
        searchPanel.add(searchField);
        searchPanel.add(searchButton);
        searchPanel.add(resetSearchButton);

        JPanel northPanel = new JPanel(new BorderLayout(5, 5));
        northPanel.add(formPanel, BorderLayout.NORTH);
        northPanel.add(buttonPanel, BorderLayout.CENTER);
        northPanel.add(searchPanel, BorderLayout.SOUTH);

        JScrollPane scrollPane = new JScrollPane(table);
        mainPanel.add(northPanel, BorderLayout.NORTH);
        mainPanel.add(scrollPane, BorderLayout.CENTER);

        setContentPane(mainPanel);
    }

    private void initFrameSettings() {
        setTitle("Book catalogue");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(900, 600);
        setLocationRelativeTo(null);
    }

    private void addActionListeners() {
        addButton.addActionListener(e -> addBook());
        deleteButton.addActionListener(e -> removeBook());
        updateButton.addActionListener(e -> updateBook());
        saveButton.addActionListener(e -> saveToFile());
        loadButton.addActionListener(e -> loadFromFile());
        searchButton.addActionListener(e -> searchBook());
        resetSearchButton.addActionListener(e -> refreshTable());
    }

    private void refreshTable() {
        tableModel.setRowCount(0);
        List<Publication> list = catalogue.getAllPublications();
        for(Publication p : list){
            Book book = (Book) p;
            tableModel.addRow(new Object[]{book.getTitle(), book.getAuthor(), book.getPublisher(), book.getYear(), book.getGenre()});
        }
    }

    private void addBook() {
        try{
            Book book = createBookFromFields();
            catalogue.addPublication(book);
            refreshTable();
            clearFields();
        }catch(IllegalArgumentException e){
            JOptionPane.showMessageDialog(this, e.getMessage());
        }
    }

    private Book createBookFromFields() {
        String title = titleField.getText().trim();
        String author = authorField.getText().trim();
        String publisher = publisherField.getText().trim();
        String yearText = yearField.getText().trim();
        String genre = genreField.getText().trim();

        if (title.isEmpty() || author.isEmpty() || publisher.isEmpty() || genre.isEmpty() || yearText.isEmpty()) {
            throw new IllegalArgumentException("All fields must be filled");
        }

        int year;

        try {
            year = Integer.parseInt(yearText);
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("Year must be a number");
        }
        if(year < 0 || year > 2026) {
            throw new IllegalArgumentException("Year must be between 0 and 2026.");
        }

        return new Book(title, year, author, publisher, genre);
    }

    private void removeBook() {
        try{
            String title = titleField.getText().trim();
            if(!title.isEmpty()) {
                catalogue.removePublicationByTitle(title);
                refreshTable();
                clearFields();
            } else {
                JOptionPane.showMessageDialog(this, "Enter a book name to delete");
            }
        }catch(BookNotFoundException e){
            JOptionPane.showMessageDialog(this, e.getMessage());
        }
    }

    private void updateBook() {
        try {
            String title = titleField.getText().trim();
            if (title.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Enter book title");
                return;
            }

            List<Publication> list = catalogue.getAllPublications();
            boolean found = false;

            for (Publication p : list) {
                if (p.getTitle().equalsIgnoreCase(title)) {
                    Book book = (Book) p;

                    if (!authorField.getText().trim().isEmpty()) {
                        book.setAuthor(authorField.getText().trim());
                    }

                    if (!publisherField.getText().trim().isEmpty()) {
                        book.setPublisher(publisherField.getText().trim());
                    }

                    if (!yearField.getText().trim().isEmpty()) {
                        int year = Integer.parseInt(yearField.getText().trim());
                        book.setYear(year);
                    }

                    if (!genreField.getText().trim().isEmpty()) {
                        book.setGenre(genreField.getText().trim());
                    }

                    found = true;
                    break;
                }
            }

            if (!found) {
                throw new BookNotFoundException("Book not found: " + title);
            }

            refreshTable();
            clearFields();

        } catch (BookNotFoundException e) {
            JOptionPane.showMessageDialog(this, e.getMessage());
        }catch(NumberFormatException e){
            JOptionPane.showMessageDialog(this, "Year must be a number");
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Error updating book");
        }
    }

    private void saveToFile() {
        try{
            catalogue.saveToFile("books.dat");
            JOptionPane.showMessageDialog(this, "Saved successfully");
        }catch(IOException e){
            JOptionPane.showMessageDialog(this, "Error saving");
        }
    }

    private void loadFromFile() {
        try{
            catalogue.loadFromFile("books.dat");
            refreshTable();
        }catch(IOException e){
            JOptionPane.showMessageDialog(this, e.getMessage());
        }catch(Exception e){
            JOptionPane.showMessageDialog(this, "Error loading");
        }
    }

    private void searchBook(){
        String search = searchField.getText().toLowerCase();
        tableModel.setRowCount(0);
        List<Publication> list = catalogue.getAllPublications();

        for(Publication p : list){
            if(p.getTitle().toLowerCase().contains(search)){
                Book book = (Book) p;
                tableModel.addRow(new Object[]{book.getTitle(), book.getAuthor(), book.getPublisher(), book.getYear(), book.getGenre()});
            }
        }
    }

    private void clearFields() {
        titleField.setText("");
        authorField.setText("");
        publisherField.setText("");
        yearField.setText("");
        genreField.setText("");
    }
}
