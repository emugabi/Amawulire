package thinking.codebender.amawulire.models;


import java.io.Serializable;

public class Articles implements Serializable {

    String theTitle;
    String excerpt;
    String image;
    String date;
    String category;

    public Articles(String theTitle, String excerpt, String image, String date, String category) {
        this.theTitle = theTitle;
        this.excerpt = excerpt;
        this.image = image;
        this.date = date;
        this.category = category;
    }

    public String getTitle() {
        return theTitle;
    }

    public String getExcerpt() {
        return excerpt;
    }

    public String getImage() {
        return image;
    }

    public String getDate() {
        return date;
    }

    public String getCategory() {
        return category;
    }
}
