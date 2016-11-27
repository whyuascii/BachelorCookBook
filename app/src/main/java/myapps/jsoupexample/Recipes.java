package myapps.jsoupexample;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

/**
 * Created by Eduardo Verde on 11/20/2016.
 */

public class Recipes {

    private DatabaseManager dbManager;
    private Context ourcontext;
    private SQLiteDatabase database;


    public Recipes(Context c) {
        ourcontext = c;
    }

    public Recipes open() throws SQLException {
        dbManager = new DatabaseManager(ourcontext);
        database = dbManager.getWritableDatabase();
        return this;
    }

    public void close() {
        dbManager.close();
    }


    //Insert Data Into table
    public void insertData(String name, String description, String items, String directions, String video){
        ContentValues cv = new ContentValues();
        cv.put(DatabaseManager.RECIPENAME,name);
        cv.put(DatabaseManager.RECIPEDESCRIPTION,description);
        cv.put(DatabaseManager.RECIPEITEMS,items);
        cv.put(DatabaseManager.RECIPEDIRECTIONS,directions);
        cv.put(DatabaseManager.VIDEO,video);
        database.insert(DatabaseManager.RECIPESTABLE,null, cv);
    }



    public Cursor readData(String itemSearch){
        if (itemSearch.equals("Default")){
            String [] titleColumn = new String[]{"rowid _id", DatabaseManager.ID, DatabaseManager.RECIPENAME};
            Cursor c = database.query(DatabaseManager.RECIPESTABLE,titleColumn, null,null,null,null,null);
            if(c != null){
                c.moveToFirst();
            }
            return c;
        }else{
            String[] titleColumn = new String[]{"rowid _id", DatabaseManager.ID, DatabaseManager.RECIPENAME};
            Cursor c = database.query(DatabaseManager.RECIPESTABLE, titleColumn, DatabaseManager.RECIPEITEMS + " LIKE ?",
                    new String[]{"%" + itemSearch + "%"}, null, null, null);
            if (c != null) {
                c.moveToFirst();
            }
            return c;
        }
    }


}
