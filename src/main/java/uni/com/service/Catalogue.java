package uni.com.service;

import uni.com.model.Publication;
import uni.com.exception.BookNotFoundException;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class Catalogue {
    private List<Publication> publications;

    public Catalogue() {
        publications = new ArrayList<>();
    }

    public void addPublication(Publication p) {
        if (p != null) {
            publications.add(p);
        } else {
            System.err.println("Error: cannot add null publication");
        }
    }

    public void removePublicationByTitle(String title) throws BookNotFoundException {
        if (title == null || title.trim().isEmpty()) {
            throw new BookNotFoundException("The name cannot be empty");
        }

        Publication found = findPublicationByTitle(title);
        if (found == null) {
            throw new BookNotFoundException("A book called '" + title + "' was not found");
        }

        boolean removed = publications.remove(found);
        if (!removed) {
            System.err.println("Error: failed to delete book '" + title + "'");
        }else{
            System.out.println("Book removed successfully");
        }
    }

    public Publication findPublicationByTitle(String title) {
        if (title == null || title.trim().isEmpty()) {
            return null;
        }

        String searchTitle = title.trim().toLowerCase();
        for (Publication p : publications) {
            if (p != null && p.getTitle() != null && p.getTitle().toLowerCase().equals(searchTitle)) {
                return p;
            }
        }
        return null;
    }

    public List<Publication> getAllPublications() {
        List<Publication> copy = new ArrayList<>();
        for (Publication p : publications) {
            if (p != null) {
                copy.add(p);
            }
        }
        return copy;
    }

    public void saveToFile(String filename) throws IOException {
        if (filename == null || filename.trim().isEmpty()) {
            throw new IOException("The file name cannot be empty");
        }

        try (ObjectOutputStream out = new ObjectOutputStream(Files.newOutputStream(Paths.get(filename.trim())))) {
            out.writeObject(new ArrayList<>(publications));
            System.out.println("The directory is saved to a file: " + filename);
        }
    }

    public void loadFromFile(String filename) throws IOException, ClassNotFoundException {
        if (filename == null || filename.trim().isEmpty()) {
            throw new IOException("The file name cannot be empty");
        }

        try (ObjectInputStream in = new ObjectInputStream(Files.newInputStream(Paths.get(filename.trim())))) {
            @SuppressWarnings("unchecked")
            List<Publication> loadedList = (List<Publication>) in.readObject();
            publications.clear();

            if (loadedList != null) {
                for (Publication p : loadedList) {
                    if (p != null) {
                        publications.add(p);
                    }
                }
            }

            System.out.println("Directory downloaded from file: " + filename);
        } catch (FileNotFoundException e) {
            throw new IOException("File not found: " + filename, e);
        } catch (ClassNotFoundException e) {
            throw new ClassNotFoundException("Deserialization error: " + e.getMessage(), e);
        } catch (IOException e) {
            throw new IOException("Error reading file: " + e.getMessage(), e);
        }
    }
}
