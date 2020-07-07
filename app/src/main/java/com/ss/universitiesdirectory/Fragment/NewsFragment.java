package com.ss.universitiesdirectory.Fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import androidx.fragment.app.Fragment;
import com.ss.universitiesdirectory.R;
import com.ss.universitiesdirectory.RSS.Downloader;

public class NewsFragment extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_news, container, false);

        Bundle bundle = this.getArguments();
        ListView listView = view.findViewById(R.id.NewsList);

        if(bundle.getString("News") != null){
            new Downloader(getActivity(), bundle.getString("News"), listView).execute();
        }

        return view;
    }
}