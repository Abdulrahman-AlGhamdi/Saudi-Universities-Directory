package com.ss.universitiesdirectory.Fragment;

import android.os.Bundle;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import com.ss.universitiesdirectory.R;

public class WebsiteFragment extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_website, container, false);

        Bundle bundle = this.getArguments();
        WebView mWebView = view.findViewById(R.id.WebsiteView);
        mWebView.setWebViewClient(new WebViewClient());
        mWebView.getSettings().setJavaScriptEnabled(true);

        if(bundle != null){
            if(bundle.getString("WebsiteShow") != null){
                mWebView.loadUrl(bundle.getString("WebsiteShow"));
            }

            if(bundle.getString("CollegeShow") != null){
                mWebView.loadUrl(bundle.getString("CollegeShow"));
            }

            if(bundle.getString("NewsShow") != null){
                mWebView.loadUrl(bundle.getString("NewsShow"));
            }
        }


        return view;
    }
}