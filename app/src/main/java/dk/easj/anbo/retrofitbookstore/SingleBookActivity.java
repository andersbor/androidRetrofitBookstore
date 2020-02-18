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
    private Book originalBook;
    private TextView messageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_single_book);
        messageView = findViewById(R.id.singleBookMessageTextView);

        Intent intent = getIntent();
        originalBook = (Book) intent.getSerializableExtra(BOOK);

        Log.d(LOG_TAG, originalBook.toString());
        TextView headingView = findViewById(R.id.singleBookHeadingTextview);
        headingView.setText("Book Id=" + originalBook.getId());

        EditText titleView = findViewById(R.id.singleBookTitleEditText);
        titleView.setText(originalBook.getTitle());

        EditText authorView = findViewById(R.id.singleBookAuthorEditText);
        authorView.setText(originalBook.getAuthor());

        EditText publisherView = findViewById(R.id.singleBookPublisherEditText);
        publisherView.setText(originalBook.getPublisher());

        EditText priceView = findViewById(R.id.singleBookPriceEditText);
        priceView.setText(Double.toString(originalBook.getPrice()));
    }

    public void backButtonClicked(View view) {
        Log.d(LOG_TAG, "backButtonClicked");
        finish();
    }

    public void deleteBookButtonClicked(View view) {
        Log.d(LOG_TAG, "deleteBookButtonClicked");
        BookStoreService bookStoreService = ApiUtils.getBookStoreService();
        Log.d(LOG_TAG, "bookStoreService");
        int bookId = originalBook.getId();
        Log.d(LOG_TAG, "bookId " + bookId);
        Call<Book> deleteBookCall = bookStoreService.deleteBook(bookId);
        Log.d(LOG_TAG, "deleteBookCall");

        deleteBookCall.enqueue(new Callback<Book>() {
            @Override
            public void onResponse(Call<Book> call, Response<Book> response) {
                if (response.isSuccessful()) {
                    //Snackbar.make(view, "Book deleted, id: " + originalBook.getId(), Snackbar.LENGTH_LONG).show();
                    String message = "Book deleted, id: " + originalBook.getId();
                    Toast.makeText(getBaseContext(), message, Toast.LENGTH_SHORT).show();
                    Log.d(LOG_TAG, message);
                } else {
                    //Snackbar.make(view, "Problem: " + response.code() + " " + response.message(), Snackbar.LENGTH_LONG).show();
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

    public void updateBookButtonClick(View view) {
        Log.d(LOG_TAG, "updateBookButtonClicked");

    }


    public void updateBookButtonClicked(View view) {
        Log.d(LOG_TAG, "updateBookButtonClicked");
        Toast.makeText(this, "updateBookButtonClicked", Toast.LENGTH_SHORT).show();
    }

    public void anotherButtonClicked(View view) {
        Log.d(LOG_TAG, "anotherButtonClicked");
        Toast.makeText(this, "anotherButtonClicked", Toast.LENGTH_SHORT).show();

        EditText authorField = findViewById(R.id.singleBookAuthorEditText);
        EditText titleField = findViewById(R.id.singleBookTitleEditText);
        EditText publisherField = findViewById(R.id.singleBookPublisherEditText);
        EditText priceField = findViewById(R.id.singleBookPriceEditText);
        // REST bug: price cannot be updated!

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
        Book bookToUpdate = new Book(author, title, publisher, price);
        Log.d(LOG_TAG, "Update " + bookToUpdate);

        BookStoreService bookStoreService = ApiUtils.getBookStoreService();
        Call<Book> callUpdate = bookStoreService.updateBook(originalBook.getId(), bookToUpdate);
        callUpdate.enqueue(new Callback<Book>() {
            @Override
            public void onResponse(Call<Book> call, Response<Book> response) {
                if (response.isSuccessful()) {
                    Log.d(LOG_TAG, response.body().toString());
                    messageView.setText("Updated " + response.body().toString());
                } else {
                    messageView.setText("Problem: " + response.code() + " " + response.message());
                }
            }

            @Override
            public void onFailure(Call<Book> call, Throwable t) {
                messageView.setText("Problem: " + t.getMessage());
            }
        });
    }
}
