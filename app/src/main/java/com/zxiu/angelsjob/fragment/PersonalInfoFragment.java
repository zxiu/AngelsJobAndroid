package com.zxiu.angelsjob.fragment;


import android.databinding.DataBindingUtil;

import com.zxiu.angelsjob.R;
import com.zxiu.angelsjob.bean.User;
import com.zxiu.angelsjob.databinding.FragmentPersonalInfoBinding;

/**
 * Created by Xiu on 9/21/2016.
 */

public class PersonalInfoFragment extends AngelsJobFragment {

    @Override
    public int getTitleId() {
        return R.string.personal_info;
    }

    @Override
    public int getLayoutId() {
        return R.layout.fragment_personal_info;
    }

    @Override
    protected void onBindViewData() {
        FragmentPersonalInfoBinding binding = DataBindingUtil.bind(getView());
        binding.setUser(User.getCurrentUser());
    }

    @Override
    protected int[] getDateInputViewIds() {
        return new int[]{R.id.birthday};
    }

}
