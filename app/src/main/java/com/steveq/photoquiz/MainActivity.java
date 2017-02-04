package com.steveq.photoquiz;

import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.TextView;
import android.widget.Toast;

import butterknife.BindView;
import butterknife.ButterKnife;
import uk.co.chrisjenx.calligraphy.CalligraphyConfig;

public class MainActivity extends AppCompatActivity {

    public static final String RANKING_FRAGMENT = "RANKING_FRAGMENT";
    public static final String PREPARATION_FRAGMENT = "PREPARATION_FRAGMENT";
    @BindView(R.id.mainAppBar) AppBarLayout mAppBarLayout;
    @BindView(R.id.collapsingToolbar) CollapsingToolbarLayout mCollapsingToolbarLayout;
    @BindView(R.id.fragmentContainer) FrameLayout mFragmentContainer;
    @BindView(R.id.mainFab) FloatingActionButton mFloatingActionButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        CalligraphyConfig.initDefault(new CalligraphyConfig.Builder()
                .setDefaultFontPath("fonts/Roboto-RobotoRegular.ttf")
                .setFontAttrId(R.attr.fontPath)
                .build()
        );
        View view = LayoutInflater.from(this).inflate(R.layout.activity_main, null);


//        mAppBarLayout = (AppBarLayout) view.findViewById(R.id.mainAppBar);
//        mCollapsingToolbarLayout = (CollapsingToolbarLayout) view.findViewById(R.id.collapsingToolbar);
//        AppBarLayout.LayoutParams params = (AppBarLayout.LayoutParams) mCollapsingToolbarLayout.getLayoutParams();
//        params.setScrollFlags(0);
        setContentView(view);

        ButterKnife.bind(this, view);

        FragmentManager fm = getSupportFragmentManager();
        fm.beginTransaction()
                .add(R.id.fragmentContainer, new RankingFragment(), RANKING_FRAGMENT)
                .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE)
                .commit();

        mFloatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getSupportFragmentManager()
                        .beginTransaction()
                        .replace(R.id.fragmentContainer, new PreparationFragment(), PREPARATION_FRAGMENT)
                        .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE)
                        .addToBackStack(PREPARATION_FRAGMENT)
                        .commit();
            }
        });
    }
}
