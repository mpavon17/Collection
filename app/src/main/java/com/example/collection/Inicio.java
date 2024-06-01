package com.example.collection;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebSettings;
import android.webkit.WebView;


public class Inicio extends Fragment {
    private WebView webView;
    public Inicio() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_inicio, container, false);
        webView = view.findViewById(R.id.webView);
        //WebSettings webSettings = webView.getSettings();
        webView.clearCache(true);
        //webSettings.setJavaScriptEnabled(true);
        webView.loadUrl("file:///android_asset/coollections.html");
        return view;
    }
}