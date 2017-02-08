package com.steveq.photoquiz;

import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.widget.FrameLayout;

import com.j256.ormlite.android.apptools.OpenHelperManager;
import com.steveq.photoquiz.database.DatabaseManager;
import com.steveq.photoquiz.database.QuizOpenDatabaseHelper;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import uk.co.chrisjenx.calligraphy.CalligraphyConfig;

public class MainActivity extends AppCompatActivity{

    public static final String RANKING_FRAGMENT = "RANKING_FRAGMENT";
    public static final String PREPARATION_FRAGMENT = "PREPARATION_FRAGMENT";
    private static final String QUIZ_FRAGMENT = "QUIZ_FRAGMENT";
    private static final String TAG = MainActivity.class.getSimpleName();
    public static final String PLAYER_ID = "PLAYER_ID";
    private QuizOpenDatabaseHelper databaseHelper = null;
    private Fragment currentFragment;
    private boolean backAllowed;

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
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        getSupportFragmentManager()
                .beginTransaction()
                .add(R.id.fragmentContainer, new RankingFragment(), RANKING_FRAGMENT)
                .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE)
                .commit();
    }

    @OnClick(R.id.mainFab)
    public void mainFab(){
        if(getCurrentFragment() instanceof RankingFragment){
            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.fragmentContainer, new PreparationFragment(), PREPARATION_FRAGMENT)
                    .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE)
                    .addToBackStack(PREPARATION_FRAGMENT)
                    .commit();
        } else if(getCurrentFragment() instanceof PreparationFragment){
            long id = ((PreparationFragment)getCurrentFragment()).createPlayer();
            QuizFragment quiz = new QuizFragment();
            Bundle args = new Bundle();
            args.putLong(PLAYER_ID, id);
            quiz.setArguments(args);
            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.fragmentContainer, quiz, QUIZ_FRAGMENT)
                    .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE)
                    .addToBackStack(QUIZ_FRAGMENT)
                    .commit();
        }
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

    public void setBackAllowed(boolean backAllowed) {
        this.backAllowed = backAllowed;
    }

    @Override
    public void onBackPressed() {
        if (backAllowed) {
            super.onBackPressed();
        } else {

        }
    }
}
