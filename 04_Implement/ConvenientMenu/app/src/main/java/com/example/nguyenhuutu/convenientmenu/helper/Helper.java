package com.example.nguyenhuutu.convenientmenu.helper;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Point;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Display;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.example.nguyenhuutu.convenientmenu.R;
import com.example.nguyenhuutu.convenientmenu.homepage.HomePage;
import com.example.nguyenhuutu.convenientmenu.register.SwitchRegister;

import java.util.ArrayList;
import java.util.List;

import static android.content.Context.MODE_PRIVATE;
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

    public static void configMainMenu(final Activity activity, final NavigationView mainMenu, final DrawerLayout mDrawerLayout, Toolbar mToolBar) {
        View headerView = mainMenu.getHeaderView(0);

        headerView.findViewById(R.id.headerMenu).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(activity, "header click", Toast.LENGTH_SHORT).show();
            }
        });

        List<String> loginedUser = getLoginedUser(activity);
//        if (loginedUser.size() == 0) {
//            mainMenu.removeHeaderView(headerView);
//            headerView = activity.getLayoutInflater().inflate(R.layout.nav_header_non_login, null);
//            mainMenu.addHeaderView(headerView);
//        }
//        else {
//
//        }

        //mainMenu.getMenu().findItem(R.id.main_menu_home).setVisible(false);
        mainMenu.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                menuItem.setChecked(false);
                switch (menuItem.getItemId()) {
                    case R.id.main_menu_home:
                        if (!(activity instanceof HomePage)) {
                            Intent homePageIntent = new Intent(activity, HomePage.class);
                            activity.startActivity(homePageIntent);
                        }
                        break;
                    case R.id.main_menu_restaurant_list:
                        break;
                    case R.id.main_menu_info_account:
                        break;
                    case R.id.main_menu_change_password:
                        break;
                    case R.id.main_menu_list_mark:
                        break;
                    case R.id.main_menu_manage_menu:
                        break;
                    case R.id.main_menu_manage_event:
                        break;
                    case R.id.main_menu_login:
                        break;
                    case R.id.main_menu_register:
                        if (!(activity instanceof SwitchRegister)) {
                            Intent switchRegisterIntent = new Intent(activity, SwitchRegister.class);
                            activity.startActivity(switchRegisterIntent);
                        }
                        break;
                    case R.id.main_menu_logout:
                        break;
                    case R.id.main_menu_setting:
                        break;
                }

                mDrawerLayout.closeDrawers();
                return true;
            }
        });

        ActionBarDrawerToggle mToggle= new ActionBarDrawerToggle(activity,mDrawerLayout,mToolBar,R.string.open,R.string.close);
        mDrawerLayout.addDrawerListener(mToggle);
        mToggle.syncState();

        checkMenuItem(activity, mainMenu);
    }

    public static void checkMenuItem(final Activity activity, NavigationView mainMenu) {
        if (activity instanceof HomePage) {
            mainMenu.setCheckedItem(0);
        }
        else if (activity instanceof SwitchRegister) {
            mainMenu.setCheckedItem(R.id.main_menu_register);
        }
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
