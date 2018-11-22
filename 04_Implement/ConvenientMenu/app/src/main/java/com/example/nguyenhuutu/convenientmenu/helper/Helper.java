package com.example.nguyenhuutu.convenientmenu.helper;

import android.app.Activity;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Point;
import android.util.Log;
import android.view.Display;
import android.widget.Toast;

import com.android.volley.Cache;
import com.android.volley.Network;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.BasicNetwork;
import com.android.volley.toolbox.DiskBasedCache;
import com.android.volley.toolbox.HurlStack;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONObject;

import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.SocketTimeoutException;
import java.net.URI;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.net.ssl.HttpsURLConnection;

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

    public static boolean checkAvailableCustomerAccount(final Activity context, final String account) {
        boolean result = true;
        String link = "https://convenientmenu.herokuapp.com/";
        try {
            URL url = new URL(link);
            HttpsURLConnection con = (HttpsURLConnection) url.openConnection();
            Toast.makeText(context, "inited", Toast.LENGTH_SHORT).show();
            con.setRequestMethod("GET");
            Toast.makeText(context, "set method", Toast.LENGTH_SHORT).show();
            con.setReadTimeout(10000);
            con.setConnectTimeout(15000);
//            con.setDoOutput(false);
            con.setDoInput(true);
            Toast.makeText(context, "set DoOutput", Toast.LENGTH_SHORT).show();
//            OutputStreamWriter writer = new OutputStreamWriter(con.getOutputStream());
//            writer.write("account="+account);
//            writer.flush();
            Toast.makeText(context, "Connecting", Toast.LENGTH_SHORT).show();
            con.connect();
            Toast.makeText(context, "connected", Toast.LENGTH_SHORT).show();
            int code = con.getResponseCode();
            Toast.makeText(context, "continue", Toast.LENGTH_SHORT).show();
            Toast.makeText(context, "code: " + code, Toast.LENGTH_SHORT).show();
            StringBuilder sb = new StringBuilder();
            BufferedReader reader = new BufferedReader(new InputStreamReader(con.getInputStream()));
            String line;

            while ((line = reader.readLine()) != null) {
                sb.append(line);
            }
            Toast.makeText(context, sb.toString(), Toast.LENGTH_SHORT).show();
//            String postParam = "account=" + account;
//            URL obj = new URL(link);
//            HttpsURLConnection con = (HttpsURLConnection) obj.openConnection();
//            con.setRequestMethod("POST");
//            con.setRequestProperty("account", account);
//            con.setRequestProperty("User-Agent", USER_AGENT);

            // For POST only - START
//            con.setDoOutput(true);
//            OutputStream os = con.getOutputStream();
//            os.write(postParam.getBytes());
//            os.flush();
//            os.close();
            // For POST only - END

//            int responseCode = con.getResponseCode();
//            System.out.println("POST Response Code :: " + responseCode);
//
//            if (responseCode == HttpURLConnection.HTTP_OK) { //success
//                BufferedReader in = new BufferedReader(new InputStreamReader(
//                        con.getInputStream()));
//                String inputLine;
//                StringBuffer response = new StringBuffer();
//
//                while ((inputLine = in.readLine()) != null) {
//                    response.append(inputLine);
//                }
//                in.close();

                // print result
//                Log.e("response error: ",response.toString());
//            } else {
//                Toast.makeText(context, "not works", Toast.LENGTH_SHORT).show();
//            }
            //------------------
//            RequestQueue MyRequestQueue = Volley.newRequestQueue(context);
//
//            String url = "https://convenientmenu.heroku.com/check-account-available";
//            StringRequest MyStringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
//                @Override
//                public void onResponse(String response) {
//                    Toast.makeText(context, response, Toast.LENGTH_SHORT).show();
//                    //This code is executed if the server responds, whether or not the response contains data.
//                    //The String 'response' contains the server's response.
//                }
//            }, new Response.ErrorListener() { //Create an error listener to handle errors appropriately.
//                @Override
//                public void onErrorResponse(VolleyError error) {
//                    Log.e("error check inside: ", error.toString());
//                }
//            }) {
//                protected Map<String, String> getParams() {
//                    Map<String, String> MyData = new HashMap<String, String>();
//                    MyData.put("account", account); //Add the data you'd like to send to the server.
//                    return MyData;
//                }
//            };
//            MyRequestQueue.add(MyStringRequest);
        }
        catch (Exception ex) {
            Toast.makeText(context, "error: " + ex.toString(), Toast.LENGTH_SHORT).show();
        }

        return result;
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
