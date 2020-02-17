package dk.easj.anbo.retrofitbookstore;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SingleBookActivity extends AppCompatActivity {
    public static final String BOOK = "BOOK";
    private static final String LOG_TAG = "MYBOOKS";
    private Book book;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_single_book);
        Intent intent = getIntent();
        book = (Book) intent.getSerializableExtra(BOOK);

        Log.d(LOG_TAG, book.toString());
        TextView headingView = findViewById(R.id.book_heading_textview);
        headingView.setText("Book Id=" + book.getId());

        EditText titleView = findViewById(R.id.singleBookTitleEditText);
        titleView.setText(book.getTitle());

        EditText authorView = findViewById(R.id.singleBookAuthorEditText);
        authorView.setText(book.getAuthor());

        EditText publisherView = findViewById(R.id.singleBookPublisherEditText);
        publisherView.setText(book.getPublisher());

        EditText priceView = findViewById(R.id.singleBookPriceEditText);
        priceView.setText(Double.toString(book.getPrice()));
    }

    public void backButtonClicked(View view) {
        Log.d(LOG_TAG, "backButtonClicked");
        finish();
    }

    public void deleteBookButtonClicked(View view) {
        Log.d(LOG_TAG, "deleteBookButtonClicked");
        BookStoreService bookStoreService = ApiUtils.getBookStoreService();
        Log.d(LOG_TAG, "bookStoreService");
        int bookId = book.getId();
        Log.d(LOG_TAG, "bookId " + bookId);
        Call<Book> deleteBookCall = bookStoreService.deleteBook(bookId);
        Log.d(LOG_TAG, "deleteBookCall");
        /*try {
            Thread.sleep(20000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }*/
        deleteBookCall.enqueue(new Callback<Book>() {
            @Override
            public void onResponse(Call<Book> call, Response<Book> response) {
                if (response.isSuccessful()) {
                    //Snackbar.make(view, "Book deleted, id: " + book.getId(), Snackbar.LENGTH_LONG).show();
                    String message = "Book deleted, id: " + book.getId();
                    Toast.makeText(getBaseContext(), message, Toast.LENGTH_SHORT).show();
                    Log.d(LOG_TAG, message);
                } else {
                    //Snackbar.make(view, "Problem: " + response.code() + " " + response.message(), Snackbar.LENGTH_LONG).show();
                    TextView messageView = findViewById(R.id.singleBookMessageTextView);
                    String problem = call.request().url() + "\n" + response.code() + " " + response.message();
                    messageView.setText(problem);
                    //Toast.makeText(getBaseContext(), problem, Toast.LENGTH_SHORT).show();
                    Log.e(LOG_TAG, problem);
                }

            }

            @Override
            public void onFailure(Call<Book> call, Throwable t) {
                //Snackbar.make(view, "Problem: " + t.getMessage(), Snackbar.LENGTH_LONG).show();
                Log.e(LOG_TAG, "Problem: " + t.getMessage());
            }
        });

    }

    public void updateBookButtonClicked(View view) {
        Log.d(LOG_TAG, "updateBookButtonClicked");
        // TODO UPDATE Retrofit

        EditText authorField = findViewById(R.id.singleBookAuthorEditText);
        EditText titleField = findViewById(R.id.singleBookTitleEditText);
        EditText publisherField = findViewById(R.id.singleBookPublisherEditText);
        EditText priceField = findViewById(R.id.singleBookPriceEditText);

        String author = authorField.getText().toString().trim();
        // TODO check if author is empty string?
        String title = titleField.getText().toString().trim();
        String publisher = publisherField.getText().toString().trim();
        String priceString = priceField.getText().toString().trim();

        double price = 0.0;
        try {
            price = Double.parseDouble(priceString);
        } catch (NumberFormatException ex) {
            priceField.setError("Not a valid price");
            return;
        }
        Book book = new Book();
        book.setAuthor(author);
        book.setTitle(title);
        book.setPublisher(publisher);
        book.setPrice(price);
        Log.d(LOG_TAG, "Update " + book);
    }
}
