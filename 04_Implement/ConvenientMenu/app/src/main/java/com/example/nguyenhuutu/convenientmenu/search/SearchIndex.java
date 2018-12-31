package com.example.nguyenhuutu.convenientmenu.search;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.widget.SearchView;

import com.example.nguyenhuutu.convenientmenu.R;

public class SearchIndex extends Activity implements SearchView.OnQueryTextListener {


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

    }

    @Override
    public boolean onQueryTextSubmit(String query) {
        return false;
    }

    @Override
    public boolean onQueryTextChange(String newText) {
        return false;
    }
}
