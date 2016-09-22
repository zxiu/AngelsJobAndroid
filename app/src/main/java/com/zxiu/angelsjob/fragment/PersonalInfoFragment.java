package com.zxiu.angelsjob.fragment;


import android.support.design.widget.TextInputEditText;

import com.zxiu.angelsjob.R;

import butterknife.BindView;
import butterknife.OnTextChanged;

/**
 * Created by Xiu on 9/21/2016.
 */

public class PersonalInfoFragment extends AngelsJobFragment {
    @BindView(R.id.first_name)
    TextInputEditText firstName;
    @BindView(R.id.last_name)
    TextInputEditText lastName;
    @BindView(R.id.birthday)
    TextInputEditText birthday;
    @BindView(R.id.email)
    TextInputEditText email;


    @Override
    public int getTitleId() {
        return R.string.personal_info;
    }

    @Override
    public int getLayoutId() {
        return R.layout.fragment_personal_info;
    }

    @OnTextChanged({R.id.first_name, R.id.last_name, R.id.email})
    public void save() {
        saveUser();
    }
}
