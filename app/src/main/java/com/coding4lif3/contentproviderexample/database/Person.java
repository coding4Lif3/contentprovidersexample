package com.coding4lif3.contentproviderexample.database;

import android.content.ContentValues;
import android.database.Cursor;

/**
 * A class representation of a row in table "Person"
 * <p/>
 * Created by federicomonaco on 10/26/14.
 */
public class Person {

    // SQL convention says Table name should be "singular", so not Persons
    public static final String TABLE_NAME = "Person";
    // Naming the id column with an underscore is good to be consistent
    // with other Android things. This is ALWAYS needed
    public static final String COL_ID = "_id";
    // These fields can be anything you want.
    public static final String COL_FIRSTNAME = "firstname";
    public static final String COL_LASTNAME = "lastname";
    public static final String COL_BIO = "bio";

    // For database projection so order is consistent
    public static final String[] FIELDS = {COL_ID, COL_FIRSTNAME, COL_LASTNAME,
            COL_BIO};

    /*
     * The SQL code that creates a Table for storing Persons in.
     * Note that the last row does NOT end in a comma like the others.
     * This is a common source of error.
     */
    public static final String CREATE_TABLE =
            "CREATE TABLE " + TABLE_NAME + "("
                    + COL_ID + " INTEGER PRIMARY KEY,"
                    + COL_FIRSTNAME + " TEXT NOT NULL DEFAULT '',"
                    + COL_LASTNAME + " TEXT NOT NULL DEFAULT '',"
                    + COL_BIO + " TEXT NOT NULL DEFAULT ''"
                    + ")";


    // Fields corresponding to database columns (these are the person properties)
    private long id = -1;
    private String firstname = "";
    private String lastname = "";
    private String bio = "";

    /**
     * Constructor for a new Person object by passing a cursor
     * Convert information from the database into a Person object.
     * @param cursor
     */
    public Person(final Cursor cursor) {
        // Indices expected to match order in FIELDS!
        this.id = cursor.getLong(0);
        this.firstname = cursor.getString(1);
        this.lastname = cursor.getString(2);
        this.bio = cursor.getString(3);
    }

    /**
     * Return the fields in a ContentValues object, suitable for insertion
     * into the database.
     */
    public ContentValues getContent() {
        final ContentValues values = new ContentValues();
        // Note that ID is NOT included here
        values.put(COL_FIRSTNAME, firstname);
        values.put(COL_LASTNAME, lastname);
        values.put(COL_BIO, bio);

        return values;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public String getBio() {
        return bio;
    }

    public void setBio(String bio) {
        this.bio = bio;
    }

}
