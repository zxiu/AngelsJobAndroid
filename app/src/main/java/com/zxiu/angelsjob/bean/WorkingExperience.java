package com.zxiu.angelsjob.bean;

import java.util.Date;

/**
 * Created by Xiu on 9/24/2016.
 */

public class WorkingExperience {
    public Date begin = new Date();
    public Date end = new Date();


    public Employer employer = new Employer();
    public String title;
    public String activity;
    public String description;

    public String getBegin() {
        return begin.toString();
    }

    public void setBegin(String begin) {

    }

    public String getEnd() {
        return end.toString();
    }

    public void setEnd(String end) {

    }




}
