package com.ss.universitiesdirectory.Fragment;

import android.os.Bundle;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import com.squareup.picasso.Picasso;
import com.ss.universitiesdirectory.R;

public class DetailsFragment extends Fragment {

    private View view;
    private Bundle bundle;
    private ImageView mLogo;
    private Fragment fragment;
    private TextView mAbout, mCollage;
    private Button mRSS;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view =  inflater.inflate(R.layout.fragment_details, container, false);
        
        init();
        setData();
        ButtonClick();

        return view;
    }

    private void init() {
        bundle = new Bundle();
        mRSS = view.findViewById(R.id.RSS);
        mLogo = view.findViewById(R.id.Logo);
        mAbout = view.findViewById(R.id.AboutText);
        mCollage = view.findViewById(R.id.CollegeText);
    }

    private void setData() {
        bundle = this.getArguments();

        if(bundle != null){
            mAbout.setText(bundle.getString("About"));
            mCollage.setText(bundle.getString("Collage"));
            Picasso.get().load(bundle.getString("Logo")).into(mLogo);
        }
    }

    private void ButtonClick() {
        mRSS.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fragment = new NewsFragment();
                if(bundle.getString("News") != null){
                    bundle.putString("News", bundle.getString("News"));
                    fragment.setArguments(bundle);
                }
                FragmentTransaction transaction = getFragmentManager().beginTransaction();
                transaction.setCustomAnimations(android.R.anim.fade_in, android.R.anim.fade_out, android.R.anim.fade_in, android.R.anim.fade_out);
                transaction.replace(R.id.Container, fragment).addToBackStack(null).commit();
            }
        });
    }
}
