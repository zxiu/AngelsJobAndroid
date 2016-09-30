package com.zxiu.angelsjob.fragment;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.DatePicker;
import android.widget.TextView;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.HashSet;
import java.util.Set;

import butterknife.ButterKnife;

/**
 * Created by Xiu on 9/21/2016.
 */

public abstract class AngelsJobFragment extends Fragment implements View.OnFocusChangeListener {
    private String TAG = "AngelsJobFragment";
    private View view;
    private Set<View> dateInputViews = new HashSet<>();

    public abstract int getTitleId();

    public abstract int getLayoutId();

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(getLayoutId(), container, false);
        ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        onBindViewData();
        for (int id : getDateInputViewIds()) {
            View v = ButterKnife.findById(view, id);
            if (v != null) {
                dateInputViews.add(v);
                v.setOnKeyListener(null);
                v.setOnFocusChangeListener(this);
            }
        }
    }

    protected abstract void onBindViewData();

    protected abstract int[] getDateInputViewIds();

    SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");

    @Override
    public void onFocusChange(final View v, boolean hasFocus) {
        if (hasFocus && dateInputViews.contains(v) && v instanceof TextView) {
            final TextView tv = (TextView) v;
            int year, month, day;
            try {
                Calendar calendar = new GregorianCalendar();
                calendar.setTime(simpleDateFormat.parse(tv.getText().toString()));
                year = calendar.get(Calendar.YEAR);
                month = calendar.get(Calendar.MONTH);
                day = calendar.get(Calendar.DAY_OF_MONTH);
            } catch (ParseException e) {
                e.printStackTrace();
                year = 1980;
                month = Calendar.JANUARY;
                day = 1;
            }
            new DatePickerDialog(v.getContext(), new DatePickerDialog.OnDateSetListener() {
                @Override
                public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                    Calendar calendar = new GregorianCalendar();
                    calendar.set(year, month, dayOfMonth);
                    tv.setText(simpleDateFormat.format(calendar.getTime()));
                }
            }, year, month, day).show();
        }
    }
}
