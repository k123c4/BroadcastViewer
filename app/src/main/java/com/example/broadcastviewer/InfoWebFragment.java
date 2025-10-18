package com.example.broadcastviewer;

import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

public class InfoWebFragment extends Fragment {

    private WebView webView;
    private TrackerViewModel vm;

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate layout
        View v = inflater.inflate(R.layout.info_web_fragment, container, false);
        webView = v.findViewById(R.id.webView);


        // Keep you inside the WebView
        webView.setWebViewClient(new WebViewClient());
        webView.setWebChromeClient(new WebChromeClient());

        // load start URL
        webView.loadUrl("https://seekingalpha.com");
        return v;
    }
    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        vm = new ViewModelProvider(requireActivity()).get(TrackerViewModel.class);
        vm.getSelectedTicker().observe(getViewLifecycleOwner(), ticker -> {
            if (ticker == null || ticker.trim().isEmpty()) {
                webView.loadUrl("https://seekingalpha.com");//
            } else {
                webView.loadUrl("https://seekingalpha.com/symbol/" + ticker);
            }
        });
    }
}
