package com.example.nguyenhuutu.convenientmenu.view_information.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.nguyenhuutu.convenientmenu.R;

import java.util.ArrayList;
import java.util.List;


public class ListAddressAdapter extends ArrayAdapter<String> {

    private Context mContext;
    private List<String> mList = new ArrayList<>();

    public ListAddressAdapter(@NonNull Context context, ArrayList<String> list) {
        super(context, 0, list);
        mContext = context;
        mList = list;
    }

    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        // Check if an existing view is being reused, otherwise infla te the view
        String address = getItem(position);
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.item_list_address, parent, false);
        }
        // Lookup view for data population
        TextView tvName = (TextView) convertView.findViewById(R.id.item_list_address_text);
        tvName.setText(address);

        return convertView;
    }
}
