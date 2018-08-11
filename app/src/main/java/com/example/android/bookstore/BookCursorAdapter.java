package com.example.android.bookstore;

import android.content.ContentUris;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CursorAdapter;
import android.widget.TextView;
import android.widget.Toast;

import static com.example.android.bookstore.data.BookContract.BookEntry.COLUMN_BOOK_PRICE;
import static com.example.android.bookstore.data.BookContract.BookEntry.COLUMN_BOOK_PRODUCT_NAME;
import static com.example.android.bookstore.data.BookContract.BookEntry.COLUMN_BOOK_QUANTITY;
import static com.example.android.bookstore.data.BookContract.BookEntry.CONTENT_URI;
import static com.example.android.bookstore.data.BookContract.BookEntry._ID;


public class BookCursorAdapter extends CursorAdapter {


    public BookCursorAdapter(Context context, Cursor c) {
        super(context, c, 0);
    }

    /**
     * Makes a new blank list item view. No data is set (or bound) to the views yet.
     *
     * @param context app context
     * @param cursor  The cursor from which to get the data. The cursor is already
     *                moved to the correct position.
     * @param parent  The parent to which the new view is attached to
     * @return the newly created list item view.
     */
    @Override
    public View newView(Context context, Cursor cursor, ViewGroup parent) {
        return LayoutInflater.from(context).inflate(R.layout.book_item, parent, false);

    }

    /**
     * This method binds the book data (in the current row pointed to by cursor) to the given
     * list item layout. For example, the name for the current book can be set on the name TextView
     * in the list item layout.
     *
     * @param view    Existing view, returned earlier by newView() method
     * @param context app context
     * @param cursor  The cursor from which to get the data. The cursor is already moved to the
     *                correct row.
     */
    @Override
    public void bindView(View view, final Context context, Cursor cursor) {
        final int bookQuantity = cursor.getInt(cursor.getColumnIndex(COLUMN_BOOK_QUANTITY));
        final int bookId = cursor.getInt(cursor.getColumnIndex(_ID));
        TextView bookNameTextView = view.findViewById(R.id.book_name);
        TextView priceTextView = view.findViewById(R.id.book_price);
        TextView quantityTextView = view.findViewById(R.id.book_quantity);
        Button sell = view.findViewById(R.id.sell_button);
        sell.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (bookQuantity > 0) {
                    int newQuantity = bookQuantity - 1;
                    Uri quantityUri = ContentUris.withAppendedId(CONTENT_URI, bookId);
                    ContentValues values = new ContentValues();
                    values.put(COLUMN_BOOK_QUANTITY, newQuantity);

                    int rowUpdated = context.getContentResolver().update(quantityUri, values, null, null);
                    if (!(rowUpdated > 0)) {
                        Toast.makeText(context, R.string.sale_error, Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(context, R.string.book_sold, Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(context, R.string.re_stock, Toast.LENGTH_SHORT).show();
                }
            }
        });
        // Extract properties from cursor
        String name = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_BOOK_PRODUCT_NAME));
        String priceIs = context.getString(R.string.priceTextView);
        String price = String.format(priceIs, cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_BOOK_PRICE)));
        String quantity = context.getString(R.string.we_have) + " " + cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_BOOK_QUANTITY)) + " " + context.getString(R.string.in_store);
        if (cursor.getColumnIndexOrThrow(COLUMN_BOOK_QUANTITY) == 0) {
            quantity = context.getString(R.string.don_t_have);
        }
        // Populate fields with extracted properties
        bookNameTextView.setText(name);
        priceTextView.setText(String.valueOf(price));
        quantityTextView.setText(quantity);
    }
}