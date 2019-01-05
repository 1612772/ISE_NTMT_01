package com.example.nguyenhuutu.convenientmenu.eventmanage;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.nguyenhuutu.convenientmenu.CMDB;
import com.example.nguyenhuutu.convenientmenu.CMStorage;
import com.example.nguyenhuutu.convenientmenu.Const;
import com.example.nguyenhuutu.convenientmenu.Event;
import com.example.nguyenhuutu.convenientmenu.Fragment.ListEvent;
import com.example.nguyenhuutu.convenientmenu.LoadImage;
import com.example.nguyenhuutu.convenientmenu.R;
import com.example.nguyenhuutu.convenientmenu.helper.Helper;
import com.example.nguyenhuutu.convenientmenu.helper.UserSession;
import com.example.nguyenhuutu.convenientmenu.main.MainActivity;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.Date;
import java.util.List;

public class ManageEvent extends Fragment implements AdapterView.OnItemClickListener,View.OnClickListener {
    private static String TAG="ManageEvent";
    ListView mListView;
    ImageView mAdd;
    public static ListEvent mAdapter;
    private TextView messageNoEvent;

    public ManageEvent(){

    }

    private void getData(String restAccount) {
        CMDB.db
                .collection("event")
                .whereEqualTo("rest_account", restAccount)
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            List<Event> dataList = task.getResult().toObjects(Event.class);

                            int mount = dataList.size();
                            if (mount == 0) {
                                notifyNoEvent();
                            }
                            else {
                                for (int i = 0; i < mount; i++) {
                                    final int finalI = i;
                                    CMStorage.storage.child("images/event/" + dataList.get(i).getEvent_image_files().get(0))
                                            .getDownloadUrl()
                                            .addOnSuccessListener(new OnSuccessListener<Uri>() {
                                                @Override
                                                public void onSuccess(Uri uri) {
                                                    //  dataList.get(finalI).setImageUrl(uri.toString());

                                                    Log.d(TAG, "onSuccess: uri = "+uri.toString());
                                                    try {
                                                        LoadImage loadImage = new LoadImage();
                                                        loadImage.execute(uri.toString(), finalI, Const.MANAGE_EVENT);
                                                    } catch (Exception ex) {}

                                                }
                                            });

                                }
//                                for (Event event:
//                                        dataList ) {
//                                    Log.d(TAG, "onComplete: "+event.toString());
//                                }
                                mAdapter = new ListEvent(getActivity(), R.layout.item_event, dataList);
                                mListView.setAdapter(mAdapter);
                                mListView.setOnItemClickListener(ManageEvent.this);
                            }

                        } else {
                            Toast.makeText(getContext(), "Kết nối server thất bại", Toast.LENGTH_LONG).show();
                        }
                    }
        });
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.manage_event,container,false); // add xml

        UserSession rest = Helper.getLoginedUser(getActivity());
        String restAccount = rest.getUsername();

        mListView = view.findViewById(R.id.lvEvents);
        mAdd = view.findViewById(R.id.imgAdd);
        messageNoEvent = view.findViewById(R.id.messageNoEvent);

        mAdd.setOnClickListener(this);
        getData(restAccount);

        return view;
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

    }

    private void filterEvents(List<Event> eventList) {
        Date nowDate = new Date(); // get now date

        int count = eventList.size();
        int index = 0;
        while (index < count) {
            if (eventList.get(index).getEnd_date().compareTo(nowDate) < 0) {
                eventList.remove(index);
                count--;
            } else {
                index++;
            }
        }
    }

    @Override
    public void onClick(View v) {
        if(v.getId()==R.id.imgAdd) addEvent();
    }

    public void addEvent() {
//        Activity a = getActivity();
//        if(a instanceof MainActivity) {
//            ((MainActivity)a).setTitle("Thêm sự kiện");
//            ((MainActivity)a).switchContent(new AddNewEvent());
//        }
        Intent addEventIntent = new Intent(getActivity(), AddNewEvent.class);
        startActivity(addEventIntent);
    }

    private void notifyNoEvent() {
        messageNoEvent.setVisibility(View.VISIBLE);

        ViewGroup.LayoutParams params = messageNoEvent.getLayoutParams();
        params.height = Helper.convertDpToPixel(30);
        messageNoEvent.setLayoutParams(params);
    }

    private void hideNotifyNoEvent() {
        messageNoEvent.setVisibility(View.INVISIBLE);

        ViewGroup.LayoutParams params = messageNoEvent.getLayoutParams();
        params.height = Helper.convertDpToPixel(0);
        messageNoEvent.setLayoutParams(params);
    }
}
