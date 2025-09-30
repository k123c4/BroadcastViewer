package com.example.broadcastviewer;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import androidx.fragment.app.Fragment;

public class InfoWebFragment extends Fragment {

    private WebView webView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.info_web_fragment, container, false);
        webView = view.findViewById(R.id.webView);


        WebSettings settings = webView.getSettings();
        settings.setJavaScriptEnabled(true);
        webView.setWebViewClient(new WebViewClient());


        if (savedInstanceState == null) {
            loadHomePage();
        }

        return view;
    }
    public void loadHomePage() {
        if (webView != null) {
            webView.loadUrl("https://seekingalpha.com");
        }
    }


    public void updateUrl(String ticker) {
        if (webView != null) {
            String url = "https://seekingalpha.com/symbol/" + ticker;
            webView.loadUrl(url);
        }
    }
}
