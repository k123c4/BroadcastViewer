package com.example.broadcastviewer;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import androidx.fragment.app.Fragment;

public class InfoWebFragment extends Fragment {

    private WebView webView;

    @Override
    public View onCreateView(LayoutInflater inflater,
                             ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.info_web_fragment, container, false);
        webView = view.findViewById(R.id.webView);

        webView.setWebViewClient(new WebViewClient());

        if (getArguments() != null) {
            String ticker = getArguments().getString("ticker");
            if (ticker != null && !ticker.isEmpty()) {
                webView.loadUrl("https://seekingalpha.com/symbol/" + ticker);
                return view;
            }
        }


        webView.loadUrl("https://seekingalpha.com");
        return view;
    }

    public void updateUrl(String ticker) {
        if (webView != null && ticker != null && !ticker.isEmpty()) {
            webView.loadUrl("https://seekingalpha.com/symbol/" + ticker);
        }
    }
}
