package com.steveq.photoquiz;

import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.j256.ormlite.android.apptools.OpenHelperManager;
import com.j256.ormlite.dao.Dao;
import com.steveq.photoquiz.adapters.RankingAdapter;
import com.steveq.photoquiz.database.QuizOpenDatabaseHelper;
import com.steveq.photoquiz.database.model.Players;

import java.sql.SQLException;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import uk.co.chrisjenx.calligraphy.CalligraphyConfig;

public class MainActivity extends AppCompatActivity {

    public static final String RANKING_FRAGMENT = "RANKING_FRAGMENT";
    public static final String PREPARATION_FRAGMENT = "PREPARATION_FRAGMENT";
    private static final String QUIZ_FRAGMENT = "QUIZ_FRAGMENT";
    private static final String TAG = MainActivity.class.getSimpleName();
    private QuizOpenDatabaseHelper databaseHelper = null;
    private Fragment currentFragment;

    @BindView(R.id.mainAppBar) AppBarLayout mAppBarLayout;
    @BindView(R.id.collapsingToolbar) CollapsingToolbarLayout mCollapsingToolbarLayout;
    @BindView(R.id.fragmentContainer) FrameLayout mFragmentContainer;
    @BindView(R.id.mainFab) FloatingActionButton mFloatingActionButton;
    @BindView(R.id.mainToolbar) Toolbar mToolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        CalligraphyConfig.initDefault(new CalligraphyConfig.Builder()
                .setDefaultFontPath("fonts/Roboto-RobotoRegular.ttf")
                .setFontAttrId(R.attr.fontPath)
                .build()
        );
        ViewGroup view = (ViewGroup) getLayoutInflater().inflate(R.layout.activity_main, null);

        setContentView(view);

        ButterKnife.bind(this, view);

        getSupportFragmentManager()
                .beginTransaction()
                .add(R.id.fragmentContainer, new RankingFragment(), RANKING_FRAGMENT)
                .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE)
                .commit();

        mFloatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(getCurrentFragment() instanceof RankingFragment){
                    getSupportFragmentManager()
                            .beginTransaction()
                            .replace(R.id.fragmentContainer, new PreparationFragment(), PREPARATION_FRAGMENT)
                            .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE)
                            .addToBackStack(PREPARATION_FRAGMENT)
                            .commit();
                } else if(getCurrentFragment() instanceof PreparationFragment){
                    getSupportFragmentManager()
                            .beginTransaction()
                            .replace(R.id.fragmentContainer, new QuizFragment(), QUIZ_FRAGMENT)
                            .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE)
                            .addToBackStack(QUIZ_FRAGMENT)
                            .commit();

                    AppBarLayout.LayoutParams params = (AppBarLayout.LayoutParams) mCollapsingToolbarLayout.getLayoutParams();
                    params.setScrollFlags(AppBarLayout.LayoutParams.SCROLL_FLAG_SCROLL | AppBarLayout.LayoutParams.SCROLL_FLAG_EXIT_UNTIL_COLLAPSED);
                    mCollapsingToolbarLayout.setLayoutParams(params);
                }
            }
        });
    }

    private QuizOpenDatabaseHelper getHelper(){
        if(databaseHelper == null){
            databaseHelper = OpenHelperManager.getHelper(this, QuizOpenDatabaseHelper.class);
        }
        return databaseHelper;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if(databaseHelper != null){
            OpenHelperManager.releaseHelper();
            databaseHelper = null;
        }
    }

    public Fragment getCurrentFragment(){
        List<Fragment> fragments = getSupportFragmentManager().getFragments();
        for(Fragment f : fragments){
            if (f != null && f.isVisible()){
                return f;
            }
        }
        return null;
    }

    public CollapsingToolbarLayout getCollapsingToolbarLayout() {
        return mCollapsingToolbarLayout;
    }

    public void setCollapsingToolbarLayout(CollapsingToolbarLayout collapsingToolbarLayout) {
        mCollapsingToolbarLayout = collapsingToolbarLayout;
    }
}
