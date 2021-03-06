package bean;

import io.github.belmomusta.validation.annotation.Validation;
import io.github.belmomusta.validation.enumeration.Operator;

import java.util.Date;
import java.util.List;
import java.util.Set;

/**
 * Created by DELL on 30/05/2018.
 */
public class BookDest {

    private String title;
    private String author;
    @Validation(operator = Operator.EQUALS, value = "20.0")
    private int price;
    private Date publishedAt;

    @Validation(operator = Operator.LENGTH, value = "2")
    private List<String> languages;
    @Validation(operator = Operator.LENGTH, value = "11")
    private int[] isbn;
    @Validation(operator = Operator.LENGTH, value = "3")
    private Set<String> keywords;


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

    public Set<String> getKeywords() {
        return keywords;
    }

    public void setKeywords(Set<String> keywords) {
        this.keywords = keywords;
    }
}
