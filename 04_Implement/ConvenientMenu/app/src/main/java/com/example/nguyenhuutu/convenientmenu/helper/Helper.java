package com.example.nguyenhuutu.convenientmenu.helper;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Point;
import android.view.Display;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import static org.json.JSONObject.NULL;

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
        // init
        try {
            result.put("logined", false);
            result.put("username", "");
            result.put("password", "");
            result.put("isRest", false);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        SharedPreferences pref = activity.getSharedPreferences("recent_logined_user", Context.MODE_PRIVATE);

        if (pref.contains("logined_user")) {
            try {
                result = new JSONObject(pref.getString("logined_user", "").toString());
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

        return result;
    }

    public static void setSampleUserInLocal(Activity activity) {
        SharedPreferences pref = activity.getSharedPreferences("recent_logined_user", Context.MODE_PRIVATE);
        JSONObject user = new JSONObject();
        try {
            user.put("logined", true);
            user.put("username", "tunh");
            user.put("password", "Nht13101997");
            user.put("isRest", false);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        SharedPreferences.Editor editor = pref.edit();
        editor.putString("logined_user", user.toString());
        editor.commit();
    }
}
