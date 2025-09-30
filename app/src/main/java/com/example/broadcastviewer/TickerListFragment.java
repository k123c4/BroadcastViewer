package com.example.broadcastviewer;

import android.os.Bundle;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;


public class TickerListFragment extends Fragment {
    public interface OnTickerSelectedListener {
        void onTickerSelected(String ticker);
    }

    private ListView tickerListView;
    private String[] defaultTickers = {"NEE", "AAPL", "DIS"};

    public TickerListFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        return inflater.inflate(R.layout.ticker_list_fragment, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        tickerListView = view.findViewById(R.id.ticker_list_view);

        ArrayAdapter<String> adapter = new ArrayAdapter<>(
                requireContext(),
                android.R.layout.simple_list_item_1,
                defaultTickers
        );

        tickerListView.setAdapter(adapter);

        tickerListView.setOnItemClickListener((parent, view1, position, id) -> {
            String selectedTicker = defaultTickers[position];

            Bundle args = new Bundle();
            args.putString("ticker", selectedTicker);

            InfoWebFragment infoWebFragment = new InfoWebFragment();
            infoWebFragment.setArguments(args);

            requireActivity().getSupportFragmentManager().beginTransaction()
                    .replace(R.id.InfoWebFragment, infoWebFragment)
                    .addToBackStack(null)
                    .commit();
        });

    }
}
