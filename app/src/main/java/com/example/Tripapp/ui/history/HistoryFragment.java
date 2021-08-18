package com.example.Tripapp.ui.history;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.cardview.widget.CardView;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;


import com.example.Tripapp.R;
import com.example.Tripapp.Trip;
import com.example.Tripapp.trip_map.MapsFragment;
import com.example.Tripapp.trip_map.TripMapActivity;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.model.LatLng;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class HistoryFragment extends Fragment {

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_history, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState)
    {
        super.onViewCreated(view, savedInstanceState);

        // TODO load trips from Storage instead
        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat timeFormat = new SimpleDateFormat("hh:mm aa", Locale.US);
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd /MM /yyyy", Locale.US);
        ArrayList<Trip> trips = new ArrayList<>(Arrays.asList(
            new Trip("Trip", "start Point", "end point", 31.205753, 29.924526,
                dateFormat.format(calendar.getTime()),
                timeFormat.format(calendar.getTime()),
                calendar.get(Calendar.DAY_OF_MONTH), calendar.get(Calendar.MONTH),
                calendar.get(Calendar.YEAR), calendar.get(Calendar.HOUR_OF_DAY), calendar.get(Calendar.MINUTE), null),
            new Trip("Trip", "start Point", "end point", 31.205753, 29.924526,
                dateFormat.format(calendar.getTime()),
                timeFormat.format(calendar.getTime()),
                calendar.get(Calendar.DAY_OF_MONTH), calendar.get(Calendar.MONTH),
                calendar.get(Calendar.YEAR), calendar.get(Calendar.HOUR_OF_DAY), calendar.get(Calendar.MINUTE), null)));
        ListView lstView = view.findViewById(R.id.best_list);
        lstView.setAdapter(new BestAdapter (getActivity(), trips, R.color.white));
    }
}

class BestAdapter extends ArrayAdapter<Trip>
{
    private Context context;
    private int backgroundColor;

    BestAdapter(Activity context, ArrayList<Trip> items, int backgroundColor) {
        super(context, 0, items);
        this.context = context;
        this.backgroundColor = backgroundColor;
    }

    @SuppressLint("ResourceAsColor")
    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent)
    {
        View listItemView = convertView;
        ViewHolder viewHolder;
        Trip currentItem = getItem(position);
        assert currentItem != null;

        if(listItemView == null) {
            listItemView = LayoutInflater.from(getContext()).inflate(
                    R.layout.history_list_item, parent, false);
            viewHolder = new ViewHolder(listItemView);
            listItemView.setTag(viewHolder);
        }else{
            viewHolder = (ViewHolder) listItemView.getTag();
        }

        /* TODO change txtStatus textColor (red if cancelled / green if finished)
        TextView txtStatus = listItemView.findViewById(R.id.item_txt_status);
        if(currentItem.getStatus().equals("finished")){
            txtStatus.setTextColor(R.color.temp_green);
            txtStatus.setText(currentItem.getStatus());
        }else if(currentItem.getStatus().equals("cancelled")){
            txtStatus.setTextColor(R.color.temp_red);
            txtStatus.setText(currentItem.getStatus());
        }*/

        int color = ContextCompat.getColor(getContext(), backgroundColor);
        parent.setBackgroundColor(color);

        SimpleDateFormat sdFormat = new SimpleDateFormat("dd-MMM-yyy HH:mm", Locale.getDefault());
//        String date = sdFormat.format(currentItem.getDate());

        viewHolder.getTxtTitle().setText(currentItem.getTitle());
        viewHolder.getTxtStart().setText(currentItem.getStartPoint());
        viewHolder.getTxtEnd().setText(currentItem.getEndPoint());
//        viewHolder.getTxtDate().setText(date);
//        viewHolder.getTxtStatus().setText(currentItem.getStatus());

        final double longitude = currentItem.getLongitude();
        final double latitude = currentItem.getLatitude();

        viewHolder.getCard().setOnClickListener(view -> {
            MapsFragment.tripMap.moveCamera(CameraUpdateFactory.newLatLng(new LatLng(longitude, latitude)));
            MapsFragment.tripMap.animateCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(longitude, latitude), 8f));
            /*Intent intent = new Intent(getContext(), TripMapActivity.class);
            context.startActivity(intent);*/
        });

        viewHolder.getBtnDelete().setOnClickListener(view -> new AlertDialog.Builder(BestAdapter.super.getContext())
                .setTitle(R.string.delete)
                .setMessage(R.string.confirm_delete)
                .setPositiveButton(R.string.delete, (dialogInterface, i) -> {
                    // TODO delete trip
                })
                .setNegativeButton(R.string.cancel, (dialogInterface, i) -> {
                    // TODO cancel dialog
                })
                .show());

        viewHolder.getBtnReuse().setOnClickListener(view -> new AlertDialog.Builder(BestAdapter.super.getContext())
                .setTitle(R.string.reuse)
                .setMessage(R.string.confirm_reuse)
                .setPositiveButton(R.string.reuse, (dialogInterface, i) -> {
                    // TODO resuse trip
                })
                .setNegativeButton(R.string.cancel, (dialogInterface, i) -> {
                    // TODO cancel dialog
                })
                .show());

        return listItemView;
    }

    private static class ViewHolder{
        private View view;
        private CardView card;
        private TextView txtTitle, txtStart, txtEnd, txtDate, txtStatus;
        private ImageButton btnDelete, btnReuse;

        public ViewHolder(View convertView){
            view = convertView;
        }

        // TODO in case we implemented categories then each category should have its own color
        public CardView getCard(){
            if(card == null){
                card = view.findViewById(R.id.item_card);
            }
            return card;
        }

        public TextView getTxtTitle(){
            if(txtTitle == null){
                txtTitle = view.findViewById(R.id.item_txt_title);
            }
            return txtTitle;
        }

        public TextView getTxtStart(){
            if(txtStart == null){
                txtStart = view.findViewById(R.id.item_txt_start);
            }
            return txtStart;
        }

        public TextView getTxtEnd(){
            if(txtEnd == null){
                txtEnd = view.findViewById(R.id.item_txt_end);
            }
            return txtEnd;
        }

        public TextView getTxtDate(){
            if(txtDate == null){
                txtDate = view.findViewById(R.id.item_txt_date);
            }
            return txtDate;
        }

        public TextView getTxtStatus(){
            if(txtStatus == null){
                txtStatus = view.findViewById(R.id.item_txt_status);
            }
            return txtStatus;
        }

        public ImageButton getBtnDelete(){
            if(btnDelete == null){
                btnDelete = view.findViewById(R.id.item_btn_delete);
            }
            return btnDelete;
        }

        public ImageButton getBtnReuse(){
            if(btnReuse == null){
                btnReuse = view.findViewById(R.id.item_btn_reuse);
            }
            return btnReuse;
        }

    }
}