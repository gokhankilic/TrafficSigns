package com.example.trafficsigns.symbols;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
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
import com.example.trafficsigns.symbols.helper.OnStartDragListener;
import com.example.trafficsigns.symbols.helper.SimpleItemTouchHelperCallback;

import java.util.ArrayList;


public class FavoritedSymbolListFragment extends BaseFragment implements OnStartDragListener {

    public static final String TAG = FavoritedSymbolListFragment.class.getSimpleName();

    //VARIABLES
    SymbolListAdapter adapter;
    ArrayList<Symbol> symbols;
    private ItemTouchHelper mItemTouchHelper;
    private boolean isEditModeOn = false;

    //VIEW
    RecyclerView recyclerView;


    public static FavoritedSymbolListFragment newInstance() {
        FavoritedSymbolListFragment fragment = new FavoritedSymbolListFragment();
        return fragment;
    }


    @Override
    public void onResume() {
        super.onResume();


    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        symbols = BaseActivity.mDBHelper.getFavoritedSymbols();

        int index = 0;
        for (Symbol symbol:symbols){
            BaseActivity.mDBHelper.updateFavoritedSymbols(symbol.getId(),index);
            index++;
        }
        return inflater.inflate(R.layout.component_recyclerview, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        getBaseActivity().initToolbarView("Favorites");

        recyclerView = view.findViewById(R.id.recyclerView);
        adapter = new SymbolListAdapter(symbols,this);
        recyclerView.addItemDecoration(new DividerItemDecoration(recyclerView.getContext(), DividerItemDecoration.VERTICAL));


        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getBaseActivity().getApplicationContext());
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(adapter);

        ItemTouchHelper.Callback callback = new SimpleItemTouchHelperCallback(adapter,getContext());
        mItemTouchHelper = new ItemTouchHelper(callback);
        mItemTouchHelper.attachToRecyclerView(recyclerView);


    }


    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        getBaseActivity().getMenuInflater().inflate(R.menu.bar_edit_button, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId() == R.id.app_bar_edit_button){
           isEditModeOn = !isEditModeOn;
           adapter.setEditModeOn(isEditModeOn);
           if(isEditModeOn){
               item.setTitle("DONE");
           }else{
               item.setTitle("EDIT");
           }
        }
        return true;
    }





    @Override
    public void onStartDrag(RecyclerView.ViewHolder viewHolder) {
        mItemTouchHelper.startDrag(viewHolder);
    }
}

