package com.sanleng.emergencystation.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.sanleng.emergencystation.R;
import com.sanleng.emergencystation.activity.EmergencyRescueActivity;

public class Tab1Fragment extends Fragment {
    private View view;
    private TextView textView;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.taba_fragment, null);
        initView();
        return view;
    }

    private void initView(){
        textView=view.findViewById(R.id.taba);
        textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(getActivity(),EmergencyRescueActivity.class);
                startActivity(intent);
            }
        });

    }
}
