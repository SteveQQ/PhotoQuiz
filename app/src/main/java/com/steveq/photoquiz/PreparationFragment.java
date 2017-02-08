package com.steveq.photoquiz;


import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;

import com.steveq.photoquiz.database.DatabaseManager;

import butterknife.BindView;
import butterknife.ButterKnife;


/**
 * A simple {@link Fragment} subclass.
 */
public class PreparationFragment extends Fragment {


    private static final String TAG = PreparationFragment.class.getSimpleName();

    private AppCompatActivity mActivity;

    @BindView(R.id.nameEditText) EditText nameEditText;
    @BindView(R.id.saveCheckBox) CheckBox saveCheckBox;

    public PreparationFragment() {
        // Required empty public constructor
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        Log.d(TAG, "onAttach");
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mActivity = (AppCompatActivity) getActivity();
        Log.d(TAG, "onCreate");
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        ViewGroup viewGroup = (ViewGroup) inflater.inflate(R.layout.fragment_preparation, container, false);
        ButterKnife.bind(this, viewGroup);

        Log.d(TAG, "onCreateView");
        disableScrolling();
        enableBack();
        return viewGroup;
    }


    private void disableScrolling() {
        CollapsingToolbarLayout coll = ((MainActivity)mActivity).getCollapsingToolbarLayout();
        AppBarLayout.LayoutParams params = (AppBarLayout.LayoutParams) coll.getLayoutParams();
        params.setScrollFlags(0);
        coll.setLayoutParams(params);
        ((MainActivity)mActivity).setCollapsingToolbarLayout(coll);
    }

    private void enableBack() {
        ((MainActivity)mActivity).setBackAllowed(true);
    }

    public long createPlayer(){
        return DatabaseManager.getInstance(mActivity)
                .addNewPlayer(nameEditText.getText().toString(), saveCheckBox.isChecked());
    }
}
