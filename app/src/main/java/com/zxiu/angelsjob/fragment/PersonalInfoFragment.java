package com.zxiu.angelsjob.fragment;


import android.databinding.DataBindingUtil;

import com.zxiu.angelsjob.R;
import com.zxiu.angelsjob.bean.User;
import com.zxiu.angelsjob.databinding.FragmentPersonalInfoBinding;
import com.zxiu.angelsjob.databinding.LayoutAddressBinding;

/**
 * Created by Xiu on 9/21/2016.
 */

public class PersonalInfoFragment extends AngelsJobFragment {
//    @BindView(R.id.first_name)
//    TextInputEditText firstName;
//    @BindView(R.id.last_name)
//    TextInputEditText lastName;
//    @BindView(R.id.birthday)
//    TextInputEditText birthday;
//    @BindView(R.id.email)
//    TextInputEditText email;


    @Override
    public int getTitleId() {
        return R.string.personal_info;
    }

    @Override
    public int getLayoutId() {
        return R.layout.fragment_personal_info;
    }

    @Override
    protected void bindUser() {
        FragmentPersonalInfoBinding binding = DataBindingUtil.bind(view);
        binding.setUser(User.getCurrentUser());
        LayoutAddressBinding addressBinding = DataBindingUtil.bind(view.findViewById(R.id.layout_address));
        addressBinding.setAddress(User.getCurrentUser().curriculumVitae.personalInfo.address);
    }
}
