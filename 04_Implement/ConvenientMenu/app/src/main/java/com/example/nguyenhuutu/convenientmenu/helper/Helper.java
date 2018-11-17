package com.example.nguyenhuutu.convenientmenu.helper;

import android.app.Activity;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Point;
import android.util.Log;
import android.view.Display;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import static java.lang.Boolean.FALSE;

public class Helper {
    public static String LocalDbName = "convenient_menu";
    public static float getOneDP() {
        float oneDP = 1;

        return oneDP;
    }

    public static Point getDisplaySize(Display display) {
        // display size in pixels
        Point size = new Point();
        display.getSize(size);

        return size;
    }





    public static SQLiteDatabase connectLocalDB(Activity activity) {
        SQLiteDatabase db = SQLiteDatabase.openDatabase(activity.getApplication().getFilesDir() + "/" + LocalDbName, null, SQLiteDatabase.CREATE_IF_NECESSARY);
        return db;
    }

    public static Boolean checkTableExists(Activity activity, SQLiteDatabase db, String tableName) {
        String query = "SELECT logined_user FROM " + LocalDbName + " WHERE type='table' AND name=" + tableName;
        Cursor mCursor = db.rawQuery(query, null);

        if (mCursor.getCount() == 0) {
            return FALSE;
        }
        else {
            return Boolean.TRUE;
        }
    }

    public static List<String> getLoginedUser(Activity activity) {
        List<String> result = new ArrayList<String>();
        SQLiteDatabase db = connectLocalDB(activity);

        db.beginTransaction();
    try {
        if (!checkTableExists(activity,db, "logined_user")) {
            // Create table
            String tableQuery = "CREATE TABLE logined_user (" +
                    "username text PRIMARY KEY," +
                    "password text," +
                    "is_restaurant BOOLEAN," +
                    "logined BOOLEAN DEFAULT(0)" +
                    ")";
            db.execSQL(tableQuery);
            db.setTransactionSuccessful();
            Toast.makeText(activity, "Create table successful", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(activity, "The table exists", Toast.LENGTH_SHORT).show();
        }
    }
    catch (Exception ex) {
        Log.e("check table: ", ex.toString());
    }

//        String query = "SELECT username, password, is_restautant FROM logined_user WHERE logined=1";
//        Cursor loginedUser = db.rawQuery(query, null);
//        db.setTransactionSuccessful();
//
//        if (loginedUser.getCount() > 0) {
//            loginedUser.moveToFirst();
//            result.add(loginedUser.getString(0).toString());
//            result.add(loginedUser.getString(1).toString());
//        }

        db.endTransaction();

        db.close();

        return result;
    }
}
