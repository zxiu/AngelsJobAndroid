package com.zxiu.angelsjob.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.util.SparseArrayCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.zxiu.angelsjob.R;
import com.zxiu.angelsjob.bean.User;
import com.zxiu.angelsjob.fragment.AngelsJobFragment;
import com.zxiu.angelsjob.fragment.PersonalInfoFragment;
import com.zxiu.angelsjob.fragment.WorkingExperienceFragment;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by Xiu on 9/21/2016.
 */

public class CurriculumVitaeActivity extends AppCompatActivity {
    @BindView(R.id.pager)
    ViewPager pager;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.fab_ok)
    FloatingActionButton fab_ok;

    MyPagerAdapter myPagerAdapter;
    List<Class<AngelsJobFragment>> fragmentClasses = new ArrayList() {{
        add(PersonalInfoFragment.class);
        add(WorkingExperienceFragment.class);
    }};

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Thread.setDefaultUncaughtExceptionHandler(new ExceptionHandler());
        setContentView(R.layout.activity_curriculum_vitae);
        ButterKnife.bind(this);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowHomeEnabled(false);
        getSupportActionBar().setDisplayHomeAsUpEnabled(false);

        myPagerAdapter = new MyPagerAdapter(getSupportFragmentManager());
        pager.setOffscreenPageLimit(3);
        pager.setAdapter(myPagerAdapter);

    }

    @OnClick(R.id.fab_cancel)
    protected void discard() {
        Snackbar.make(fab_ok, "Discard?", Snackbar.LENGTH_LONG)
                .setAction(android.R.string.ok, new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        finish();
                    }
                }).show();
    }
    @OnClick(R.id.fab_ok)
    protected void save() {
        Snackbar.make(fab_ok, "Save?", Snackbar.LENGTH_LONG)
                .setAction(android.R.string.ok, new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        User.storeCurrentUser();
                        finish();
                    }
                }).show();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    /**
     * A placeholder fragment containing a simple view.
     */
    public static class PlaceholderFragment extends Fragment {
        /**
         * The fragment argument representing the section number for this
         * fragment.
         */
        private static final String ARG_SECTION_NUMBER = "section_number";

        public PlaceholderFragment() {
        }

        /**
         * Returns a new instance of this fragment for the given section
         * number.
         */
        public static PlaceholderFragment newInstance(int sectionNumber) {
            PlaceholderFragment fragment = new PlaceholderFragment();
            Bundle args = new Bundle();
            args.putInt(ARG_SECTION_NUMBER, sectionNumber);
            fragment.setArguments(args);
            return fragment;
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            View rootView = inflater.inflate(R.layout.fragment_main, container, false);
            TextView textView = (TextView) rootView.findViewById(R.id.section_label);
            textView.setText("Hello " + getArguments().getInt(ARG_SECTION_NUMBER));
            return rootView;
        }
    }


    public class MyPagerAdapter extends FragmentStatePagerAdapter {
        SparseArrayCompat<String> titles = new SparseArrayCompat<>();

        public MyPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public AngelsJobFragment getItem(int position) {
            AngelsJobFragment f = null;
            try {
                f = fragmentClasses.get(position).getConstructor().newInstance();
                titles.put(position, getString(f.getTitleId()));
                return f;
            } catch (InstantiationException e) {
                e.printStackTrace();
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            } catch (InvocationTargetException e) {
                e.printStackTrace();
            } catch (NoSuchMethodException e) {
                e.printStackTrace();
            }
            return f;
        }

        @Override
        public int getCount() {
            return fragmentClasses.size();
        }

        @Override
        public CharSequence getPageTitle(int position) {
            String title = titles.get(position);
            if (title == null) {
                title = getString(getItem(position).getTitleId());
            }
            return title;
        }
    }

    public class ExceptionHandler implements java.lang.Thread.UncaughtExceptionHandler {
        private final String LINE_SEPARATOR = "\n";
        final String LOG_TAG = ExceptionHandler.class.getSimpleName();

        @SuppressWarnings("deprecation")
        public void uncaughtException(Thread thread, Throwable exception) {
            StringWriter stackTrace = new StringWriter();
            exception.printStackTrace(new PrintWriter(stackTrace));

            StringBuilder errorReport = new StringBuilder();
            errorReport.append(stackTrace.toString());

            Log.e(LOG_TAG, errorReport.toString());

            android.os.Process.killProcess(android.os.Process.myPid());
            System.exit(10);
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }
}
