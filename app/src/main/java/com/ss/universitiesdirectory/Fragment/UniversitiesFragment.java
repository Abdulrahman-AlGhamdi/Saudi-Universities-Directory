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
    private UniversitiesModel Sattam, Saud, AlQura, Nora, ImamIslamic, Qassim, Hail, Shaqra, Majmaah,
            SaudiElectronic, SaudHealth, Jeddah, Abdulaziz, Taif, Taibah, Islamic, Fahd, Faisal,
            ImamAbdulrahman, HafrBatin, Jouf, Tabuk, NorthernBorders, Albaha, Najran, Khalid, Jazan,
            Bisha;
    private Button mSattamUniversity, mSaudUniversity, mAlQuraUniversity, mNoraUniversity, mImamUniversity,
            mQassimUniversity, mHailUniversity, mShaqraUniversity, mMajmaahUniversity,
            mSaudiElectronicUniversity, mSaudHealthUniversity, mJeddahUniversity, mAbdulazizUniversity,
            mTaifUniversity, mTaibahUniversity, mIslamicUniversity, mFahdUniversity, mFaisalUniversity,
            mImamAbdulrahmanUniversity, mHafrBatinUniversity, mJoufUniversity, mTabukUniversity,
            mNorthernBordersUniversity, mAlbahaUniversity, mNajranUniversity, mKhalidUniversity,
            mJazanUniversity, mBishaUniversity;

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
        reference = FirebaseDatabase.getInstance().getReference();

        mAlQuraUniversity = view.findViewById(R.id.AlQuraUniversity);
        mFahdUniversity = view.findViewById(R.id.FahdUniversity);
        mNoraUniversity = view.findViewById(R.id.NoraUniversity);
        mSaudUniversity = view.findViewById(R.id.SaudUniversity);
        mImamUniversity = view.findViewById(R.id.ImamUniversity);
        mHailUniversity = view.findViewById(R.id.HailUniversity);
        mTaifUniversity = view.findViewById(R.id.TaifUniversity);
        mJoufUniversity = view.findViewById(R.id.JoufUniversity);
        mBishaUniversity = view.findViewById(R.id.BishaUniversity);
        mTabukUniversity = view.findViewById(R.id.TabukUniversity);
        mJazanUniversity = view.findViewById(R.id.JazanUniversity);
        mKhalidUniversity = view.findViewById(R.id.KhalidUniversity);
        mNajranUniversity = view.findViewById(R.id.NajranUniversity);
        mAlbahaUniversity = view.findViewById(R.id.AlbahaUniversity);
        mFaisalUniversity = view.findViewById(R.id.FaisalUniversity);
        mTaibahUniversity = view.findViewById(R.id.TaibahUniversity);
        mJeddahUniversity = view.findViewById(R.id.JeddahUniversity);
        mShaqraUniversity = view.findViewById(R.id.ShaqraUniversity);
        mSattamUniversity = view.findViewById(R.id.SattamUniversity);
        mQassimUniversity = view.findViewById(R.id.QassimUniversity);
        mIslamicUniversity = view.findViewById(R.id.IslamicUniversity);
        mMajmaahUniversity = view.findViewById(R.id.MajmaahUniversity);
        mHafrBatinUniversity = view.findViewById(R.id.HafrBatinUniversity);
        mAbdulazizUniversity = view.findViewById(R.id.AbdulazizUniversity);
        mSaudHealthUniversity = view.findViewById(R.id.SaudHealthUniversity);
        mImamAbdulrahmanUniversity = view.findViewById(R.id.ImamAbdulrahmanUniversity);
        mSaudiElectronicUniversity = view.findViewById(R.id.SaudiElectronicUniversity);
        mNorthernBordersUniversity = view.findViewById(R.id.NorthernBordersUniversity);

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
                Taif = dataSnapshot.child("TaifUniversity").getValue(UniversitiesModel.class);
                Jouf = dataSnapshot.child("JoufUniversity").getValue(UniversitiesModel.class);
                Hail = dataSnapshot.child("HailUniversity").getValue(UniversitiesModel.class);
                Saud = dataSnapshot.child("SaudUniversity").getValue(UniversitiesModel.class);
                Nora = dataSnapshot.child("NoraUniversity").getValue(UniversitiesModel.class);
                Fahd = dataSnapshot.child("FahdUniversity").getValue(UniversitiesModel.class);
                Bisha = dataSnapshot.child("BishaUniversity").getValue(UniversitiesModel.class);
                Tabuk = dataSnapshot.child("TabukUniversity").getValue(UniversitiesModel.class);
                Jazan = dataSnapshot.child("JazanUniversity").getValue(UniversitiesModel.class);
                Taibah = dataSnapshot.child("TaibahUniversity").getValue(UniversitiesModel.class);
                Khalid = dataSnapshot.child("KhalidUniversity").getValue(UniversitiesModel.class);
                Najran = dataSnapshot.child("NajranUniversity").getValue(UniversitiesModel.class);
                Albaha = dataSnapshot.child("AlbahaUniversity").getValue(UniversitiesModel.class);
                Faisal = dataSnapshot.child("FaisalUniversity").getValue(UniversitiesModel.class);
                Qassim = dataSnapshot.child("QassimUniversity").getValue(UniversitiesModel.class);
                Sattam = dataSnapshot.child("SattamUniversity").getValue(UniversitiesModel.class);
                Shaqra = dataSnapshot.child("ShaqraUniversity").getValue(UniversitiesModel.class);
                Jeddah = dataSnapshot.child("JeddahUniversity").getValue(UniversitiesModel.class);
                Majmaah = dataSnapshot.child("MajmaahUniversity").getValue(UniversitiesModel.class);
                Islamic = dataSnapshot.child("IslamicUniversity").getValue(UniversitiesModel.class);
                ImamIslamic = dataSnapshot.child("ImamUniversity").getValue(UniversitiesModel.class);
                AlQura = dataSnapshot.child("UmmAlQuraUniversity").getValue(UniversitiesModel.class);
                Abdulaziz = dataSnapshot.child("AbdulazizUniversity").getValue(UniversitiesModel.class);
                HafrBatin = dataSnapshot.child("HafrBatinUniversity").getValue(UniversitiesModel.class);
                SaudHealth = dataSnapshot.child("SaudHealthUniversity").getValue(UniversitiesModel.class);
                ImamAbdulrahman = dataSnapshot.child("ImamAbdulrahmanUniversity").getValue(UniversitiesModel.class);
                NorthernBorders = dataSnapshot.child("NorthernBordersUniversity").getValue(UniversitiesModel.class);
                SaudiElectronic = dataSnapshot.child("SaudiElectronicUniversity").getValue(UniversitiesModel.class);
                progressDialog.dismiss();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Log.d("DatabaseError", databaseError.getMessage());
            }
        });
    }

    private void UniversityButtons() {
        mHailUniversity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getData(Hail);
            }
        });
        mSaudUniversity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getData(Saud);
            }
        });
        mTaifUniversity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getData(Taif);
            }
        });
        mFahdUniversity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getData(Fahd);
            }
        });
        mJoufUniversity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getData(Jouf);
            }
        });
        mNoraUniversity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getData(Nora);
            }
        });
        mBishaUniversity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getData(Bisha);
            }
        });
        mJazanUniversity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getData(Jazan);
            }
        });
        mTabukUniversity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getData(Tabuk);
            }
        });
        mShaqraUniversity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getData(Shaqra);
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
        mAlQuraUniversity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getData(AlQura);
            }
        });
        mKhalidUniversity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getData(Khalid);
            }
        });
        mNajranUniversity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getData(Najran);
            }
        });
        mAlbahaUniversity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getData(Albaha);
            }
        });
        mFaisalUniversity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getData(Faisal);
            }
        });
        mTaibahUniversity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getData(Taibah);
            }
        });
        mJeddahUniversity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getData(Jeddah);
            }
        });
        mIslamicUniversity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getData(Islamic);
            }
        });
        mMajmaahUniversity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getData(Majmaah);
            }
        });
        mImamUniversity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getData(ImamIslamic);
            }
        });
        mHafrBatinUniversity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getData(HafrBatin);
            }
        });
        mAbdulazizUniversity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getData(Abdulaziz);
            }
        });
        mSaudHealthUniversity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getData(SaudHealth);
            }
        });
        mSaudiElectronicUniversity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getData(SaudiElectronic);
            }
        });
        mImamAbdulrahmanUniversity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getData(ImamAbdulrahman);
            }
        });
        mNorthernBordersUniversity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getData(NorthernBorders);
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
        if(model.getWebsite() != null){
            bundle.putString("Website", model.getWebsite());
        }
        if(model.getTwitter() != null){
            bundle.putString("Twitter", model.getTwitter());
        }
        if(model.getCollege() != null){
            bundle.putString("College", model.getCollege());
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
