package dk.easj.anbo.retrofitbookstore;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AddBookActivity extends AppCompatActivity {
    private static final String LOG_TAG = "MYBOOKS";
    private ProgressBar progressBar;
    private TextView messageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_book);
        progressBar = findViewById(R.id.addBookProgressbar);
        messageView = findViewById(R.id.addBookMessageTextView);
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

        BookStoreService bookStoreService = ApiUtils.getBookStoreService();

        // Call<Book> saveBookCall = bookStoreService.saveBook(author, title, publisher, price);
        Book book = new Book(author, title, publisher, price);

        Call<Book> saveBookCall = bookStoreService.saveBookBody(book);
        progressBar.setVisibility(View.VISIBLE);
        saveBookCall.enqueue(new Callback<Book>() {
            @Override
            public void onResponse(Call<Book> call, Response<Book> response) {
                progressBar.setVisibility(View.INVISIBLE);
                if (response.isSuccessful()) {
                    Book theNewBook = response.body();
                    Log.d(LOG_TAG, theNewBook.toString());
                    Toast.makeText(AddBookActivity.this, "Book added, id: " + theNewBook.getId(), Toast.LENGTH_SHORT).show();
//                    Snackbar.make(view, "Book added, id: " + theNewBook.getId(), Snackbar.LENGTH_LONG).show();
                } else {
                    String problem = "Problem: " + response.code() + " " + response.message();
                    Log.e(LOG_TAG, problem);
                    messageView.setText("Problem");
                    //Toast.makeText(AddBookActivity.this, problem, Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<Book> call, Throwable t) {
                progressBar.setVisibility(View.INVISIBLE);
                messageView.setText(t.getMessage());
                Log.e(LOG_TAG, t.getMessage());
            }
        });
    }
}
