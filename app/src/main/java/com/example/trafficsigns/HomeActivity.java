package com.example.trafficsigns;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.MenuItem;

import com.example.trafficsigns.common.BaseActivity;
import com.example.trafficsigns.info.InfoFragment;
import com.example.trafficsigns.symbols.CategoryListFragment;
import com.example.trafficsigns.symbols.FavoritedSymbolListFragment;

import java.util.List;

public class HomeActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        addContentView(R.layout.component_container);

        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);

        if(savedInstanceState == null) {
            CategoryListFragment fragment = CategoryListFragment.newInstance();
            inflateFragment(fragment, R.id.containerView, CategoryListFragment.TAG);
        }

        initToolbarView("Categories");



    }

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            removeAllFragments();
            switch (item.getItemId()) {
                case R.id.navigation_symbols:
                    CategoryListFragment companyListFragment = CategoryListFragment.newInstance();
                    inflateFragment(companyListFragment,R.id.containerView,CategoryListFragment.TAG);
                    return true;
                case R.id.navigation_favorites:
                    FavoritedSymbolListFragment favoritedSymbolListFragment = FavoritedSymbolListFragment.newInstance();
                    inflateFragment(favoritedSymbolListFragment,R.id.containerView,FavoritedSymbolListFragment.TAG);
                    return true;
                case R.id.navigation_info:
                    InfoFragment infoFragment = InfoFragment.newInstance();
                    inflateFragment(infoFragment,R.id.containerView,InfoFragment.TAG);
                    return true;
            }
            return false;
        }
    };

    /*in home screen, we deal with fragments and we replace or add new fragment according to the if there is fragment
   that is inflated before*/
    public void inflateFragment(Fragment fragment, int containerId, String fragmentTag){
        FragmentTransaction ft;
        ft = getSupportFragmentManager().beginTransaction();

        if(getVisibleFragmentTag() != null) {
            replaceFragment(fragment,containerId,getVisibleFragmentTag());
        }else {
            ft.add(containerId, fragment, fragmentTag);
            ft.commitAllowingStateLoss();
        }


    }

    //for replacing new fragment with current inflated fragment
    public void replaceFragment(Fragment fragment,int containerId,String fragmentTag){

        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(containerId,fragment, fragmentTag);
        transaction.addToBackStack(fragmentTag);
        transaction.commit();

    }


    // to get tag of the current inflated fragment
    public String getVisibleFragmentTag(){
        FragmentManager fragmentManager = HomeActivity.this.getSupportFragmentManager();
        List<Fragment> fragments = fragmentManager.getFragments();
        if(fragments != null){
            for(Fragment fragment : fragments){
                if(fragment != null && fragment.isVisible())
                    return fragment.getTag();
            }
        }
        return null;
    }


    public void removeAllFragments() {
        FragmentManager fm = HomeActivity.this.getSupportFragmentManager();
        for(int i = 0; i < fm.getBackStackEntryCount(); ++i) {
            fm.popBackStack();
        }
    }


}
