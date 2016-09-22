package com.zxiu.angelsjob.fragment;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.zxiu.angelsjob.bean.User;
import com.zxiu.angelsjob.databinding.FragmentPersonalInfoBinding;

import butterknife.ButterKnife;

/**
 * Created by Xiu on 9/21/2016.
 */

public abstract class AngelsJobFragment extends Fragment {
    String TAG = "AngelsJobFragment";
    View view;

    public abstract int getTitleId();

    public abstract int getLayoutId();


    protected User user = User.getCurrentUser();

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        FragmentPersonalInfoBinding binding = DataBindingUtil.inflate(inflater, getLayoutId(), container, false);
        binding.setUser(user);
        view = binding.getRoot();
        ButterKnife.bind(this, view);
        return view;
    }

    protected void saveUser() {
        user.save();
    }
}
