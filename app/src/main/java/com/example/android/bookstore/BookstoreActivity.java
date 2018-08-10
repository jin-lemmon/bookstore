package com.example.android.bookstore;

import android.app.LoaderManager;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.CursorLoader;
import android.content.Intent;
import android.content.Loader;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;

import com.example.android.bookstore.data.BookContract.BookEntry;
import com.example.android.bookstore.data.BookDbHelper;

public class BookstoreActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<Cursor> {

    private BookDbHelper mDbHelper;
    private BookCursorAdapter mCursorAdapter;
    private static final int BOOK_STORE_LOADER = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bookstore);
        Button insertBook = findViewById(R.id.insert_book);
        insertBook.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(BookstoreActivity.this, DetailActivity.class);
                startActivity(intent);
            }
        });
        ListView bookListView = (ListView) findViewById(R.id.inventory);

        // Find and set empty view on the ListView, so that it only shows when the list has 0 items.
        View emptyView = findViewById(R.id.empty_view);
        bookListView.setEmptyView(emptyView);

        // Setup an Adapter to create a list item for each row of book data in the Cursor.
        // There is no book data yet (until the loader finishes) so pass in null for the Cursor.
        mCursorAdapter = new BookCursorAdapter(this, null);
        bookListView.setAdapter(mCursorAdapter);
        bookListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {

                Intent intent = new Intent(BookstoreActivity.this, DetailActivity.class);
                Uri bookUri = ContentUris.withAppendedId(BookEntry.CONTENT_URI, id);
                intent.setData(bookUri);
                startActivity(intent);
            }
        });
        getLoaderManager().initLoader(BOOK_STORE_LOADER, null, this);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu options from the res/menu/menu_catalog.xml file.
        // This adds menu items to the app bar.
        getMenuInflater().inflate(R.menu.menu_bookstore, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // User clicked on a menu option in the app bar overflow menu
        switch (item.getItemId()) {
            // Respond to a click on the "Insert dummy data" menu option
            case R.id.insert_the_mummy_data:
                insertMummyData();
                return true;
            // Respond to a click on the "Delete all entries" menu option
            case R.id.fahreneit451:
                fahreneit451();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }


    protected void onStart() {
        super.onStart();
    }


    private void insertMummyData() {
        ContentValues values = new ContentValues();
        values.put(BookEntry.COLUMN_BOOK_PRODUCT_NAME, "The Mummy");
        values.put(BookEntry.COLUMN_BOOK_PRICE, "120");
        values.put(BookEntry.COLUMN_BOOK_QUANTITY, "27081999");
        values.put(BookEntry.COLUMN_BOOK_SUPPLIER, "Brendan Fraser");
        values.put(BookEntry.COLUMN_BOOK_SUPPLIER_PHONE, "68669");
// Insert the new row, returning the primary key value of the new row
        Uri newUri = getContentResolver().insert(BookEntry.CONTENT_URI, values);
    }

    private void fahreneit451() {
        int rowsDeleted = getContentResolver().delete(BookEntry.CONTENT_URI, null, null);
        Log.v("CatalogActivity", rowsDeleted + " rows deleted from pet database");
    }

    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        String[] projection = {BookEntry._ID,
                BookEntry.COLUMN_BOOK_PRODUCT_NAME,
                BookEntry.COLUMN_BOOK_PRICE,
                BookEntry.COLUMN_BOOK_QUANTITY};

        return new CursorLoader(BookstoreActivity.this, BookEntry.CONTENT_URI,
                projection, null, null, null);
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor cursor) {
        mCursorAdapter.swapCursor(cursor);
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {
        mCursorAdapter.swapCursor(null);
    }
}