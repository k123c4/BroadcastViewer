package com.example.broadcastviewer;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class TrackerViewModel extends ViewModel {

    private final MutableLiveData<List<String>> tickers = new MutableLiveData<>();
    private final MutableLiveData<String> selectedTicker = new MutableLiveData<>();

    public TrackerViewModel() {
        tickers.setValue(new ArrayList<>(Arrays.asList("NEE", "AAPL", "DIS")));
        selectedTicker.setValue(null); // on start
    }

    public LiveData<List<String>> getTickers() {
        return tickers;
    }

    public LiveData<String> getSelectedTicker() {
        return selectedTicker;
    }

    public void selectTicker(String ticker) {
        selectedTicker.setValue(ticker);
    }

    // Max 6 tickers
    public void addTicker(String newTicker) {
        List<String> cur = tickers.getValue();
        if (cur == null) cur = new ArrayList<>();
        if (cur.size() < 6) cur.add(newTicker);
        else cur.set(5, newTicker);
        tickers.setValue(cur);
    }
}
