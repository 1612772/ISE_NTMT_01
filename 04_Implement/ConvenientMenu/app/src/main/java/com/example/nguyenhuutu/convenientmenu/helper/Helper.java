package com.example.nguyenhuutu.convenientmenu.helper;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Point;
import android.util.Log;
import android.view.Display;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import static com.google.android.gms.flags.impl.SharedPreferencesFactory.getSharedPreferences;
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

//    public static SQLiteDatabase connectLocalDB(Activity activity) {
//        SQLiteDatabase db = SQLiteDatabase.openDatabase(activity.getApplication().getFilesDir() + "/" + LocalDbName, null, SQLiteDatabase.CREATE_IF_NECESSARY);
//        return db;
//    }
//
//    public static Boolean checkTableExists(Activity activity, SQLiteDatabase db, String tableName) {
//        String query = "SELECT logined_user FROM " + LocalDbName + " WHERE type='table' AND name=" + tableName;
//        Cursor mCursor = db.rawQuery(query, null);
//
//        if (mCursor.getCount() == 0) {
//            return FALSE;
//        }
//        else {
//            return Boolean.TRUE;
//        }
//    }

    public static JSONObject getLoginedUser(Activity activity) {
        JSONObject result = new JSONObject();

        SharedPreferences pref = activity.getSharedPreferences("recent_logined_user", Context.MODE_PRIVATE);

        if (pref.contains("usersString")) {
            try {
                JSONObject user;
                JSONArray usersArr = new JSONArray(pref.getString("usersString","[]").toString());
                int count = usersArr.length();
                boolean flag = false;
                for (int index = 0; index < count; index++) {
                    user = usersArr.getJSONObject(index);
                    if (user.getBoolean("logined") == true) {
                        flag = true;
                        result.put("exists", true);
                        result.put("username", user.getString("username"));
                        result.put("password", user.getString("password"));
                        result.put("isRest", user.getBoolean("isRest"));
                        break;
                    }
                }

                if (flag == false) {
                    result.put("exists", false);
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        else {
            try {
                result.put("exists", false);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

        return result;
    }
}
