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
import android.widget.HorizontalScrollView;

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
    private UniversitiesModel Sattam, Saud, AlQura, Nora, Imam, Qassim;
    private Button mSattamUniversity, mSaudUniversity, mAlQura, mNoraUniversity, mImamUniversity, mQassimUniversity;

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
        mAlQura = view.findViewById(R.id.AlQuraUniversity);
        mNoraUniversity = view.findViewById(R.id.NoraUniversity);
        mSaudUniversity = view.findViewById(R.id.SaudUniversity);
        mImamUniversity = view.findViewById(R.id.ImamUniversity);
        reference = FirebaseDatabase.getInstance().getReference();
        mSattamUniversity = view.findViewById(R.id.SattamUniversity);
        mQassimUniversity = view.findViewById(R.id.QassimUniversity);

        final HorizontalScrollView centralScrollView = view.findViewById(R.id.CentreScroll);
        view.postDelayed(new Runnable() {
            @Override
            public void run() {
                centralScrollView.fullScroll(HorizontalScrollView.FOCUS_RIGHT);
            }
        }, 0);

        final HorizontalScrollView westScrollView = view.findViewById(R.id.WestScroll);
        view.postDelayed(new Runnable() {
            @Override
            public void run() {
                westScrollView.fullScroll(HorizontalScrollView.FOCUS_RIGHT);
            }
        }, 0);

        final HorizontalScrollView eastScrollView = view.findViewById(R.id.EastScroll);
        view.postDelayed(new Runnable() {
            @Override
            public void run() {
                eastScrollView.fullScroll(HorizontalScrollView.FOCUS_RIGHT);
            }
        }, 0);

        final HorizontalScrollView northScrollView = view.findViewById(R.id.NorthScroll);
        view.postDelayed(new Runnable() {
            @Override
            public void run() {
                northScrollView.fullScroll(HorizontalScrollView.FOCUS_RIGHT);
            }
        }, 0);

        final HorizontalScrollView southScrollView = view.findViewById(R.id.SouthScroll);
        view.postDelayed(new Runnable() {
            @Override
            public void run() {
                southScrollView.fullScroll(HorizontalScrollView.FOCUS_RIGHT);
            }
        }, 0);
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
                Nora = dataSnapshot.child("NoraUniversity").getValue(UniversitiesModel.class);
                Imam = dataSnapshot.child("ImamUniversity").getValue(UniversitiesModel.class);
                Qassim = dataSnapshot.child("QassimUniversity").getValue(UniversitiesModel.class);
                Sattam = dataSnapshot.child("SattamUniversity").getValue(UniversitiesModel.class);
                AlQura = dataSnapshot.child("UmmAlQuraUniversity").getValue(UniversitiesModel.class);
                progressDialog.dismiss();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Log.d("DatabaseError", databaseError.getMessage());
            }
        });
    }

    private void UniversityButtons() {
        mAlQura.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getData(AlQura);
            }
        });
        mNoraUniversity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getData(Nora);
            }
        });
        mImamUniversity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getData(Imam);
            }
        });
        mSaudUniversity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getData(Saud);
            }
        });
        mSattamUniversity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getData(Sattam);
            }
        });
        mQassimUniversity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getData(Qassim);
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
