package com.barberapp.barberuser.view.fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.ProgressBar;

import com.barberapp.barberuser.R;
import com.barberapp.barberuser.utils.AppConstants;

import butterknife.BindView;
import butterknife.ButterKnife;


public class LoadingFragment extends Fragment {
    @BindView(R.id.webView)
    WebView webView;
    @BindView(R.id.loadingUrlView)
    ProgressBar loadingUrlView;
    private String url = "";
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_loading,container,false);
        ButterKnife.bind(this,view);
        webView.getSettings().setJavaScriptEnabled(true);
        webView.getSettings().setLoadWithOverviewMode(true);
        webView.getSettings().setUseWideViewPort(true);
        webView.getSettings().setBuiltInZoomControls(true);
        webView.getSettings().setPluginState(WebSettings.PluginState.ON);
       // webView.setWebViewClient(new WebViewClient());
        webView.setWebChromeClient(new WebChromeClient() {

            @Override
            public void onProgressChanged(WebView view, int progress) {
                loadingUrlView.setVisibility(View.VISIBLE);
                if (progress == 100) {
                    loadingUrlView.setVisibility(View.GONE);
                }
            }
        });
        try {
            url = getArguments().getString(AppConstants.EXTRA_LOADABLE_URL);
        }catch (Exception e){
            //e.printStackTrace();
            url = "https://google.com";
        }

        webView.loadUrl(url);
        return view;
    }
}
