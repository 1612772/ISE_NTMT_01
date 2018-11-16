package com.example.nguyenhuutu.convenientmenu.homepage;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.HorizontalScrollView;
import android.support.v7.widget.Toolbar;
import android.widget.Toast;

import com.example.nguyenhuutu.convenientmenu.R;
import com.example.nguyenhuutu.convenientmenu.helper.Helper;
import com.example.nguyenhuutu.convenientmenu.register.SwitchRegister;

public class HomePage extends AppCompatActivity {

    private HorizontalScrollView hightRatingRestaurantList;
    private HorizontalScrollView muchViewedrestaurantList;
    private HorizontalScrollView hightRatingDishList;
    private HorizontalScrollView newEventList;

    private DrawerLayout mDrawerLayout;
    private ActionBarDrawerToggle mToggle;
    private Toolbar mToolbar;
    private NavigationView mainMenu;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_page);

        mToolbar= findViewById(R.id.toolbar);
        setSupportActionBar(mToolbar);

        mainMenu = findViewById(R.id.nav_view);
        mDrawerLayout = findViewById(R.id.drawer_layout);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.main_menu_icon);

        Helper.configMainMenu(this, mainMenu, mDrawerLayout, mToolbar);
//        View headerView = mainMenu.getHeaderView(0);
//        headerView.findViewById(R.id.headerMenu).setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Toast.makeText(HomePage.this, "header click", Toast.LENGTH_SHORT).show();
//            }
//        });
//        //mainMenu.getMenu().findItem(R.id.main_menu_home).setVisible(false);
//        mainMenu.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
//            @Override
//            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
//                menuItem.setChecked(true);
//                switch (menuItem.getItemId()) {
//                    case R.id.main_menu_home:
//                        break;
//                    case R.id.main_menu_restaurant_list:
//                        break;
//                    case R.id.main_menu_info_account:
//                        break;
//                    case R.id.main_menu_change_password:
//                        break;
//                    case R.id.main_menu_list_mark:
//                        break;
//                    case R.id.main_menu_manage_menu:
//                        break;
//                    case R.id.main_menu_manage_event:
//                        break;
//                    case R.id.main_menu_login:
//                        break;
//                    case R.id.main_menu_register:
//                        Intent switchRegisterIntent = new Intent(HomePage.this, SwitchRegister.class);
//                        startActivity(switchRegisterIntent);
//                        break;
//                    case R.id.main_menu_logout:
//                        break;
//                    case R.id.main_menu_setting:
//                        break;
//                }
//
//                mDrawerLayout.closeDrawers();
//                return true;
//            }
//        });
//
//        mDrawerLayout= findViewById(R.id.drawer_layout);
//        mToggle= new ActionBarDrawerToggle(this,mDrawerLayout,mToolbar,R.string.open,R.string.close);
//        mDrawerLayout.addDrawerListener(mToggle);
//        mToggle.syncState();

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item){
        Toast.makeText(this, "selected", Toast.LENGTH_SHORT).show();

        return super.onOptionsItemSelected(item);
    }
}
