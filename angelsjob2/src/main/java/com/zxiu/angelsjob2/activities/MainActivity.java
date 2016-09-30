package com.zxiu.angelsjob2.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

import com.zxiu.angelsjob2.R;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * An example full-screen activity that shows and hides the system UI (i.e.
 * status bar and navigation/system bar) with user interaction.
 */
public class MainActivity extends BaseActivity {

    private List<MainMenuItem> mainMenuItemList = new ArrayList() {{
        add(new MainMenuItem(R.string.curriculum_vitae, R.drawable.banner_cv, CurriculumVitaeActivity.class, Gravity.START));
        add(new MainMenuItem(R.string.cover_letter, R.drawable.banner_cl, CoverLetterActivity.class, Gravity.END));
        add(new MainMenuItem(R.string.opportunities, R.drawable.banner_job, CurriculumVitaeActivity.class, Gravity.START));
        add(new MainMenuItem(R.string.applications, R.drawable.banner_successful, CoverLetterActivity.class, Gravity.END));
    }};

    @BindView(R.id.recycler)
    RecyclerView mRecyclerView;

    @Override
    public int getContentViewId() {
        return R.layout.content_activity_main;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mRecyclerView = (RecyclerView) findViewById(R.id.recycler);
        mRecyclerView.setAdapter(new MenuViewAdapter());
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
    }

    public class MenuViewAdapter extends RecyclerView.Adapter<MenuViewAdapter.MyViewHolder> {
        //        List<MainMenuItem> mainMenuItemList=new ArrayList<>();
        public class MyViewHolder extends RecyclerView.ViewHolder {
            @BindView(R.id.title)
            TextView title;
            @BindView(R.id.image)
            ImageView image;

            View itemView;

            public MyViewHolder(View itemView) {
                super(itemView);
                this.itemView = itemView;
                ButterKnife.bind(this, itemView);
            }
        }

        @Override
        public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_menu, parent, false);
            MyViewHolder vh = new MyViewHolder(v);
            return vh;
        }


        @Override
        public void onBindViewHolder(MyViewHolder holder, int position) {
            holder.title.setText(getItem(position).titleId);
            holder.title.setGravity(getItem(position).gravity);
            holder.image.setImageResource(getItem(position).imageId);
            holder.itemView.setOnClickListener(getItem(position).onClickListener);
            setAnimation(holder.itemView, position);
        }

        private int lastPosition = -1;

        private void setAnimation(View viewToAnimate, int position) {
            // If the bound view wasn't previously displayed on screen, it's animated
            if (position > lastPosition) {
                Animation animation = AnimationUtils.loadAnimation(viewToAnimate.getContext(), position % 2 == 0 ? R.anim.slide_in_left : R.anim.slide_in_right);
                viewToAnimate.setAnimation(animation);
                viewToAnimate.animate().setStartDelay(5000);
                //viewToAnimate.startAnimation(animation);
                lastPosition = position;
            }
        }


        public MainMenuItem getItem(int position) {
            return mainMenuItemList.get(position);
        }

        @Override
        public int getItemCount() {
            return mainMenuItemList.size();
        }
    }

    public class MenuViewItemAnimator extends RecyclerView.ItemAnimator {

        @Override
        public boolean animateDisappearance(@NonNull RecyclerView.ViewHolder viewHolder, @NonNull ItemHolderInfo preLayoutInfo, @Nullable ItemHolderInfo postLayoutInfo) {
            return false;
        }

        @Override
        public boolean animateAppearance(@NonNull RecyclerView.ViewHolder viewHolder, @Nullable ItemHolderInfo preLayoutInfo, @NonNull ItemHolderInfo postLayoutInfo) {
            return false;
        }

        @Override
        public boolean animatePersistence(@NonNull RecyclerView.ViewHolder viewHolder, @NonNull ItemHolderInfo preLayoutInfo, @NonNull ItemHolderInfo postLayoutInfo) {
            return false;
        }

        @Override
        public boolean animateChange(@NonNull RecyclerView.ViewHolder oldHolder, @NonNull RecyclerView.ViewHolder newHolder, @NonNull ItemHolderInfo preLayoutInfo, @NonNull ItemHolderInfo postLayoutInfo) {
            return false;
        }

        @Override
        public void runPendingAnimations() {

        }

        @Override
        public void endAnimation(RecyclerView.ViewHolder item) {

        }

        @Override
        public void endAnimations() {

        }

        @Override
        public boolean isRunning() {
            return false;
        }
    }

    public class MainMenuItem {
        public int titleId;
        public int imageId;
        public Class<?> clazz;
        public int gravity;
        public View.OnClickListener onClickListener;

        public MainMenuItem(int titleId, int imageId, final Class<?> clazz, int gravity) {
            this.titleId = titleId;
            this.imageId = imageId;
            this.clazz = clazz;
            this.gravity = gravity;
            if (clazz != null) {
                onClickListener = new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (AppCompatActivity.class.isAssignableFrom(clazz)) {
                            v.getContext().startActivity(new Intent(v.getContext(), clazz));
                        }
                    }
                };
            }
        }
    }
}
