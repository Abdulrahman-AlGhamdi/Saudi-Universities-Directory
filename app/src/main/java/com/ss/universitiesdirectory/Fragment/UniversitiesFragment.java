package com.ss.universitiesdirectory.Fragment;

import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.ss.universitiesdirectory.Model.UniversitiesModel;
import com.ss.universitiesdirectory.R;

public class UniversitiesFragment extends Fragment {

    private View view;
    private Button mSattamUniversity, mSaudUniversity;
    private DatabaseReference reference;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view =  inflater.inflate(R.layout.fragment_universities, container, false);

        init();
        DataBaseManagement();
        toUniversityDetails();

        return view;
    }

    private void init() {
        mSattamUniversity = view.findViewById(R.id.SattamUniversity);
        mSaudUniversity = view.findViewById(R.id.SaudUniversity);
        reference = FirebaseDatabase.getInstance().getReference();
    }

    private void DataBaseManagement() {
        reference.child("SattamUniversity").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                UniversitiesModel model = dataSnapshot.getValue(UniversitiesModel.class);
                Log.d("CheckValue", model.getAbout());
                Log.d("CheckValue", model.getCollege());
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
            }
        });
    }

    private void toUniversityDetails() {
        mSattamUniversity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DetailsFragment fragment = new DetailsFragment();
                assert getFragmentManager() != null;
                FragmentTransaction transaction = getFragmentManager().beginTransaction();
                transaction.setCustomAnimations(android.R.anim.fade_in, android.R.anim.fade_out, android.R.anim.fade_in, android.R.anim.fade_out);
                transaction.replace(R.id.Container, fragment).addToBackStack(null);
                transaction.commit();
            }
        });

        mSaudUniversity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            }
        });
    }
}
