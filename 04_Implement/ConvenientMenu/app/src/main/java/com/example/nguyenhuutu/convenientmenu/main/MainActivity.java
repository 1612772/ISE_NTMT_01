package com.example.nguyenhuutu.convenientmenu.main;

import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.example.nguyenhuutu.convenientmenu.R;
import com.example.nguyenhuutu.convenientmenu.helper.Helper;
import com.example.nguyenhuutu.convenientmenu.homepage.fragment.HomePageFragment;
import com.example.nguyenhuutu.convenientmenu.register.fragment.SwitchRegisterFragment;

import org.json.JSONException;
import org.json.JSONObject;

import static org.json.JSONObject.NULL;

public class MainActivity extends AppCompatActivity {
    private Toolbar mToolbar;
    private NavigationView mainMenu;
    private DrawerLayout mDrawerLayout;
    public Fragment contentFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        contentFragment = new HomePageFragment();

        mToolbar= findViewById(R.id.toolbar);
        setSupportActionBar(mToolbar);

        mainMenu = findViewById(R.id.nav_view);
        mDrawerLayout = findViewById(R.id.drawer_layout);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.main_menu_icon);

        setMainMenu();
        configMainMenu();

        switchContent(contentFragment);
    }

    private void setTitle(String title) {
        getSupportActionBar().setTitle(title);
    }

    private void setMainMenu() {
        JSONObject loginedUserJson = new JSONObject();
        try{
            loginedUserJson = Helper.getLoginedUser(this);
        }
        catch(Exception ex) {

        }
        try{
            if (loginedUserJson.getBoolean("exists") == true) {
                if (loginedUserJson.getBoolean("isRest") == true) {
                    mainMenu.inflateMenu(R.menu.main_menu_restaurant_login);
                }
                else {
                    mainMenu.inflateMenu(R.menu.main_menu_customer_login);
                }
                setHeaderOfMainMenu(R.layout.nav_header_login, loginedUserJson);
            }
            else {
                mainMenu.inflateMenu(R.menu.main_menu_non_login);
                setHeaderOfMainMenu(R.layout.nav_header_non_login, loginedUserJson);
            }
        }
        catch(Exception ex){

        }
    }

    private void setHeaderOfMainMenu(int id, JSONObject data) {
        mainMenu.removeHeaderView(mainMenu.getHeaderView(0));
        mainMenu.addHeaderView(getLayoutInflater().inflate(id, null));

        if (id == R.layout.nav_header_login) {
            Toast.makeText(this, "user logined", Toast.LENGTH_SHORT).show();
        }
    }

    public void configMainMenu() {
        View headerView = mainMenu.getHeaderView(0);

        headerView.findViewById(R.id.headerMenu).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        //mainMenu.getMenu().findItem(R.id.main_menu_home).setVisible(false);
        mainMenu.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                menuItem.setChecked(false);
                switch (menuItem.getItemId()) {
                    case R.id.main_menu_home:
                        if (!(contentFragment instanceof HomePageFragment)) {
                            setTitle("Convenient Menu");
                            contentFragment = new HomePageFragment();
                            switchContent(contentFragment);
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
                        if (!(contentFragment instanceof SwitchRegisterFragment)) {
                            setTitle("Đăng ký");
                            contentFragment = new SwitchRegisterFragment();
                            switchContent(contentFragment);
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

        ActionBarDrawerToggle mToggle= new ActionBarDrawerToggle(this,mDrawerLayout,mToolbar,R.string.open,R.string.close);
        mDrawerLayout.addDrawerListener(mToggle);
        mToggle.syncState();

//        checkMenuItem();
    }

//    public void checkMenuItem() {
//        if (activity instanceof HomePage) {
//            mainMenu.setCheckedItem(0);
//        }
//        else if (activity instanceof SwitchRegister) {
//            mainMenu.setCheckedItem(R.id.main_menu_register);
//        }
//    }

    public void switchContent(Fragment frag) {
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        ft.replace(R.id.mainContent, frag);
        ft.commit();
    }
}
