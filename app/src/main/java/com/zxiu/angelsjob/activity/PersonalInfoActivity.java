package com.zxiu.angelsjob.activity;

import com.google.firebase.database.DatabaseReference;
import com.zxiu.angelsjob.AngelsJobApp;
import com.zxiu.angelsjob.bean.User;
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

    @Override
    protected DatabaseReference getDatabaseRef() {
        return AngelsJobApp.currentUserDatabaseRef.child("personalInfo");
    }

    @Override
    protected Object getStoreObject() {
        return User.getCurrentUser().personalInfo;
    }
}
