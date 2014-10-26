package com.coding4lif3.contentproviderexample.database;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by federicomonaco on 10/26/14.
 */
public class DatabaseHandler extends SQLiteOpenHelper {

    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "ContentProviderExample";

    //this is a singleton
    private static DatabaseHandler mInstance;
    private final Context context;

    public static DatabaseHandler getInstance(final Context context) {
        if (mInstance == null) {
            mInstance = new DatabaseHandler(context);
        }
        return mInstance;
    }

    public DatabaseHandler(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);

        // Good idea to use process context here
        this.context = context.getApplicationContext();
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(Person.CREATE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }


    /**
     * Query the database for a row with the specified id
     * @param id
     * @return
     */
    public synchronized Person getPerson(final long id) {
        final SQLiteDatabase db = this.getReadableDatabase();
        final Cursor cursor = db.query(Person.TABLE_NAME,
                Person.FIELDS, Person.COL_ID + " IS ?",
                new String[] { String.valueOf(id) }, null, null, null, null);
        if (cursor == null || cursor.isAfterLast()) {
            return null;
        }

        Person item = null;
        if (cursor.moveToFirst()) {
            item = new Person(cursor);
        }
        cursor.close();

        return item;
    }

    /**
     * Remove person by object
     * @param person
     * @return the number of rows that were deleted. In this case it should never be anything except zero or one
     */
    public synchronized int removePerson(final Person person) {
        final SQLiteDatabase db = this.getWritableDatabase();
        final int result;
        result = db.delete(Person.TABLE_NAME,
                Person.COL_ID + " IS ?",
                new String[] { Long.toString(person.getId())});

        return result;
    }

    /**
     * Is both an insert and an update method
     * @param person
     * @return success value (true if ok, false if no)
     */
    public synchronized boolean putPerson(final Person person) {
        boolean success = false;
        int result = 0;
        final SQLiteDatabase db = this.getWritableDatabase();

        // trying update
        if (person.getId() > -1) {
            result += db.update(Person.TABLE_NAME, person.getContent(),
                    Person.COL_ID + " IS ?",
                    new String[] { String.valueOf(person.getId()) });
        }

        if (result > 0) {
            success = true;
        } else {
            // Update failed or wasn't possible, insert instead
            final long id = db.insert(Person.TABLE_NAME, null,
                    person.getContent());

            if (id > -1) {
                person.setId(id);
                success = true;
            }
        }

        return success;
    }
}
