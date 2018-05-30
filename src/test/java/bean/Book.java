package bean;

import musta.belmo.validation.annotation.Assertion;
import musta.belmo.validation.annotation.Validation;
import musta.belmo.validation.enumeration.Operator;

import java.util.Date;

/**
 * Created by DELL on 30/05/2018.
 */
public class Book {

    private String title;
    private String author;
    @Validation(assertion = @Assertion(operator = Operator.EQUALS, value = "20.0"))
    private int price;
    private Date publishedAt;


    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public Date getPublishedAt() {
        return publishedAt;
    }

    public void setPublishedAt(Date publishedAt) {
        this.publishedAt = publishedAt;
    }
}
