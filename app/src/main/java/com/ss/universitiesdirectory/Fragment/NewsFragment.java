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

    private View view;
    private Bundle bundle;
    private ListView listView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_news, container, false);

        init();

        return view;
    }

    private void init() {
        bundle = new Bundle();
        bundle = this.getArguments();
        listView =  view.findViewById(R.id.NewsList);
        if(bundle != null){
            new Downloader(getActivity(), bundle.getString("News"), listView).execute();
        }
    }
}