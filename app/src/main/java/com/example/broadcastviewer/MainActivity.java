package com.example.broadcastviewer;

import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

public class MainActivity extends AppCompatActivity
        implements TickerListFragment.OnTickerSelectedListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        if (savedInstanceState == null) {
            FragmentManager fm = getSupportFragmentManager();
            FragmentTransaction trans = fm.beginTransaction();

            TickerListFragment listFragment = new TickerListFragment();
            InfoWebFragment infoFragment = new InfoWebFragment();
            trans.add(R.id.TickerListFragment, listFragment, "listFrag");
            trans.add(R.id.InfoWebFragment, infoFragment, "infoFrag");

            trans.commit();
        }}

    @Override
    public void onTickerSelected(String ticker) {
        InfoWebFragment infoFragment = (InfoWebFragment)
                getSupportFragmentManager().findFragmentByTag("infoFrag");

        if (infoFragment != null) {
            infoFragment.updateUrl(ticker);
        }
    }
}
