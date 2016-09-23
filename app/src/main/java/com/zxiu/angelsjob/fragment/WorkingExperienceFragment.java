package com.zxiu.angelsjob.fragment;


import android.databinding.DataBindingUtil;

import com.zxiu.angelsjob.R;
import com.zxiu.angelsjob.bean.User;
import com.zxiu.angelsjob.databinding.FragmentWorkingExperienceBinding;

/**
 * Created by Xiu on 9/21/2016.
 */

public class WorkingExperienceFragment extends AngelsJobFragment {


    @Override
    public int getTitleId() {
        return R.string.working_experience;
    }

    @Override
    public int getLayoutId() {
        return R.layout.fragment_working_experience;
    }

    @Override
    protected void bindViewData() {
        FragmentWorkingExperienceBinding binding = DataBindingUtil.bind(view);
        binding.setUser(User.getCurrentUser());
    }
}
