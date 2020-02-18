package dk.easj.anbo.retrofitbookstore;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AddBookActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_book);
    }

    public void addBookButtonClicked(View view) {
        EditText authorField = findViewById(R.id.addBookAuthorEditText);
        EditText titleField = findViewById(R.id.addBookTitleEditText);
        EditText publisherField = findViewById(R.id.addBookPublisherEditText);
        EditText priceField = findViewById(R.id.addBookPriceEditText);

        String author = authorField.getText().toString().trim();
        // TODO check if author is empty string?
        String title = titleField.getText().toString().trim();
        String publisher = publisherField.getText().toString().trim();
        String priceString = priceField.getText().toString().trim();

        double price;
        try {
            price = Double.parseDouble(priceString);
        } catch (NumberFormatException ex) {
            priceField.setError("Not a valid price");
            return;
        }
/*
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://anbo-restserviceproviderbooks.azurewebsites.net/Service1.svc/")
                // https://futurestud.io/tutorials/retrofit-2-adding-customizing-the-gson-converter
                // Gson is no longer the default converter
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        BookStoreService bookStoreService = retrofit.create(BookStoreService.class);
*/
        BookStoreService bookStoreService = ApiUtils.getBookStoreService();

        // Call<Book> saveBookCall = bookStoreService.saveBook(author, title, publisher, price);
        Book book = new Book(author, title, publisher, price);

        Call<Book> saveBookCall = bookStoreService.saveBookBody(book);
        saveBookCall.enqueue(new Callback<Book>() {
            @Override
            public void onResponse(Call<Book> call, Response<Book> response) {
                if (response.isSuccessful()) {
                    Book theNewBook = response.body();
                    Log.d("MYBOOKS", theNewBook.toString());
                    Toast.makeText(AddBookActivity.this, "Book added, id: " + theNewBook.getId(), Toast.LENGTH_SHORT).show();
//                    Snackbar.make(view, "Book added, id: " + theNewBook.getId(), Snackbar.LENGTH_LONG).show();
                } else {
                    String problem = "Problem: " + response.code() + " " + response.message();
                    Log.e("MYBOOKS", problem);
                    Toast.makeText(AddBookActivity.this, problem, Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<Book> call, Throwable t) {
                Log.e("MYBOOKS", t.getMessage());
            }
        });
    }
}
