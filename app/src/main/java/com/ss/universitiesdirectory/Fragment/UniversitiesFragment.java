package com.ss.universitiesdirectory.Fragment;

import android.app.ProgressDialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.ss.universitiesdirectory.Model.UniversitiesModel;
import com.ss.universitiesdirectory.R;

public class UniversitiesFragment extends Fragment {

    private View view;
    private Bundle bundle;
    private Fragment fragment;
    private DatabaseReference reference;
    private ProgressDialog progressDialog;
    private Button mSattamUniversity, mSaudUniversity;
    private UniversitiesModel Sattam, Saud;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view =  inflater.inflate(R.layout.fragment_universities, container, false);

        init();
        Progress();
        DataBaseManagement();
        toUniversityDetails();

        return view;
    }

    private void init() {
        bundle = new Bundle();
        mSaudUniversity = view.findViewById(R.id.SaudUniversity);
        reference = FirebaseDatabase.getInstance().getReference();
        mSattamUniversity = view.findViewById(R.id.SattamUniversity);
    }

    private void Progress() {
        progressDialog = new ProgressDialog(getActivity());
        progressDialog.show();
        progressDialog.setCancelable(false);
        progressDialog.setMessage("Please Wait...");
        progressDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.GRAY));
    }

    private void DataBaseManagement() {

        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                Saud = dataSnapshot.child("SaudUniversity").getValue(UniversitiesModel.class);
                Sattam = dataSnapshot.child("SattamUniversity").getValue(UniversitiesModel.class);
                progressDialog.dismiss();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Log.d("DatabaseError", databaseError.getMessage());
            }
        });
    }

    private void toUniversityDetails() {
        mSattamUniversity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fragment = new DetailsFragment();
                bundle.putString("Logo", Sattam.getLogo());
                bundle.putString("About", Sattam.getAbout());
                bundle.putString("Collage", Sattam.getCollege());
                bundle.putString("Twitter", "https://twitter.com/itdl_psau");
                bundle.putString("News", "https://www.psau.edu.sa/ar/rss.xml");
                bundle.putString("Facebook", "https://www.facebook.com/psau.edu.sa");
                bundle.putString("Location", "https://goo.gl/maps/rnMxSpaucC5N9Spp6");
                fragment.setArguments(bundle);
                FragmentTransaction transaction = getFragmentManager().beginTransaction();
                transaction.setCustomAnimations(android.R.anim.fade_in, android.R.anim.fade_out, android.R.anim.fade_in, android.R.anim.fade_out);
                transaction.replace(R.id.Container, fragment).addToBackStack(null).commit();
            }
        });

        mSaudUniversity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fragment = new DetailsFragment();
                bundle.putString("Logo", Saud.getLogo());
                bundle.putString("About", Saud.getAbout());
                bundle.putString("Collage", Saud.getCollege());
                bundle.putString("Twitter", "https://twitter.com/_KSU");
                bundle.putString("Location", "https://goo.gl/maps/KQPSzajAsBfDRh5r9");
                bundle.putString("Facebook", "https://www.facebook.com/King.Saud.University");
                fragment.setArguments(bundle);
                FragmentTransaction transaction = getFragmentManager().beginTransaction();
                transaction.setCustomAnimations(android.R.anim.fade_in, android.R.anim.fade_out, android.R.anim.fade_in, android.R.anim.fade_out);
                transaction.replace(R.id.Container, fragment).addToBackStack(null).commit();
            }
        });
    }
}
