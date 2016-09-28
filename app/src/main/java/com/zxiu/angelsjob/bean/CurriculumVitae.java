package com.zxiu.angelsjob.bean;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Xiu on 9/21/2016.
 */

public class CurriculumVitae {
    public static String NAME = "curriculumVitae";
    public List<WorkingExperience> workingExperiences = new ArrayList() {
        {
            add(new WorkingExperience());
            add(new WorkingExperience());
            add(new WorkingExperience());
        }
    };
    public List<Asset> assets = new ArrayList<>();
}
