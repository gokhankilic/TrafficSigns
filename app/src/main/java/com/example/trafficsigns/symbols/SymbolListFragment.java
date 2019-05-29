package com.example.trafficsigns.symbols;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.example.trafficsigns.R;
import com.example.trafficsigns.common.BaseActivity;
import com.example.trafficsigns.common.BaseFragment;
import com.example.trafficsigns.models.Symbol;
import java.util.ArrayList;

public class SymbolListFragment extends BaseFragment {

    public static final String TAG = SymbolListFragment.class.getSimpleName();
    public static final String SELECTED_CATEGORY = "selectedCategory";
    public static final String SELECTED_CATEGORY_NAME = "selectedCategoryName";

    //VARIABLES
    SymbolListAdapter adapter;
    ArrayList<Symbol> symbols;
    int selectedCategoryId;
    String selectedCategoryName;




    //VIEW
    RecyclerView recyclerView;


    public static SymbolListFragment newInstance(int selectedCategoryId,String selectedCategoryName){
        SymbolListFragment fragment = new SymbolListFragment();
        Bundle args = new Bundle();
        args.putInt(SELECTED_CATEGORY,selectedCategoryId);
        args.putString(SELECTED_CATEGORY_NAME,selectedCategoryName);
        fragment.setArguments(args);
        return fragment;
    }


    @Override
    public void onResume() {
        super.onResume();


    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        Bundle bundle = getArguments();

        if(bundle != null){
            selectedCategoryId = bundle.getInt(SELECTED_CATEGORY);
            selectedCategoryName = bundle.getString(SELECTED_CATEGORY_NAME);

            symbols = BaseActivity.mDBHelper.getSymbols(selectedCategoryId);
        }


        return inflater.inflate(R.layout.component_recyclerview,container,false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        getBaseActivity().initToolbarView(selectedCategoryName);

        recyclerView = view.findViewById(R.id.recyclerView);
        adapter = new SymbolListAdapter(symbols);
        recyclerView.addItemDecoration(new DividerItemDecoration(recyclerView.getContext(),DividerItemDecoration.VERTICAL));


        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getBaseActivity().getApplicationContext());
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(adapter);


    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        getBaseActivity().getMenuInflater().inflate(R.menu.bar_search, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }


    @Override
    public void onPrepareOptionsMenu(Menu menu) {
        MenuItem searchMenuItem = menu.findItem(R.id.app_bar_search);
        SearchView searchView = (SearchView) searchMenuItem.getActionView();
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                filterSymbols(s);
                return false;
            }
        });
    }

    void filterSymbols(String text) {
        if (text.equals("") || text.isEmpty()) {
            adapter.setData(symbols);
        } else {
            ArrayList<Symbol> filteredSymbols = new ArrayList<>();
            for (Symbol symbol : symbols) {
                if (symbol.getName().toLowerCase().contains(text.toLowerCase())) {
                    filteredSymbols.add(symbol);
                }
            }
            adapter.setData(filteredSymbols);

        }


    }









}
