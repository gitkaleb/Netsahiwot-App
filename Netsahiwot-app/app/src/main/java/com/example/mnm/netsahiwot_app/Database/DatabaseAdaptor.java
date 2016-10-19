package com.example.mnm.netsahiwot_app.Database;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.Toast;

/**
 * Created by kzone on 10/5/2016.
 */

public class DatabaseAdaptor {
    DatabaseHelper helper;
    private static final String TABLE_NAME = "_newsTableName";

    public DatabaseAdaptor(Context context) {
        helper = new DatabaseHelper(context);
    }

    /**GET SINGLE ROW**/

    public Cursor getRowByID(String Title) {

        final String NEWSTitle = "_title";

        SQLiteDatabase db = helper.getReadableDatabase();
        Cursor c = db.rawQuery("SELECT * FROM " + TABLE_NAME + " WHERE " + NEWSTitle + " = '" + Title + "'", null);
        if (c != null) {
            c.moveToFirst();
        }
        return c;

    }

    /**INSERT DATA FROM JSON **/


    public long insertData(String ID, String TITLE, String NOTE , String ImageLoction , String ImageUrl) {

        SQLiteDatabase db = helper.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(DatabaseHelper.ID, ID);
        contentValues.put(DatabaseHelper.TITLE,TITLE);
        contentValues.put(DatabaseHelper. NOTE,NOTE);
        contentValues.put(DatabaseHelper.ImageLoction, ImageLoction);
        contentValues.put(DatabaseHelper.ImageUrl, ImageUrl);

        long id = db.insert(DatabaseHelper.TABLE_NAME, null, contentValues);
        return id;
    }


    /**Delete from database**/
    public long deleteData(String Id) {

        SQLiteDatabase db = helper.getWritableDatabase();

        long id= db.delete(TABLE_NAME, "_id=?", new String[] {Id});
        return id;


    }

    /**Update from database**/
    public long updateData(String ID, String TITLE, String NOTE , String ImageLoction , String ImageUrl) {
        SQLiteDatabase db = helper.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(DatabaseHelper.ID, ID);
        contentValues.put(DatabaseHelper.TITLE, TITLE);
        contentValues.put(DatabaseHelper.NOTE, NOTE);
        contentValues.put(DatabaseHelper.ImageLoction, ImageLoction);
        contentValues.put(DatabaseHelper.ImageUrl, ImageUrl);


        long id= db.update(TABLE_NAME, contentValues, "_id = ?", new String[]{ID});
        return id;



    }

    /**look for specific image name**/

    public Cursor getID(String rowID) {
        String ID = "_id";
        SQLiteDatabase db = helper.getReadableDatabase();
        Cursor c = db.rawQuery("SELECT * FROM " + TABLE_NAME + " WHERE " + ID + " = '" + rowID + "'", null);
        if (c != null) {
            c.moveToFirst();
        }
        return c;

    }












    /**GET ALL ROWS**/

    public Cursor getAll(){

        //String TABLE_NAME = "CATEGORIES";
        SQLiteDatabase db = helper.getReadableDatabase();
        Cursor c = db.rawQuery("select * from _newsTableName",null);
        return c;

    }

    public static class DatabaseHelper extends SQLiteOpenHelper {
        private static final String DATABASE_NAME = "NetsaHiwot";

        private static final Integer DATABASE_VERSION = 15;

        private static final String TABLE_NAME = "_newsTableName";
        private static final String ID = "_id";
        private static final String TITLE = "_title";
        private static final String NOTE = "_news";
        private static final String ImageLoction="_imagesLocation";
        private static final String ImageUrl="_ImageUrl";

        private static final String CREATE_TABLE = "CREATE TABLE " + TABLE_NAME + "(" +ID+ " VARCHAR(255) PRIMARY KEY , " + TITLE + " VARCHAR(255)," + NOTE +" VARCHAR(255)," + ImageLoction + " VARCHAR(255),"+ ImageUrl + " VARCHAR(255));";
        private static final String DROPE_TABLE = "DROP TABLE IF EXISTS " + TABLE_NAME;
        private Context context;

        public DatabaseHelper(Context context) {
            super(context, DATABASE_NAME, null, DATABASE_VERSION);
            this.context = context;
        }

        /**CREATE TABLE**/

        @Override
        public void onCreate(SQLiteDatabase db) {
            try {
                db.execSQL(CREATE_TABLE);
                Toast.makeText(context, "Database Created !", Toast.LENGTH_LONG).show();


                //Message.message(context, "create");
            } catch (SQLException e) {
                //  Message.message(context, "" + e);
            }
        }

        /**UPGRADE TABLE**/

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
            try {
                db.execSQL(DROPE_TABLE);
                onCreate(db);
                // Message.message(context, "upgrade");
            } catch (SQLException e) {
                e.printStackTrace();
                // Message.message(context, "upgrade" + e);
            }

        }


    }
}
