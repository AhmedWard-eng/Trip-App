package com.example.Tripapp.ui.home;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.widget.PopupMenu;

import com.example.Tripapp.Data.Alarm;
import com.example.Tripapp.MainActivity;
import com.example.Tripapp.Notes.AddNotes;
import com.example.Tripapp.Notes.ShowAllNotes;
import com.example.Tripapp.R;
import com.example.Tripapp.Trip;
import com.example.Tripapp.TripAppDataActivity;
import com.example.Tripapp.services.FloatingWidgetService;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

public class UpcomingTripAdapter extends ArrayAdapter {
    private Context context;
    private ArrayList<Trip> trips;


    public UpcomingTripAdapter(Context context, ArrayList<Trip> trips) {
        super(context, R.layout.upcoming_trip_design, trips);
        this.context = context;
        this.trips = trips;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View view = null;
        ViewHolder viewHolder;
        SimpleDateFormat timeFormat;
        DateFormat dateFormat;
        if (view == null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(R.layout.upcoming_trip_design, parent, false);
            viewHolder = new ViewHolder(view);
            view.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) view.getTag();
        }

        viewHolder.getTxtTripName().setText(trips.get(position).getTitle());
        viewHolder.getTxtDate().setText(trips.get(position).getDateText());
        viewHolder.getTxtTime().setText(trips.get(position).getTimeText());
        viewHolder.getTxtStartPoint().setText(trips.get(position).getStartPoint());
        viewHolder.getTxtEndPoint().setText(trips.get(position).getEndPoint());

        viewHolder.getButtonStart().setOnClickListener(new View.OnClickListener() {

            final double longitude = trips.get(position).getEndLongitude();
            final double latitude = trips.get(position).getEndLatitude();

            @Override
            public void onClick(View v) {
                Uri intentUri = Uri.parse("google.navigation:q=" + longitude + "," + latitude);
                Intent intent = new Intent(Intent.ACTION_VIEW, intentUri);
                intent.setPackage("com.google.android.apps.maps");
                if (intent.resolveActivity(context.getPackageManager()) != null) {
                    context.startActivity(intent);
                } else {
                    Toast.makeText(getContext(), "There's no app that can respond. Please, install Google Maps", Toast.LENGTH_SHORT).show();
                }

                if(MainActivity.drawOverAppsAllowed && trips.get(position).getNotes() != null){
                    Intent serviceIntent = new Intent(context, FloatingWidgetService.class);
                    serviceIntent.putExtra(TripAppDataActivity.NOTES, trips.get(position).getNotes());
                    context.startService(serviceIntent);
                }
                if(trips.get(position).getRound()){
                    viewHolder.getButtonStart().setText("Return");
                    trips.get(position).setRound(false);
                    String string = trips.get(position).getEndPoint();
                    double startLatitude = trips.get(position).getStartLatitude();
                    double startLongitude = trips.get(position).getStartLongitude();
                    trips.get(position).setEndPoint(trips.get(position).getStartPoint());
                    trips.get(position).setStartPoint(string);
                    trips.get(position).setEndLatitude(trips.get(position).getStartLatitude());
                    trips.get(position).setEndLongitude(trips.get(position).getStartLongitude());
                    MainActivity.databaseRefUpcoming.child(trips.get(position).getTripId()).setValue(trips.get(position));
                    notifyDataSetChanged();

                }else{
                    trips.get(position).setTripKind("Finished");
                    MainActivity.databaseRefHistory.child(trips.get(position).getTripId()).setValue(trips.get(position));
                    MainActivity.databaseRefUpcoming.child(trips.get(position).getTripId()).removeValue();
                    notifyDataSetChanged();
                }
                Alarm alarm = new Alarm(trips.get(position).getTripId(),trips.get(position).getAlarmId(),
                        trips.get(position).getHour(),
                        trips.get(position).getMinute(),
                        trips.get(position).getDay(),
                        trips.get(position).getMonth(),
                        trips.get(position).getYear(),
                        System.currentTimeMillis(),
                        false,
                        trips.get(position).getTitle());
                alarm.cancelAlarm(context);
            }
        });
        //
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
                        editTrip(position);
                        return true;
                    } else if (itemId == R.id.item_Notes) {
                        addNotes(position);
                        return true;
                    }
                    return false;
                });
            }
        });
        viewHolder.getButtonNotes().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, ShowAllNotes.class);
                intent.putExtra(TripAppDataActivity.NOTES, trips.get(position).getNotes());
                context.startActivity(intent);
//                showAlertDialog();
            }
        });
        return view;
    }

    private void addNotes(int position) {
        Intent intent = new Intent(context, AddNotes.class);
        intent.putExtra(TripAppDataActivity.TRIP_ID, trips.get(position).getTripId());
        intent.putExtra(TripAppDataActivity.ALARM_ID,trips.get(position).getAlarmId());
        intent.putExtra(TripAppDataActivity.TRIP_TITLE,trips.get(position).getTitle());
        intent.putExtra(TripAppDataActivity.TRIP_DATE,trips.get(position).getDateText());
        intent.putExtra(TripAppDataActivity.TRIP_TIME,trips.get(position).getTimeText());
        intent.putExtra(TripAppDataActivity.TRIP_START_POINT, trips.get(position).getStartPoint());
        intent.putExtra(TripAppDataActivity.TRIP_END_POINT, trips.get(position).getEndPoint());
        intent.putExtra(TripAppDataActivity.NOTES, trips.get(position).getNotes());
        intent.putExtra(TripAppDataActivity.IS_ROUND, trips.get(position).getRound());
        /////////////////////////////////////////////////////////////////////////////
        intent.putExtra(TripAppDataActivity.MONTH, trips.get(position).getMonth());
        intent.putExtra(TripAppDataActivity.DAY, trips.get(position).getDay());
        intent.putExtra(TripAppDataActivity.YEAR, trips.get(position).getYear());
        intent.putExtra(TripAppDataActivity.HOUR, trips.get(position).getHour());
        intent.putExtra(TripAppDataActivity.MINUTE, trips.get(position).getMinute());
        context.startActivity(intent);
    }

