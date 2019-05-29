package com.example.trafficsigns.symbols;

import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import com.example.trafficsigns.R;
import com.example.trafficsigns.models.Category;
import java.util.ArrayList;


public class CategoryListAdapter extends RecyclerView.Adapter<CategoryListAdapter.MyViewHolder> {

    private ArrayList<Category> categoryList;


    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView title;

        public MyViewHolder(View view) {
            super(view);
            title = (TextView) view.findViewById(R.id.itemLabel);
        }


    }


    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.component_simple_list_item, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, final int position) {
        Category Category = categoryList.get(position);
        holder.title.setText(Category.getTitle());

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SymbolListFragment fragment = SymbolListFragment.newInstance(categoryList.get(position).getId(),categoryList.get(position).getTitle());
                AppCompatActivity activity = (AppCompatActivity) v.getContext();
                FragmentTransaction transaction = activity.getSupportFragmentManager().beginTransaction();
                transaction.replace(R.id.containerView,fragment,CategoryListFragment.TAG);
                transaction.addToBackStack(CategoryListFragment.TAG);
                transaction.commit();
            }
        });


    }

    @Override
    public int getItemCount() {
        return categoryList.size();
    }

    public Category getItem(int position){
        return categoryList.get(position);
    }

    public CategoryListAdapter(ArrayList<Category> categoryList) {
        this.categoryList = categoryList;
    }



}