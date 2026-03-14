package uni.com.model;

import java.io.Serializable;

/**
 * Base class representing a general publication.
 *
 * <p>Contains title and year and implements Serializable.</p>
 */
public class Publication implements Serializable {
    private static final long serialVersionUID = 1L;

    private String title;
    private int year;

    //constructors
    /** Default constructor */
    public Publication() {
    }

    /**
     * Constructs a publication with specified title and year.
     *
     * @param title publication title
     * @param year publication year
     */
    public Publication(String title, int year) {
        this.title = title;
        this.year = year;
    }

    //getters
    public String getTitle() {
        return title;
    }

    public int getYear() {
        return year;
    }

    //setters
    public void setTitle(String title) {
        this.title = title;
    }


    public void setYear(int year) {
        this.year = year;
    }

    @Override
    public String toString() {
        return "Publication: " + "title='" + title + "', year=" + year;
    }
}
