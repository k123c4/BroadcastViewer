package com.example.broadcastviewer;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.ViewModelProvider;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if (savedInstanceState == null) {
            FragmentManager fm = getSupportFragmentManager();
            FragmentTransaction tx = fm.beginTransaction();
            tx.add(R.id.TickerListFragment, new TickerListFragment(), "listFrag");
            tx.add(R.id.InfoWebFragment, new InfoWebFragment(), "infoFrag");
            tx.commit();
        }

        if (ContextCompat.checkSelfPermission(this, Manifest.permission.RECEIVE_SMS)
                != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.RECEIVE_SMS}, 67);
        }

        // initial launch via SMS
        smsIntent(getIntent());
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        setIntent(intent);
        smsIntent(intent);
    }

    private void smsIntent(Intent intent) {
        if (intent == null) return;

        if (intent.hasExtra("INVALID_FORMAT")) {
            Toast.makeText(this, "No valid watchlist entry found", Toast.LENGTH_LONG).show();
        } else if (intent.hasExtra("INVALID_TICKER")) {
            Toast.makeText(this, "Ticker was invalid", Toast.LENGTH_LONG).show();
        } else if (intent.hasExtra("TICKER")) {
            String ticker = intent.getStringExtra("TICKER");
            if (ticker != null && !ticker.trim().isEmpty()) {
                TrackerViewModel vm = new ViewModelProvider(this).get(TrackerViewModel.class);
                vm.addTicker(ticker);
                vm.selectTicker(ticker);
            }
        }
    }
}
