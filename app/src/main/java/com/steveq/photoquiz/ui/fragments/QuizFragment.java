package com.steveq.photoquiz.ui.fragments;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.steveq.photoquiz.R;

import com.steveq.photoquiz.adapters.QuestionsAdapter;


/**
 * A simple {@link Fragment} subclass.
 */
public class QuizFragment extends Fragment{

    private RecyclerView quizRecyclerView;
    private QuestionsAdapter mAdapter;
    private AppCompatActivity mActivity;

    public QuizFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mActivity = (AppCompatActivity) getActivity();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        Bundle args = getArguments();
        long id = args.getLong(com.steveq.photoquiz.ui.activities.MainActivity.PLAYER_ID);

        // Inflate the layout for this fragment
        ViewGroup viewGroup = (ViewGroup) inflater.inflate(R.layout.fragment_quiz, container, false);
        quizRecyclerView = (RecyclerView) viewGroup.findViewById(R.id.quizRecyclerView);
        mAdapter = new QuestionsAdapter((AppCompatActivity) getContext(), id);
        quizRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        quizRecyclerView.setAdapter(mAdapter);

        enableScrolling();
        disableBack();

        return viewGroup;
    }

    private void disableBack() {
    }

    private void enableScrolling() {
        CollapsingToolbarLayout coll = ((com.steveq.photoquiz.ui.activities.MainActivity)mActivity).getCollapsingToolbarLayout();
        AppBarLayout.LayoutParams params = (AppBarLayout.LayoutParams) coll.getLayoutParams();
        params.setScrollFlags(AppBarLayout.LayoutParams.SCROLL_FLAG_SCROLL | AppBarLayout.LayoutParams.SCROLL_FLAG_EXIT_UNTIL_COLLAPSED);
        coll.setLayoutParams(params);
        ((com.steveq.photoquiz.ui.activities.MainActivity)mActivity).setCollapsingToolbarLayout(coll);
    }
}
