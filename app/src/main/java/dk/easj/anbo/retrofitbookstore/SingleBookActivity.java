package dk.easj.anbo.retrofitbookstore;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

public class SingleBookActivity extends AppCompatActivity {
    public static final String BOOK = "BOOK";
    private static final String LOG_TAG = "MYBOOKS";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_single_book);
        Intent intent = getIntent();
        Book book = (Book) intent.getSerializableExtra(BOOK);

        Log.d(LOG_TAG, book.toString());
        TextView headingView = findViewById(R.id.book_heading_textview);
        headingView.setText("Book Id=" + book.getId());

        EditText titleView = findViewById(R.id.book_title_edittext);
        titleView.setText(book.getTitle());

        EditText authorView = findViewById(R.id.book_author_edittext);
        authorView.setText(book.getAuthor());

        EditText publisherView = findViewById(R.id.book_publisher_edittext);
        publisherView.setText(book.getPublisher());

        EditText priceView = findViewById(R.id.book_price_edittext);
        priceView.setText(Double.toString(book.getPrice()));
    }

    public void backButtonClicked(View view) {
        finish();
    }

    public void deleteBookButtonClicked(View view) {
        // TODO DELETE Retrofit
    }

    public void updateBookButtonClicked(View view) {
        // TODO UPDATE Retrofit
    }
}
