package com.ss.universitiesdirectory.Fragment;

import android.app.ProgressDialog;
import android.graphics.Color;
import android.graphics.drawable.Animatable2;
import android.graphics.drawable.AnimatedVectorDrawable;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.HorizontalScrollView;
import android.widget.ImageView;
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
    private String[] University;
    private ImageView mMainImage;
    private DatabaseReference reference;
    private ProgressDialog progressDialog;
    private AnimatedVectorDrawable vectorDrawable;
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
        Animation();
        UniversityButtons();

        return view;
    }

    private void init() {
        bundle = new Bundle();
        mMainImage = view.findViewById(R.id.MainImage);
        reference = FirebaseDatabase.getInstance().getReference();
        Animation appearAnimation = AnimationUtils.loadAnimation(getActivity(), R.anim.appear_animation);
        mMainImage.setAnimation(appearAnimation);
        University = new String[] {"Saud", "Sattam", "Nora", "Imam", "Qassim", "Hail", "Shaqra", "Majmaah", "SaudiElectronic",
                "SaudHealth", "Jeddah", "Abdulaziz", "UmmAlQura", "Taif", "Taibah", "Islamic", "Fahd", "Faisal",
                "ImamAbdulrahman", "HafrBatin", "Jouf", "Tabuk", "NorthernBorders", "Albaha", "Najran", "Khalid", "Jazan",
                "Bisha"};

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

    private void Animation() {
        vectorDrawable = (AnimatedVectorDrawable) mMainImage.getDrawable();
        vectorDrawable.start();
        vectorDrawable.registerAnimationCallback(new Animatable2.AnimationCallback() {
            @Override
            public void onAnimationEnd(Drawable drawable) {
                vectorDrawable.start();
            }
        });
    }

    private void UniversityButtons() {
        mSaudUniversity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatabaseManagement(University[0]);
            }
        });
        mSattamUniversity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatabaseManagement(University[1]);
            }
        });
        mNoraUniversity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatabaseManagement(University[2]);
            }
        });
        mImamUniversity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatabaseManagement(University[3]);
            }
        });
        mQassimUniversity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatabaseManagement(University[4]);
            }
        });
        mHailUniversity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatabaseManagement(University[5]);
            }
        });
        mShaqraUniversity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatabaseManagement(University[6]);
            }
        });
        mMajmaahUniversity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatabaseManagement(University[7]);
            }
        });
        mSaudiElectronicUniversity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatabaseManagement(University[8]);
            }
        });
        mSaudHealthUniversity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatabaseManagement(University[9]);
            }
        });
        mJeddahUniversity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatabaseManagement(University[10]);
            }
        });
        mAbdulazizUniversity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatabaseManagement(University[11]);
            }
        });
        mAlQuraUniversity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatabaseManagement(University[12]);
            }
        });
        mTaifUniversity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatabaseManagement(University[13]);
            }
        });
        mTaibahUniversity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatabaseManagement(University[14]);
            }
        });
        mIslamicUniversity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatabaseManagement(University[15]);
            }
        });
        mFahdUniversity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatabaseManagement(University[16]);
            }
        });
        mFaisalUniversity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatabaseManagement(University[17]);
            }
        });
        mImamAbdulrahmanUniversity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatabaseManagement(University[18]);
            }
        });
        mHafrBatinUniversity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatabaseManagement(University[19]);
            }
        });
        mJoufUniversity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatabaseManagement(University[20]);
            }
        });
        mTabukUniversity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatabaseManagement(University[21]);
            }
        });
        mNorthernBordersUniversity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatabaseManagement(University[22]);
            }
        });
        mAlbahaUniversity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatabaseManagement(University[23]);
            }
        });
        mNajranUniversity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatabaseManagement(University[24]);
            }
        });
        mKhalidUniversity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatabaseManagement(University[25]);
            }
        });
        mJazanUniversity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatabaseManagement(University[26]);
            }
        });
        mBishaUniversity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatabaseManagement(University[27]);
            }
        });
    }

    private void DatabaseManagement(final String model) {
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                progressDialog = new ProgressDialog(getActivity());
                progressDialog.show();
                progressDialog.setCancelable(false);
                progressDialog.setMessage("Please Wait...");
                progressDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.GRAY));

                UniversitiesModel University;
                University = dataSnapshot.child(model+"University").getValue(UniversitiesModel.class);

                progressDialog.dismiss();
                if(University != null){
                    getData(University);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Log.d("DatabaseError", databaseError.getMessage());
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
        if(model.getYoutube() != null){
            bundle.putString("Youtube", model.getYoutube());
        }
        if(model.getSnapchat() != null){
            bundle.putString("Snapchat", model.getSnapchat());
        }
        if(model.getFacebook() != null){
            bundle.putString("Facebook", model.getFacebook());
        }
        if(model.getLocation() != null){
            bundle.putString("Location", model.getLocation());
        }
        if(model.getInstagram() != null){
            bundle.putString("Instagram", model.getInstagram());
        }
        if(bundle != null){
            fragment.setArguments(bundle);
        }
        FragmentTransaction transaction = getFragmentManager().beginTransaction();
        transaction.setCustomAnimations(android.R.anim.fade_in, android.R.anim.fade_out, android.R.anim.fade_in, android.R.anim.fade_out);
        transaction.replace(R.id.Container, fragment).addToBackStack(null).commit();
    }
}
