package dk.easj.anbo.retrofitbookstore;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.Arrays;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {

    private static final String LOG_TAG = "MYBOOKS";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(view -> {
            //Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
            //        .setAction("Action", null).show();
            Intent intent = new Intent(MainActivity.this, AddBookActivity.class);
            startActivity(intent);
        });

        getAndShowAllBooks();
    }

    private void getAndShowAllBooks() {
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

        Call<List<Book>> getAllBooksCall = bookStoreService.getAllBooks();

        getAllBooksCall.enqueue(new Callback<List<Book>>() {
            @Override
            public void onResponse(Call<List<Book>> call, Response<List<Book>> response) {
                List<Book> allBooks = response.body();
                Log.d(LOG_TAG, allBooks.toString());
                // TODO populate RecyclerView, including click on elements
                populateRecyclerView(allBooks);
            }

            @Override
            public void onFailure(Call<List<Book>> call, Throwable t) {
                Log.e(LOG_TAG, t.getMessage());
            }
        });
    }

    private void populateRecyclerView(List<Book> allBooks) {
        RecyclerView recyclerView = findViewById(R.id.mainRecyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        RecyclerViewSimpleAdapter adapter = new RecyclerViewSimpleAdapter<>(allBooks);
        recyclerView.setAdapter(adapter);
        adapter.setOnItemClickListener(new RecyclerViewSimpleAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position, Object item) {
                Book book = (Book)item;
                Log.d(LOG_TAG, item.toString());
                Intent intent = new Intent(MainActivity.this, SingleBookActivity.class);
                intent.putExtra(SingleBookActivity.BOOK, book);
                startActivity(intent);
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
