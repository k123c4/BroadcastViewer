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

import com.example.broadcastviewer.InfoWebFragment;
import com.example.broadcastviewer.R;

public class TickerListFragment extends Fragment {
    public interface OnTickerSelectedListener {
        void onTickerSelected(String ticker);
    }

    private ListView tickerListView;
    private String[] defaultTickers = {"NEE", "AAPL", "DIS"};

    public TickerListFragment() {
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        return inflater.inflate(R.layout.ticker_list_fragment, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        tickerListView = view.findViewById(R.id.ticker_list_view);

        ArrayAdapter<String> adapter = new ArrayAdapter<>(
                requireContext(),
                android.R.layout.simple_list_item_1,
                defaultTickers
        );

        tickerListView.setAdapter(adapter);

        tickerListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String selectedTicker = defaultTickers[position];


                Bundle args = new Bundle();
                args.putString("ticker", selectedTicker);

                InfoWebFragment infoWebFragment = new InfoWebFragment();
                infoWebFragment.setArguments(args);

                requireActivity().getSupportFragmentManager().beginTransaction()
                        .replace(R.id.ticker_list_view, infoWebFragment)
                        .addToBackStack(null)
                        .commit();
            }
        });
    }
}
