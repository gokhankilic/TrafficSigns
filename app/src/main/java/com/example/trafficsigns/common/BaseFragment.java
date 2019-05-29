package com.example.trafficsigns.common;

import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;

public class BaseFragment extends Fragment {

    @Nullable
    public BaseActivity getBaseActivity() {
        if (getActivity() != null && getActivity() instanceof BaseActivity) {
            return (BaseActivity) getActivity();
        } else {
            return null;
        }
    }

}
