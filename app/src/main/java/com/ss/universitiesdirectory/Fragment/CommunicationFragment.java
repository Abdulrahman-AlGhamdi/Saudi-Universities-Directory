package com.ss.universitiesdirectory.Fragment;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import androidx.fragment.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import com.ss.universitiesdirectory.R;

public class CommunicationFragment extends DialogFragment {

    private View view;
    private Bundle bundle;
    private ImageView Twitter, Facebook;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_communication, container, false);

        init();
        SocialMedia();

        return view;
    }

    private void init() {
        bundle = new Bundle();
        bundle = this.getArguments();
        Twitter = view.findViewById(R.id.Twitter);
        Facebook = view.findViewById(R.id.Facebook);
    }

    private void SocialMedia() {
        if(bundle.getString("Twitter") != null){
            Twitter.setVisibility(View.VISIBLE);
            Twitter.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    startActivity(new Intent(Intent.ACTION_VIEW , Uri.parse(bundle.getString("Twitter"))));
                }
            });
        }

        if(bundle.getString("Facebook") != null){
            Facebook.setVisibility(View.VISIBLE);
            Facebook.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    startActivity(new Intent(Intent.ACTION_VIEW , Uri.parse(bundle.getString("Facebook"))));
                }
            });
        }
    }

}
