package com.steveq.photoquiz;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.steveq.photoquiz.adapters.QuestionsAdapter;


/**
 * A simple {@link Fragment} subclass.
 */
public class QuizFragment extends Fragment {

    RecyclerView quizRecyclerView;
    QuestionsAdapter mAdapter;

    public QuizFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        ViewGroup viewGroup = (ViewGroup) inflater.inflate(R.layout.fragment_quiz, container, false);
        quizRecyclerView = (RecyclerView) viewGroup.findViewById(R.id.quizRecyclerView);
        mAdapter = new QuestionsAdapter(getContext());
        quizRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        quizRecyclerView.setAdapter(mAdapter);

        return inflater.inflate(R.layout.fragment_quiz, container, false);
    }

}
