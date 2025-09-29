package com.example.broadcastviewer;

import android.content.Context;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;


public class TickerLIstFragment extends Fragment {
    private ListView tickerListView;
    private String[] defaultTickers = {"NEE", "AAPL", "DIS"};

    public class TickerListFragment extends Fragment {

        private ArrayList<String> tickers;
        private ArrayAdapter<String> adapter;

        public interface OnTickerSelectedListener {
            void onTickerSelected(String ticker);
        }

        private OnTickerSelectedListener callback;

        @Override
        public void onAttach(@NonNull Context context) {
            super.onAttach(context);
            if (context instanceof OnTickerSelectedListener) {
                callback = (OnTickerSelectedListener) context;
            } else {
                throw new RuntimeException(context.toString()
                        + " must implement OnTickerSelectedListener");
            }
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            View view = inflater.inflate(R.layout.ticker_list_fragment, container, false);

            ListView listView = view.findViewById(R.id.ticker_list_view);

            tickers = new ArrayList<>();
            tickers.add("NEE");
            tickers.add("AAPL");
            tickers.add("DIS");

            adapter = new ArrayAdapter<>(
                    requireContext(),
                    android.R.layout.simple_list_item_1,
                    tickers
            );

            listView.setAdapter(adapter);

            listView.setOnItemClickListener((parent, v, position, id) -> {
                String ticker = tickers.get(position);
                callback.onTickerSelected(ticker);
            });

            return view;
        }

        /**
         * Add a new ticker symbol.
         * - If < 6 tickers → just add it.
         * - If == 6 tickers → replace the 6th ticker.
         */
        public void addTicker(String newTicker) {
            if (tickers.size() < 6) {
                tickers.add(newTicker);
            } else {
                // Replace the last entry
                tickers.set(5, newTicker);
            }
            adapter.notifyDataSetChanged();
        }
    }
    }