//    private void showAlertDialog() {
//        Dialog dialog = new Dialog(context);
//        dialog.setContentView(R.layout.activity_show_all_notes);
//        WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
//        lp.copyFrom(dialog.getWindow().getAttributes());
//        lp.width = WindowManager.LayoutParams.FIRST_SUB_WINDOW;
//        lp.height = WindowManager.LayoutParams.FIRST_SUB_WINDOW;
//        dialog.setCanceledOnTouchOutside(true);
//        dialog.show();
//        dialog.getWindow().setAttributes(lp);
//
//    }

    private void editTrip(int position) {
        Intent intent = new Intent(context, TripAppDataActivity.class);
        intent.putExtra(TripAppDataActivity.TRIP_ID, trips.get(position).getTripId());
        intent.putExtra(TripAppDataActivity.ALARM_ID,trips.get(position).getAlarmId());
        intent.putExtra(TripAppDataActivity.TRIP_TITLE,trips.get(position).getTitle());
        intent.putExtra(TripAppDataActivity.TRIP_DATE,trips.get(position).getDateText());
        intent.putExtra(TripAppDataActivity.TRIP_TIME,trips.get(position).getTimeText());
        intent.putExtra(TripAppDataActivity.TRIP_START_POINT, trips.get(position).getStartPoint());
        intent.putExtra(TripAppDataActivity.TRIP_END_POINT, trips.get(position).getEndPoint());
        intent.putExtra(TripAppDataActivity.NOTES, trips.get(position).getNotes());
        intent.putExtra(TripAppDataActivity.IS_ROUND, trips.get(position).getRound());
        /////////////////////////////////////////////////////////////////////////////
        intent.putExtra(TripAppDataActivity.MONTH, trips.get(position).getMonth());
        intent.putExtra(TripAppDataActivity.DAY, trips.get(position).getDay());
        intent.putExtra(TripAppDataActivity.YEAR, trips.get(position).getYear());
        intent.putExtra(TripAppDataActivity.HOUR, trips.get(position).getHour());
        intent.putExtra(TripAppDataActivity.MINUTE, trips.get(position).getMinute());
        context.startActivity(intent);
    }

    private void cancelTrip(int position) {
        AlertDialog.Builder alert = new AlertDialog.Builder(context);
        alert.setTitle("Cancel Trip");
        alert.setMessage("Are you sure you want to cancel this Trip?");
        alert.setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {

            public void onClick(DialogInterface dialog, int which) {
                // continue with cancel
                Alarm alarm = new Alarm(trips.get(position).getTripId(),trips.get(position).getAlarmId(),
                        trips.get(position).getHour(),
                        trips.get(position).getMinute(),
                        trips.get(position).getDay(),
                        trips.get(position).getMonth(),
                        trips.get(position).getYear(),
                        System.currentTimeMillis(),
                        false,
                        trips.get(position).getTitle());
                alarm.cancelAlarm(context);
                trips.get(position).setTripKind("Canceled");
                MainActivity.databaseRefHistory.child(trips.get(position).getTripId()).setValue(trips.get(position));
                MainActivity.databaseRefUpcoming.child(trips.get(position).getTripId()).removeValue();
                notifyDataSetChanged();
            }
        });
        alert.setNegativeButton(android.R.string.cancel, new DialogInterface.OnClickListener() {
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
        alert.setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {

            public void onClick(DialogInterface dialog, int which) {
                // continue with delete
                Alarm alarm = new Alarm(trips.get(position).getTripId(),trips.get(position).getAlarmId(),
                        trips.get(position).getHour(),
                        trips.get(position).getMinute(),
                        trips.get(position).getDay(),
                        trips.get(position).getMonth(),
                        trips.get(position).getYear(),
                        System.currentTimeMillis(),
                        false,
                        trips.get(position).getTitle());
                alarm.cancelAlarm(context);
                trips.get(position).setTripKind("Deleted");
                MainActivity.databaseRefHistory.child(trips.get(position).getTripId()).setValue(trips.get(position));
                MainActivity.databaseRefUpcoming.child(trips.get(position).getTripId()).removeValue();
                notifyDataSetChanged();
            }
        });

        alert.setNegativeButton(android.R.string.cancel, new DialogInterface.OnClickListener() {
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
    Button buttonStart;
    TextView buttonMenu;
    ImageButton buttonNotes;

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

    public Button getButtonStart() {
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

    public ImageButton getButtonNotes() {
        if (buttonNotes == null) {

            buttonNotes = convertView.findViewById(R.id.btn_notes);
        }
        return buttonNotes;
    }

}
