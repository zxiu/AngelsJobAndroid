package com.zxiu.angelsjob.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import butterknife.ButterKnife;

/**
 * Created by Xiu on 9/21/2016.
 */

public abstract class AngelsJobFragment extends Fragment {
    String TAG = "AngelsJobFragment";
    View view;

    public abstract int getTitleId();

    public abstract int getLayoutId();

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(getLayoutId(), container, false);
        ButterKnife.bind(this, view);
        bindUser();
        return view;
    }

    protected abstract void bindUser();

}
