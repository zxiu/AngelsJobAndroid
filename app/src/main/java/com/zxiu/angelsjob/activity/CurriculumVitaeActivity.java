package com.zxiu.angelsjob.activity;

import com.google.firebase.database.DatabaseReference;
import com.zxiu.angelsjob.AngelsJobApp;
import com.zxiu.angelsjob.bean.CurriculumVitae;
import com.zxiu.angelsjob.bean.User;
import com.zxiu.angelsjob.fragment.AngelsJobFragment;
import com.zxiu.angelsjob.fragment.WorkingExperienceFragment;

/**
 * Created by Xiu on 9/21/2016.
 */

public class CurriculumVitaeActivity extends AngelsJobActivity {

    @Override
    protected Class<AngelsJobFragment>[] getFragmentClasses() {
        return new Class[]{
                WorkingExperienceFragment.class, WorkingExperienceFragment.class
        };
    }

    @Override
    protected DatabaseReference getDatabaseRef() {
        return AngelsJobApp.currentUserDatabaseRef.child(CurriculumVitae.NAME);
    }

    @Override
    protected Object getStoreObject() {
        return User.getCurrentUser().curriculumVitae;
    }
}
