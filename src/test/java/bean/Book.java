package bean;

import musta.belmo.validation.annotation.Assertion;
import musta.belmo.validation.annotation.Validation;
import musta.belmo.validation.enumeration.Operator;

import java.util.Date;
import java.util.List;

/**
 * Created by DELL on 30/05/2018.
 */
public class Book {

    private String title;
    private String author;
    @Validation(assertion = @Assertion(operator = Operator.EQUALS, value = "20.0"))
    private int price;
    private Date publishedAt;

   // @Validation(assertion = @Assertion(operator = Operator.LENGTH, value = "2"))
    private List<String> languages;
    @Validation(assertion = @Assertion(operator = Operator.LENGTH, value = "11"))
    private int[] isbn;


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

    public List<String> getLanguages() {
        return languages;
    }

    public void setLanguages(List<String> languages) {
        this.languages = languages;
    }

    public int[] getIsbn() {
        return isbn;
    }

    public void setIsbn(int[] isbn) {
        this.isbn = isbn;
    }
}
