package dk.easj.anbo.retrofitbookstore;

import androidx.annotation.NonNull;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

class Book implements Serializable {
    @SerializedName("Author")
    @Expose
    private String author;
    @SerializedName("Id")
    @Expose
    private Integer id;
    @SerializedName("Price")
    @Expose
    private Double price;
    @SerializedName("Publisher")
    @Expose
    private String publisher;
    @SerializedName("Title")
    @Expose
    private String title;

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public String getPublisher() {
        return publisher;
    }

    public void setPublisher(String publisher) {
        this.publisher = publisher;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    @NonNull
    @Override
    public String toString() {
        return author + " " + title + " " + publisher + " " + price;
    }
}
