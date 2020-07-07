package com.ss.universitiesdirectory.Fragment;

import android.os.Bundle;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.ss.universitiesdirectory.R;
import com.ss.universitiesdirectory.RSS.Downloader;

public class CollegeFragment extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_college, container, false);

        Bundle bundle = this.getArguments();
        WebView mCollegeWebView = view.findViewById(R.id.CollageWebView);
        mCollegeWebView.setWebViewClient(new WebViewClient());

        if(bundle.getString("College") != null){
            mCollegeWebView.loadUrl(bundle.getString("College"));
        }


        return view;
    }
}