package com.example.trafficsigns.symbols;

import android.content.res.ColorStateList;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.trafficsigns.R;
import com.example.trafficsigns.common.BaseActivity;
import com.example.trafficsigns.common.BaseFragment;
import com.example.trafficsigns.models.Symbol;


public class SymbolDetailFragment extends BaseFragment {

    public static final String TAG = SymbolDetailFragment.class.getSimpleName();
    public static String SYMBOL = "symbol";

    //VARIABLES
    Symbol symbol;

    //VIEWS
    ImageView symbolImageView;
    TextView symbolDescriptionTextView;


    public static SymbolDetailFragment newInstance(Symbol symbol) {
        SymbolDetailFragment fragment = new SymbolDetailFragment();
        Bundle args = new Bundle();
        args.putParcelable(SYMBOL, symbol);
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

        if (bundle != null) {
            symbol = bundle.getParcelable(SYMBOL);
        }

        return inflater.inflate(R.layout.fragment_symbol_detail, container, false);
    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        symbolImageView = getBaseActivity().findViewById(R.id.symbolImageView);
        symbolDescriptionTextView = getBaseActivity().findViewById(R.id.symbolDescriptionTextView);
        initSymbolDetailView();
    }


    public void initSymbolDetailView() {

        getBaseActivity().initToolbarView(symbol.getName());

        Bitmap symbolImageBitmap = BitmapFactory.decodeByteArray(symbol.getImage(), 0, symbol.getImage().length);

        double screenWidth = getBaseActivity().getWindowManager().getDefaultDisplay().getWidth();
        double imageHeight = symbolImageBitmap.getHeight();
        double imageWidth = symbolImageBitmap.getWidth();
        double calculatedHeight = screenWidth * (imageHeight / imageWidth);

        symbolImageView.getLayoutParams().height = (int) calculatedHeight;
        symbolImageView.getLayoutParams().width = (int) screenWidth;

        symbolImageView.setImageBitmap(symbolImageBitmap);
        symbolDescriptionTextView.setText(symbol.getSymbolDescription());


    }


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.bar_favorite_button, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public void onPrepareOptionsMenu(Menu menu) {
        MenuItem favoriteButton = menu.findItem(R.id.app_bar_favorite_button);

        if(symbol.isInList()) {
            favoriteButton.setIcon(getResources().getDrawable(R.drawable.ic_star_filled_75));

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                favoriteButton.setIconTintList(ColorStateList.valueOf(getResources().getColor(R.color.colorAccent)));
            }
        }else{
            favoriteButton.setIcon(getResources().getDrawable(R.drawable.ic_star_75));

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                favoriteButton.setIconTintList(ColorStateList.valueOf(getResources().getColor(R.color.colorPrimary)));
            }
        }

        super.onPrepareOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId() == R.id.app_bar_favorite_button){

            if(symbol.isInList()) {
                item.setIcon(getResources().getDrawable(R.drawable.ic_star_75));

                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                    item.setIconTintList(ColorStateList.valueOf(getResources().getColor(R.color.colorAccent)));
                }
            }else{
                item.setIcon(getResources().getDrawable(R.drawable.ic_star_filled_75));

                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                    item.setIconTintList(ColorStateList.valueOf(getResources().getColor(R.color.colorPrimary)));
                }
            }

            BaseActivity.mDBHelper.updateSymbol(symbol.getId(),!symbol.isInList());
            symbol.setInList(!symbol.isInList());
        }
        return true;
    }


}
