package com.example.trafficsigns.symbols;

import android.annotation.SuppressLint;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.trafficsigns.R;
import com.example.trafficsigns.common.BaseActivity;
import com.example.trafficsigns.models.Symbol;
import com.example.trafficsigns.symbols.helper.ItemTouchHelperAdapter;
import com.example.trafficsigns.symbols.helper.OnStartDragListener;
import java.util.ArrayList;
import java.util.Collections;

public class SymbolListAdapter extends RecyclerView.Adapter<SymbolListAdapter.MyViewHolder> implements ItemTouchHelperAdapter {

    private ArrayList<Symbol> symbols;
    private OnStartDragListener mDragStartListener;
    private boolean isEditModeOn = false;




    public SymbolListAdapter(ArrayList<Symbol> categoryList,OnStartDragListener startDragListener) {
        this.mDragStartListener = startDragListener;
        this.symbols = categoryList;
    }


    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView symbolName;
        ImageView symbolImage;
        ImageView reorderImage;

        public MyViewHolder(View view) {
            super(view);
            symbolName = view.findViewById(R.id.symbolNameLabel);
            symbolImage = view.findViewById(R.id.symbolImageView);
            reorderImage = view.findViewById(R.id.reorderImageView);
        }


    }


    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.component_symbol_list_item, parent, false);

        return new MyViewHolder(itemView);
    }

    @SuppressLint("ClickableViewAccessibility")
    @Override
    public void onBindViewHolder(final MyViewHolder holder, final int position) {
        final Symbol symbol = symbols.get(position);

        Bitmap symbolImageBitmap = BitmapFactory.decodeByteArray(symbol.getImage(),0, symbol.getImage().length);

        holder.symbolName.setText(symbol.getName());
        holder.symbolImage.setImageBitmap(symbolImageBitmap);


        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Symbol selectedSymbol = symbols.get(position);
                SymbolDetailFragment fragment = SymbolDetailFragment.newInstance(symbol);

                AppCompatActivity activity = (AppCompatActivity) v.getContext();
                FragmentTransaction transaction = activity.getSupportFragmentManager().beginTransaction();
                transaction.replace(R.id.containerView,fragment,SymbolListFragment.TAG);
                transaction.addToBackStack(SymbolListFragment.TAG);
                transaction.commit();
            }
        });

        if(isEditModeOn){
            holder.reorderImage.setVisibility(View.VISIBLE);
            holder.reorderImage.setOnTouchListener(new View.OnTouchListener() {
                @Override
                public boolean onTouch(View v, MotionEvent event) {
                    mDragStartListener.onStartDrag(holder);
                    return false;
                }
            });
        }else{
            holder.reorderImage.setVisibility(View.GONE);
        }




    }


    public void setEditModeOn(boolean isOn){

        this.isEditModeOn = isOn;

        notifyItemRangeChanged(0,symbols.size());

    }

    @Override
    public int getItemCount() {
        return symbols.size();
    }

    public Symbol getItem(int position){
        return symbols.get(position);
    }

    public SymbolListAdapter(ArrayList<Symbol> categoryList) {
        this.symbols = categoryList;
    }



    public void setData(ArrayList<Symbol> filteredSymbols) {
        this.symbols = filteredSymbols;
        notifyDataSetChanged();
    }


    @Override
    public boolean onItemMove(int fromPosition, int toPosition) {
        Collections.swap(symbols, fromPosition, toPosition);
        notifyItemMoved(fromPosition, toPosition);

        int index = 0;
        for(Symbol symbol:symbols) {
            BaseActivity.mDBHelper.updateFavoritedSymbols(symbol.getId(),index);
            index++;
        }
        return true;
    }

    @Override
    public void onItemDismiss(int position) {
        notifyItemRemoved(position);
        BaseActivity.mDBHelper.updateSymbol(symbols.get(position).getId(),false);
        symbols.remove(position);


    }






}