package com.zxiu.angelsjob.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.util.SparseArrayCompat;
import android.support.v4.view.PagerTitleStrip;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;

import com.zxiu.angelsjob.R;
import com.zxiu.angelsjob.fragment.AngelsJobFragment;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.lang.reflect.InvocationTargetException;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Xiu on 9/24/2016.
 */

public abstract class AngelsJobActivity extends AppCompatActivity {
    @BindView(R.id.pager)
    ViewPager pager;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.fab_ok)
    FloatingActionButton fab_ok;
    @BindView(R.id.fab_cancel)
    FloatingActionButton fab_cancel;
    @BindView(R.id.pager_title_strip)
    PagerTitleStrip pagerTitleStrip;

    AngelsJobPagerAdapter mPagerAdapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Thread.setDefaultUncaughtExceptionHandler(new ExceptionHandler());
        setContentView(R.layout.activity_angels_job);

        ButterKnife.bind(this);
        setSupportActionBar(toolbar);
        mPagerAdapter = new AngelsJobPagerAdapter(getSupportFragmentManager());
        pager.setOffscreenPageLimit(3);
        pager.setAdapter(mPagerAdapter);

        pagerTitleStrip.setVisibility(getFragmentClasses().length > 1 ? View.VISIBLE : View.GONE);
    }

    protected abstract Class<AngelsJobFragment>[] getFragmentClasses();


    public class ExceptionHandler implements java.lang.Thread.UncaughtExceptionHandler {
        private final String LINE_SEPARATOR = "\n";
        final String LOG_TAG = CurriculumVitaeActivity.ExceptionHandler.class.getSimpleName();

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

    public class AngelsJobPagerAdapter extends FragmentStatePagerAdapter {
        SparseArrayCompat<String> titles = new SparseArrayCompat<>();

        public AngelsJobPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public AngelsJobFragment getItem(int position) {
            AngelsJobFragment f = null;
            try {
                f = getFragmentClasses()[position].getConstructor().newInstance();
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
            return getFragmentClasses().length;
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
}
