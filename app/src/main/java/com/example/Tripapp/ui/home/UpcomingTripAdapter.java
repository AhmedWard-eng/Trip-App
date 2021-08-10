package com.example.Tripapp.ui.home;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.widget.PopupMenu;

import com.example.Tripapp.R;
import com.example.Tripapp.Trip;
import com.example.Tripapp.TripAppDataActivity;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class UpcomingTripAdapter extends ArrayAdapter {
    Context context;
    ArrayList<Trip> trips;
    public UpcomingTripAdapter(Context context, ArrayList<Trip> trips) {
        super(context, R.layout.upcoming_trip_design, trips);
        this.context = context;
        this.trips = trips;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        SimpleDateFormat timeFormat;
        timeFormat = new SimpleDateFormat("kk:mm aa");
        Date date = trips.get(position).getDate();
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        View view = null;
        ViewHolder viewHolder;
        if (view == null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(R.layout.upcoming_trip_design, parent, false);
            viewHolder = new ViewHolder(view);
            view.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) view.getTag();
        }
        viewHolder.getTxtTripName().setText(trips.get(position).getTitle());
        viewHolder.getTxtDate().setText(dateFormat.format(date));
        viewHolder.getTxtTime().setText(timeFormat.format(trips.get(position).getTime()));
        viewHolder.getTxtStartPoint().setText(trips.get(position).getStartPoint());
        viewHolder.getTxtEndPoint().setText(trips.get(position).getEndPoint());
        viewHolder.getButtonStart().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getContext(), "button start"+trips.get(position).getTitle(), Toast.LENGTH_SHORT).show();
            }
        });

        viewHolder.getButtonMenu().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PopupMenu popup = new PopupMenu(context, v);
                MenuInflater inflater = popup.getMenuInflater();
                inflater.inflate(R.menu.created_trip_option, popup.getMenu());
                popup.show();
                popup.setOnMenuItemClickListener(item -> {
                    int itemId = item.getItemId();
                    if (itemId == R.id.item_cancel) {
                        cancelTrip(position);
                        return true;
                    } else if (itemId == R.id.item_delete) {
                        deleteTrip(position);
                        return true;
                    } else if (itemId == R.id.item_edit) {
                        Intent intent = new Intent(context, TripAppDataActivity.class);
//                            intent.putExtra("ArrayList",  upcomingTripData);
//                            intent.putExtra("position",position);

                        context.startActivity(intent);
                        return true;
                    }
                    return false;
                });

            }
        });
        viewHolder.getButtonNotes().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getContext(), "button notes of " +trips.get(position).getTitle(), Toast.LENGTH_SHORT).show();

            }
        });
        return view;
    }

    private void cancelTrip(int position) {
        AlertDialog.Builder alert = new AlertDialog.Builder(context);
        alert.setTitle("Cancel Trip");
        alert.setMessage("Are you sure you want to cancel this Trip?");
        alert.setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {

            public void onClick(DialogInterface dialog, int which) {
                // continue with cancel

                trips.remove(position);
                notifyDataSetChanged();
            }
        });
        alert.setNegativeButton(android.R.string.no, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                // close dialog
                dialog.cancel();
            }
        });
        alert.show();
    }


    private void deleteTrip(int position) {
        AlertDialog.Builder alert = new AlertDialog.Builder(context);
        alert.setTitle("Delete Trip");
        alert.setMessage("Are you sure you want to delete this Trip?");
        alert.setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {

            public void onClick(DialogInterface dialog, int which) {
                // continue with delete
                trips.remove(position);
                notifyDataSetChanged();
            }
        });
        alert.setNegativeButton(android.R.string.no, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                // close dialog
                dialog.cancel();
            }
        });
        alert.show();
    }
}

class ViewHolder {
    View convertView;
    TextView txtTripName;
    TextView txtDate;
    TextView txtTime;
    TextView txtStartPoint;
    TextView txtEndPoint;
    TextView buttonStart;
    TextView buttonMenu;
    TextView buttonNotes;

    public TextView getTxtTripName() {
        if (txtTripName == null) {

            txtTripName = convertView.findViewById(R.id.txt_trip_name);
        }
        return txtTripName;
    }

    public ViewHolder(View convertView) {
        this.convertView = convertView;
    }

    public TextView getTxtDate() {
        if (txtDate == null) {

            txtDate = convertView.findViewById(R.id.txt_date);
        }
        return txtDate;
    }


    public TextView getTxtTime() {
        if (txtTime == null) {

            txtTime = convertView.findViewById(R.id.txt_time);
        }
        return txtTime;
    }


    public TextView getTxtStartPoint() {
        if (txtStartPoint == null) {

            txtStartPoint = convertView.findViewById(R.id.txt_start_point);
        }
        return txtStartPoint;
    }


    public TextView getTxtEndPoint() {
        if (txtEndPoint == null) {

            txtEndPoint = convertView.findViewById(R.id.txt_end_point);
        }
        return txtEndPoint;
    }


    public TextView getButtonStart() {
        if (buttonStart == null) {

            buttonStart = convertView.findViewById(R.id.btn_start);
        }
        return buttonStart;
    }

    public TextView getButtonMenu() {
        if (buttonMenu == null) {
            buttonMenu = convertView.findViewById(R.id.btn_menu);
        }
        return buttonMenu;
    }

    public TextView getButtonNotes() {
        if (buttonNotes == null) {

            buttonNotes = convertView.findViewById(R.id.btn_notes);
        }
        return buttonNotes;
    }

}
