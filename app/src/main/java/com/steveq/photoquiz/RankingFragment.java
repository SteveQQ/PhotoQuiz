package com.steveq.photoquiz;


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

import com.steveq.photoquiz.adapters.RankingAdapter;


/**
 * A simple {@link Fragment} subclass.
 */
public class RankingFragment extends Fragment {

    private RecyclerView mRankingRecyclingView;
    private RankingAdapter mAdapter;
    private AppCompatActivity mActivity;

    public RankingFragment() {
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
        ViewGroup viewGroup = (ViewGroup) inflater.inflate(R.layout.fragment_ranking, container, false);

        mRankingRecyclingView = (RecyclerView) viewGroup.findViewById(R.id.rankingRecyclerView);
        mRankingRecyclingView.setLayoutManager(new LinearLayoutManager(getContext()));
        mAdapter = new RankingAdapter(getContext());
        mRankingRecyclingView.setAdapter(mAdapter);

        disableScrolling();
        enableBack();

        return viewGroup;
    }

    private void enableBack() {
        ((MainActivity)mActivity).setBackAllowed(true);
    }

    private void disableScrolling() {
        CollapsingToolbarLayout coll = ((MainActivity)mActivity).getCollapsingToolbarLayout();
        AppBarLayout.LayoutParams params = (AppBarLayout.LayoutParams) coll.getLayoutParams();
        params.setScrollFlags(0);
        coll.setLayoutParams(params);
        ((MainActivity)mActivity).setCollapsingToolbarLayout(coll);
    }

}
