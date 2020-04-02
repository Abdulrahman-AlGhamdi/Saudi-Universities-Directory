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
        UniversityButtons();

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

    private void UniversityButtons() {
        mSattamUniversity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getData(Sattam);
            }
        });
        mSaudUniversity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getData(Saud);
            }
        });
    }

    private void getData(UniversitiesModel model) {
        Fragment fragment = new DetailsFragment();
        if(model.getLogo() != null){
            bundle.putString("Logo", model.getLogo());
        }
        if(model.getNews() != null){
            bundle.putString("News", model.getNews());;
        }
        if(model.getAbout() != null){
            bundle.putString("About", model.getAbout());
        }
        if(model.getCollege() != null){
            bundle.putString("Collage", model.getCollege());
        }
        if(model.getTwitter() != null){
            bundle.putString("Twitter", model.getTwitter());
        }
        if(model.getFacebook() != null){
            bundle.putString("Facebook", model.getFacebook());
        }
        if(model.getLocation() != null){
            bundle.putString("Location", model.getLocation());
        }
        if(bundle != null){
            fragment.setArguments(bundle);
        }
        FragmentTransaction transaction = getFragmentManager().beginTransaction();
        transaction.setCustomAnimations(android.R.anim.fade_in, android.R.anim.fade_out, android.R.anim.fade_in, android.R.anim.fade_out);
        transaction.replace(R.id.Container, fragment).addToBackStack(null).commit();
    }
}
