package com.example.nguyenhuutu.convenientmenu.Fragment;

import android.app.Activity;
import android.content.Context;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.nguyenhuutu.convenientmenu.CMStorage;
import com.example.nguyenhuutu.convenientmenu.Event;
import com.example.nguyenhuutu.convenientmenu.R;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;

import java.util.List;

class ListEvent extends BaseAdapter {
    Context context;
    int inflat;
    List<Event> event;


    public ListEvent(Context context, int inflat, List<Event> event) {
        this.inflat = inflat;
        this.context = context;
        this.event = event;
    }

    @Override
    public int getCount() {
        return event.size();
    }

    @Override
    public Object getItem(int position) {
        return event.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = ((Activity) context).getLayoutInflater();
        final View row = inflater.inflate(inflat, null);

        // ImageView imgEvent = (ImageView)row.findViewById(R.id.imgEvent);

        TextView tvTitleEvent = (TextView) row.findViewById(R.id.tvTitleEvent);
        TextView tvTimeEvent = (TextView) row.findViewById(R.id.tvTimeEvent);
        TextView tvDescriptionEvent = (TextView) row.findViewById(R.id.tvDescriptionEvent);

        Event item = event.get(position);

        tvDescriptionEvent.setText(item.getEventContent());// Không thấy thuộc tính title
        tvTitleEvent.setText(item.getEventContent());
        tvTimeEvent.setText(item.getBeginDate().toString() + " đến " + item.getEndDate().toString());
        CMStorage.storage.child("images/event/" + item.getEventImageFiles().get(0))
                .getDownloadUrl()
                .addOnSuccessListener(new OnSuccessListener<Uri>() {
                    @Override
                    public void onSuccess(Uri uri) {
                        try {
                            Glide
                                    .with(context)
                                    .load(uri.toString())
                                    .into((ImageView) row.findViewById(R.id.imgEvent));
                        } catch (Exception ex) {
                            Toast.makeText(context, ex.toString(), Toast.LENGTH_SHORT).show();
                        }
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception exception) {
                        Toast.makeText(context, exception.toString(), Toast.LENGTH_SHORT).show();
                    }
                });
        return row;
    }
}// CustomAdapter

