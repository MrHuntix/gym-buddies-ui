package com.example.gym.buddies.animations;

import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.Transformation;

import com.example.gym.buddies.data.model.operation.Branch;
import com.example.gym.buddies.ui.profile.ui.gyms.adapters.BranchAdapter;

import java.util.List;

public class Animations {
    public static boolean toggleArrow(View view, boolean isExpanded) {
        if (isExpanded) {
            view.animate().setDuration(200);
            return true;
        } else {
            view.animate().setDuration(200);
            return false;
        }
    }

    public static void expand(RecyclerView view, List<Branch> branches, Context context, int gymId) {
        Animation animation = expandAction(view, branches, context, gymId);
        view.startAnimation(animation);
    }

    private static Animation expandAction(RecyclerView view, List<Branch> branches, Context context, int gymId) {
        view.setLayoutManager(new LinearLayoutManager(view.getContext()));
        view.setAdapter(new BranchAdapter(branches, context, gymId));
        view.measure(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        final int actualheight = view.getMeasuredHeight();

        view.getLayoutParams().height = 0;
        view.setVisibility(View.VISIBLE);

        Animation animation = new Animation() {
            @Override
            protected void applyTransformation(float interpolatedTime, Transformation t) {

                view.getLayoutParams().height = interpolatedTime == 1
                        ? ViewGroup.LayoutParams.WRAP_CONTENT
                        : (int) (actualheight * interpolatedTime);
                view.requestLayout();
            }
        };


        animation.setDuration((long) (actualheight / view.getContext().getResources().getDisplayMetrics().density));

        view.startAnimation(animation);

        return animation;

    }

    public static void collapse(RecyclerView view) {
        //view.upd
        final int actualHeight = view.getMeasuredHeight();
        Animation animation = new Animation() {
            @Override
            protected void applyTransformation(float interpolatedTime, Transformation t) {

                if (interpolatedTime == 1) {
                    view.setVisibility(View.GONE);
                } else {
                    view.getLayoutParams().height = actualHeight - (int) (actualHeight * interpolatedTime);
                    view.requestLayout();

                }
            }
        };

        animation.setDuration((long) (actualHeight/ view.getContext().getResources().getDisplayMetrics().density));
        view.startAnimation(animation);
    }
}
