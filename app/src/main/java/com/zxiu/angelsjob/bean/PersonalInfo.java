package com.zxiu.angelsjob.bean;

import java.util.Date;

/**
 * Created by Xiu on 9/21/2016.
 */

public class PersonalInfo {
    public String firstName;
    public String lastName;
    public String email;

    public Date birthday;
    public Address address = new Address();

    public String getBirthday() {
        return birthday == null ? null : birthday.toString();
    }

    public void setBirthday(String birthday){
        this.birthday = new Date();
    }

    public String getTest(){
        return null;
    }

    public void setTest(String s){

    }

}
