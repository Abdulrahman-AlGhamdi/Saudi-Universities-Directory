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
        View view = inflater.inflate(R.layout.fragment_college, container, false);

        Bundle bundle = this.getArguments();
        WebView mWebsiteView = view.findViewById(R.id.WebsiteView);
        mWebsiteView.setWebViewClient(new WebViewClient());
        mWebsiteView.getSettings().setJavaScriptEnabled(true);

        if(bundle.getString("Website") != null){
            mWebsiteView.loadUrl(bundle.getString("Website"));
        }

        if(bundle.getString("College") != null){
            mWebsiteView.loadUrl(bundle.getString("College"));
        }

        return view;
    }
}