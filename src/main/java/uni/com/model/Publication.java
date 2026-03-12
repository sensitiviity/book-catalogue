package uni.com.model;

import java.io.Serializable;

public class Publication implements Serializable {
    private String title;
    private int year;

    //constructors
    public Publication() {
    }

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
        return "Publication{" + "title='" + title + '\'' + ", year=" + year + '}';
    }
}
