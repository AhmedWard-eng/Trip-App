package com.example.Tripapp.ui.home;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.Tripapp.Trip;

public class UpcomingViewModel extends ViewModel {


    private MutableLiveData<Trip> tripMutableLiveData;

    public UpcomingViewModel() {
        tripMutableLiveData = new MutableLiveData<>();
    }

    public void addTrip(Trip trip) {
        tripMutableLiveData.setValue(trip);
    }

    public LiveData<Trip> getTrip() {
        return tripMutableLiveData;
    }
}
