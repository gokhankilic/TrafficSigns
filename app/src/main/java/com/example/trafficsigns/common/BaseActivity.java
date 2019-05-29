package com.example.trafficsigns.common;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.Toast;

import com.example.trafficsigns.R;
import com.example.trafficsigns.database.Database;

import java.io.IOException;



public class BaseActivity extends AppCompatActivity {
    public static final String TAG = BaseActivity.class.getSimpleName();


    public static Database mDBHelper;
    public SQLiteDatabase mDb;
    Toolbar toolbar;



    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_base_frame);
        initDatabase();

        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


    }



    //we have base layout and we add new layout into the base layout
    public void addContentView(int resId){
        FrameLayout contentFrame = findViewById(R.id.contentFrame);
        contentFrame.removeAllViews();
        getLayoutInflater().inflate(resId,contentFrame);
    }


    // for loading gif
    public void showLoadingLayout(){
       showLayoutLoading(R.layout.component_layout_loading);
    }



    public void showLayoutLoading(final int layoutResId){
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                FrameLayout contentFrame = findViewById(R.id.contentFrame);
                FrameLayout overlayContent = findViewById(R.id.overlayContent);

                if(contentFrame != null && overlayContent != null) {
                    contentFrame.setVisibility(View.GONE);
                    overlayContent.setVisibility(View.VISIBLE);
                    getLayoutInflater().inflate(layoutResId,overlayContent);
                }
            }
        });
    }



    //to customize action bar title
    public void initToolbarView(String titleText){
       toolbar.setTitle(titleText);

    }





    //for hiding loading gif
    public void hideLayoutLoading(){
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                FrameLayout contentFrame = findViewById(R.id.contentFrame);
                FrameLayout overlayContent = findViewById(R.id.overlayContent);

                if(contentFrame != null && overlayContent != null) {

                    overlayContent.removeAllViews();
                    contentFrame.setVisibility(View.VISIBLE);
                    overlayContent.setVisibility(View.GONE);

                }
            }
        });
    }

    //to enable or disable touch events for all views in the layout
    public void enableDisableViewGroup(ViewGroup viewGroup, boolean enabled) {
        int childCount = viewGroup.getChildCount();
        for (int i = 0; i < childCount; i++) {
            View view = viewGroup.getChildAt(i);
            view.setEnabled(enabled);
            if (view instanceof ViewGroup) {
                enableDisableViewGroup((ViewGroup) view, enabled);
            }
        }
    }


    //to open our database at initilization
    public void initDatabase(){
        mDBHelper = new Database(this);

        try {
            mDBHelper.updateDataBase();
        } catch (IOException mIOException) {
            throw new Error("UnableToUpdateDatabase");
        }

        try {
            mDb = mDBHelper.getWritableDatabase();
        } catch (SQLException mSQLException) {
            throw mSQLException;
        }
    }

    // to show simple toast message to the user
    public void displayWarningMessage(int errorMessageResId){
         Toast.makeText(this, errorMessageResId, Toast.LENGTH_SHORT).show();
    }


    // custom alert dialog
    public void showWarningAlertDialog(int warningMessageResId) {

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Warning");
        builder.setMessage(getResources().getString(warningMessageResId));

        builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {

            }
        });
        builder.show();
    }









}
