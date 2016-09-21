package com.zxiu.angelsjob.fragment;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.zxiu.angelsjob.R;
import com.zxiu.angelsjob.bean.User;
import com.zxiu.angelsjob.databinding.PersonalInfoBinding;

/**
 * Created by Xiu on 9/21/2016.
 */

public class PersonalInfoFragment extends Fragment {
    User user = new User();

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        PersonalInfoBinding binding = DataBindingUtil.inflate(inflater, R.layout.personal_info, container, false);
        View view = binding.getRoot();
        return view;
    }
}
