package com.example.android.bookstore.data;

public class BookContract {
    private BookContract() {
    }

    public static final class BookEntry{
        public static final String TABLE_NAME = "books";
        public static final String _ID = "_id";
        public static final String COLUMN_BOOK_PRODUCT_NAME = "name";
        public static final String COLUMN_BOOK_PRICE = "price";
        public static final String COLUMN_BOOK_QUANTITY = "quantity";
        public static final String COLUMN_BOOK_SUPPLIER= "supplier";
        public static final String COLUMN_BOOK_SUPPLIER_PHONE = "phone";
    }
}

