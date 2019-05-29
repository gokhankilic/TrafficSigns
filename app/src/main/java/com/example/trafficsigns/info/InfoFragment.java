package com.example.trafficsigns.info;



import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.trafficsigns.R;
import com.example.trafficsigns.common.BaseFragment;


public class InfoFragment extends BaseFragment {


    //CONSTANTS
    public static final String TAG = InfoFragment.class.getSimpleName();


    public static InfoFragment newInstance(){

        InfoFragment fragment = new InfoFragment();
        return fragment;
    }


    @Override
    public void onResume() {
        super.onResume();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_info,container,false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        getBaseActivity().initToolbarView("Info");



    }





}
