package com.zxiu.angelsjob.bean;

import com.zxiu.angelsjob.AngelsJob;

/**
 * Created by Xiu on 9/21/2016.
 */

public class User {
    static User user;
    public CurriculumVitae curriculumVitae = new CurriculumVitae();

    public static User getCurrentUser() {
        if (user == null) {
            user = new User();
        }
        return user;
    }

    private User() {
    }

    public void save() {
        if (AngelsJob.currentUserDatabaseRef!=null){
            AngelsJob.currentUserDatabaseRef.setValue(this);
        }
    }


}
