package com.example.trafficsigns.symbols;


import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.example.trafficsigns.R;
import com.example.trafficsigns.common.BaseActivity;
import com.example.trafficsigns.common.BaseFragment;
import com.example.trafficsigns.models.Category;
import java.util.ArrayList;

public class CategoryListFragment extends BaseFragment {


    //CONSTANTS
    public static final String TAG = CategoryListFragment.class.getSimpleName();


    //VARIABLES
    CategoryListAdapter adapter;
    ArrayList<Category> categoryList;
    int selectedCompanyId;
    String selectedCompanyName;

    //VIEWS
    RecyclerView recyclerView;



    public static CategoryListFragment newInstance(){
        CategoryListFragment fragment = new CategoryListFragment();
        return fragment;
    }


    @Override
    public void onResume() {
        super.onResume();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.component_recyclerview,container,false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        getBaseActivity().initToolbarView("Kategoriler");
        categoryList = BaseActivity.mDBHelper.getCategories();
        recyclerView = view.findViewById(R.id.recyclerView);
        recyclerView.addItemDecoration(new DividerItemDecoration(recyclerView.getContext(),DividerItemDecoration.VERTICAL));
        adapter = new CategoryListAdapter(categoryList);

        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getBaseActivity().getApplicationContext());
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(adapter);


    }





}
