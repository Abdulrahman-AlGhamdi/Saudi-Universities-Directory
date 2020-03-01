package com.ss.universitiesdirectory.Fragment;

import android.os.Bundle;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import com.ss.universitiesdirectory.R;

public class DetailsFragment extends Fragment {

    private View view;
    private Bundle bundle;
    private TextView mAbout, mCollage;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view =  inflater.inflate(R.layout.fragment_details, container, false);
        
        init();
        setData();

        return view;
    }

    private void init() {
        bundle = new Bundle();
        mAbout = view.findViewById(R.id.AboutText);
        mCollage = view.findViewById(R.id.CollegeText);
    }

    private void setData() {
        bundle = this.getArguments();

        if(bundle != null){
            mAbout.setText(bundle.getString("About"));
            mCollage.setText(bundle.getString("Collage"));
        }
    }
}
