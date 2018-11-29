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
import com.example.nguyenhuutu.convenientmenu.R;
import com.example.nguyenhuutu.convenientmenu.homepage.fragment.HomePageFragment;
import com.example.nguyenhuutu.convenientmenu.register.fragment.SwitchRegisterFragment;

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
        mainMenu.inflateMenu(R.menu.main_menu_non_login);
        setHeaderOfMainMenu();
    }

    private void setHeaderOfMainMenu() {
        mainMenu.removeHeaderView(mainMenu.getHeaderView(0));
        mainMenu.addHeaderView(getLayoutInflater().inflate(R.layout.nav_header_non_login, null));
    }

    public void configMainMenu() {
        View headerView = mainMenu.getHeaderView(0);

        headerView.findViewById(R.id.headerMenu).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

//        List<String> loginedUser = getLoginedUser(activity);
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
