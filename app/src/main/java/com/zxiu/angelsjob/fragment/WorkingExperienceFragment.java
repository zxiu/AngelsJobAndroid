package com.zxiu.angelsjob.fragment;


import android.databinding.DataBindingUtil;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.android.databinding.library.baseAdapters.BR;
import com.zxiu.angelsjob.R;
import com.zxiu.angelsjob.bean.User;
import com.zxiu.angelsjob.databinding.LayoutWorkingExperienceBinding;

import butterknife.BindView;

/**
 * Created by Xiu on 9/21/2016.
 */

public class WorkingExperienceFragment extends AngelsJobFragment {

    @BindView(R.id.recycle_view)
    RecyclerView mRecyclerView;
    MyAdapter myAdapter = new MyAdapter();

    @Override
    public int getTitleId() {
        return R.string.working_experience;
    }

    @Override
    public int getLayoutId() {
        return R.layout.fragment_working_experience;
    }

    @Override
    protected void onBindViewData() {
        mRecyclerView.setAdapter(myAdapter);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        Toast.makeText(getActivity(), "getItemCount() =" + myAdapter.getItemCount(), Toast.LENGTH_LONG).show();

    }

    @Override
    protected int[] getDateInputViewIds() {
        return new int[]{};
    }

    class MyAdapter extends RecyclerView.Adapter<MyViewHolder> {
        @Override
        public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_working_experience, parent, false);
            MyViewHolder myViewHolder = new MyViewHolder(v);
            return myViewHolder;
        }

        @Override
        public void onBindViewHolder(MyViewHolder holder, int position) {
            holder.getBinding().setVariable(BR.working, User.getCurrentUser().curriculumVitae.workingExperiences.get(position));
            holder.getBinding().executePendingBindings();

        }

        @Override
        public int getItemCount() {
            return User.getCurrentUser().curriculumVitae.workingExperiences.size();
        }
    }

    class MyViewHolder extends RecyclerView.ViewHolder {
        private LayoutWorkingExperienceBinding binding;

        public MyViewHolder(View itemView) {
            super(itemView);
            binding = DataBindingUtil.bind(itemView);
        }

        public LayoutWorkingExperienceBinding getBinding() {
            return binding;
        }
    }

}
