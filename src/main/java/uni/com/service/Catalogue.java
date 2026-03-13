package uni.com.service;

import uni.com.model.Publication;

import java.io.*;
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
            System.err.println("Помилка: не можна додати null публікацію");
        }
    }

    public void removePublicationByTitle(String title) throws Exception {
        if (title == null || title.trim().isEmpty()) {
            throw new Exception("Назва не може бути порожньою");
        }

        Publication found = findPublicationByTitle(title);
        if (found == null) {
            throw new Exception(title.trim());
        }

        boolean removed = publications.remove(found);
        if (!removed) {
            System.err.println("Помилка: не вдалося видалити книгу '" + title + "'");
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
            throw new IOException("Ім'я файлу не може бути порожнім");
        }

        FileOutputStream fileOut = null;
        ObjectOutputStream out = null;
        try {
            fileOut = new FileOutputStream(filename.trim());
            out = new ObjectOutputStream(fileOut);
            out.writeObject(new ArrayList<>(publications));
            System.out.println("Каталог збережено у файл: " + filename);
        } catch (IOException e) {
            throw new IOException("Помилка збереження каталогу: " + e.getMessage(), e);
        } finally {
            try {
                if (out != null) {
                    out.close();
                }
            } catch (IOException e) {
                System.err.println("Помилка закриття ObjectOutputStream: " + e.getMessage());
            }
            try {
                if (fileOut != null) {
                    fileOut.close();
                }
            } catch (IOException e) {
                System.err.println("Помилка закриття FileOutputStream: " + e.getMessage());
            }
        }
    }

    public void loadFromFile(String filename) throws IOException, ClassNotFoundException {
        if (filename == null || filename.trim().isEmpty()) {
            throw new IOException("Ім'я файлу не може бути порожнім");
        }

        FileInputStream fileIn = null;
        ObjectInputStream in = null;
        try {
            fileIn = new FileInputStream(filename.trim());
            in = new ObjectInputStream(fileIn);
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
            System.out.println("Каталог завантажено з файлу: " + filename);
        } catch (FileNotFoundException e) {
            throw new IOException("Файл не знайдено: " + filename, e);
        } catch (ClassNotFoundException e) {
            throw new ClassNotFoundException("Помилка десеріалізації: " + e.getMessage(), e);
        } catch (IOException e) {
            throw new IOException("Помилка читання файлу: " + e.getMessage(), e);
        } finally {
            try {
                if (in != null) {
                    in.close();
                }
            } catch (IOException e) {
                System.err.println("Помилка закриття ObjectInputStream: " + e.getMessage());
            }
            try {
                if (fileIn != null) {
                    fileIn.close();
                }
            } catch (IOException e) {
                System.err.println("Помилка закриття FileInputStream: " + e.getMessage());
            }
        }
    }
}
