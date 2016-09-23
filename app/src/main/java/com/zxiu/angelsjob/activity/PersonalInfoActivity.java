package com.zxiu.angelsjob.activity;

import com.zxiu.angelsjob.fragment.AngelsJobFragment;
import com.zxiu.angelsjob.fragment.PersonalInfoFragment;

/**
 * Created by Xiu on 9/24/2016.
 */

public class PersonalInfoActivity extends AngelsJobActivity {
    @Override
    protected Class<AngelsJobFragment>[] getFragmentClasses() {
        return new Class[]{
                PersonalInfoFragment.class
        };
    }
}
