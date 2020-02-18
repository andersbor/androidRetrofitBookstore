package dk.easj.anbo.retrofitbookstore;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.PUT;
import retrofit2.http.Path;


public interface BookStoreService {
    @GET("books")
    Call<List<Book>> getAllBooks();

    @GET("books/{bookId}")
    Call<Book> getBookById(int bookId);

    @POST("books")
    @FormUrlEncoded
    Call<Book> saveBook(@Field("Author") String author, @Field("Title") String title,
                        @Field("Publisher") String publisher, @Field("Price") double price);

    @POST("books")
    Call<Book> saveBookBody(@Body Book book);

    @DELETE("books/{id}")
    Call<Book> deleteBook(@Path("id") int id);

    @PUT("books/{id}")
    Call<Book> updateBook(@Path("id") int id, @Body Book book);
}
