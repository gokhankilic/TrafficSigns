package com.example.trafficsigns.database;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.trafficsigns.models.Category;
import com.example.trafficsigns.models.Symbol;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;


public class Database extends SQLiteOpenHelper {
    private static String DB_NAME = "Database.db";
    private static String DB_PATH = "";
    private static final int DB_VERSION = 1;

    private SQLiteDatabase mDataBase;
    private final Context mContext;
    private boolean mNeedUpdate = false;

    public Database(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
        if (android.os.Build.VERSION.SDK_INT >= 17)
            DB_PATH = context.getApplicationInfo().dataDir + "/databases/";
        else
            DB_PATH = "/data/data/" + context.getPackageName() + "/databases/";
        this.mContext = context;

        copyDataBase();

        this.getReadableDatabase();
    }

    public void updateDataBase() throws IOException {
        if (mNeedUpdate) {
            File dbFile = new File(DB_PATH + DB_NAME);
            if (dbFile.exists())
                dbFile.delete();

            copyDataBase();

            mNeedUpdate = false;
        }
    }

    private boolean checkDataBase() {
        File dbFile = new File(DB_PATH + DB_NAME);
        return dbFile.exists();
    }

    private void copyDataBase() {
        if (!checkDataBase()) {
            this.getReadableDatabase();
            this.close();
            try {
                copyDBFile();
            } catch (IOException mIOException) {
                throw new Error("ErrorCopyingDataBase");
            }
        }
    }

    private void copyDBFile() throws IOException {
        InputStream mInput = mContext.getAssets().open(DB_NAME);
        //InputStream mInput = mContext.getResources().openRawResource(R.raw.info);
        OutputStream mOutput = new FileOutputStream(DB_PATH + DB_NAME);
        byte[] mBuffer = new byte[1024];
        int mLength;
        while ((mLength = mInput.read(mBuffer)) > 0)
            mOutput.write(mBuffer, 0, mLength);
        mOutput.flush();
        mOutput.close();
        mInput.close();

    }

    public boolean openDataBase() throws SQLException {
        mDataBase = SQLiteDatabase.openDatabase(DB_PATH + DB_NAME, null, SQLiteDatabase.CREATE_IF_NECESSARY);
        return mDataBase != null;
    }

    @Override
    public synchronized void close() {
        if (mDataBase != null)
            mDataBase.close();
        super.close();
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        if (newVersion > oldVersion)
            mNeedUpdate = true;
    }



    public ArrayList<Category> getCategories() {
        String TABLE_NAME = "Categories";

        ArrayList<Category> categories = new ArrayList();
        String selectQuery = "SELECT * FROM " + TABLE_NAME;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        //if TABLE has rows
        if (cursor.moveToFirst()) {
            //Loop through the table rows
            do {
                Category category = new Category(cursor.getInt(0),cursor.getString(1));


                categories.add(category);
            } while (cursor.moveToNext());
        }
        db.close();
        return categories;
    }

    public ArrayList<Symbol> getSymbols(int selectedCategory) {
        String TABLE_NAME = "Symbols";

        ArrayList<Symbol> symbols = new ArrayList();
        String selectQuery = "SELECT * FROM " + TABLE_NAME + " WHERE category_id="+ selectedCategory;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        //if TABLE has rows
        if (cursor.moveToFirst()) {
            //Loop through the table rows
            do {
                boolean isInList = false;

                if(cursor.getInt(5) == 1){
                    isInList = true;
                }

                Symbol Symbol = new Symbol(cursor.getInt(0),
                        cursor.getString(1),
                        cursor.getString(2),
                        cursor.getBlob(3),
                        cursor.getInt(4),
                        isInList);


                symbols.add(Symbol);

            } while (cursor.moveToNext());
        }
        db.close();
        return symbols;
    }

    public void updateSymbol(int symbolId, boolean isFavorited){

        int isSymbolFavorited;

        if(isFavorited){
            isSymbolFavorited = 1;
        }else{
            isSymbolFavorited = 0;
        }
        String selectQuery = "UPDATE Symbols SET is_in_list=" + isSymbolFavorited + " WHERE id=" + symbolId;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        //if TABLE has rows
        if (cursor.moveToFirst()) {
            //Loop through the table rows
            do {
            } while (cursor.moveToNext());
        }
        db.close();


    }


    public void updateFavoritedSymbols(int symbolId, int listOrderId){
        String selectQuery = "UPDATE Symbols SET list_order_id=" + listOrderId + " WHERE id=" + symbolId;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        //if TABLE has rows
        if (cursor.moveToFirst()) {
            //Loop through the table rows
            do {
            } while (cursor.moveToNext());
        }
        db.close();
    }


    public ArrayList<Symbol> getFavoritedSymbols() {
        String TABLE_NAME = "Symbols";

        ArrayList<Symbol> symbols = new ArrayList();
        String selectQuery = "SELECT * FROM " + TABLE_NAME + " WHERE is_in_list=1 ORDER BY list_order_id ASC;";
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        //if TABLE has rows
        if (cursor.moveToFirst()) {
            //Loop through the table rows
            do {
                boolean isInList = false;

                if(cursor.getInt(5) == 1){
                    isInList = true;
                }

                Symbol Symbol = new Symbol(cursor.getInt(0),
                        cursor.getString(1),
                        cursor.getString(2),
                        cursor.getBlob(3),
                        cursor.getInt(4),
                        isInList);


                symbols.add(Symbol);

            } while (cursor.moveToNext());
        }
        db.close();
        return symbols;
    }






}
