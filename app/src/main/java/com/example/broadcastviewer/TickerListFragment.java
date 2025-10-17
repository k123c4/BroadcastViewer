package com.example.broadcastviewer;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import java.util.List;

public class TickerListFragment extends Fragment {
    private TrackerViewModel vm;
    private ArrayAdapter<String> adapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.ticker_list_fragment, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        // Get shared ViewModel from the activity
        vm = new ViewModelProvider(requireActivity()).get(TrackerViewModel.class);

        ListView list = view.findViewById(R.id.ticker_list_view);
        adapter = new ArrayAdapter<>(requireContext(), android.R.layout.simple_list_item_1);
        list.setAdapter(adapter);

        // Observe the ticker list in ViewModel
        vm.getTickers().observe(getViewLifecycleOwner(), symbols -> {
            adapter.clear();
            if (symbols != null) adapter.addAll(symbols);
            adapter.notifyDataSetChanged();
        });


        list.setOnItemClickListener((parent, row, position, id) -> {
            List<String> symbols = vm.getTickers().getValue();
            if (symbols != null && position >= 0 && position < symbols.size()) {
                String selected = symbols.get(position);
                vm.selectTicker(selected); // tells the ViewModel which ticker was clicked
            }
        });
    }
}
