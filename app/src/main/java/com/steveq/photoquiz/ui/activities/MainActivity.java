package com.steveq.photoquiz.ui.activities;

import android.Manifest;
import android.annotation.TargetApi;
import android.content.Intent;
import android.media.MediaScannerConnection;
import android.net.Uri;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.widget.FrameLayout;
import android.widget.Toast;

import com.j256.ormlite.android.apptools.OpenHelperManager;
import com.steveq.photoquiz.ui.fragments.QuizFragment;
import com.steveq.photoquiz.R;
import com.steveq.photoquiz.ui.fragments.RankingFragment;
import com.steveq.photoquiz.adapters.QuestionsAdapter;
import com.steveq.photoquiz.database.DatabaseManager;
import com.steveq.photoquiz.database.QuizOpenDatabaseHelper;
import com.steveq.photoquiz.onTakePhotoHandler;

import java.io.File;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import uk.co.chrisjenx.calligraphy.CalligraphyConfig;

public class MainActivity extends AppCompatActivity implements onTakePhotoHandler {

    public static final String RANKING_FRAGMENT = "RANKING_FRAGMENT";
    public static final String PREPARATION_FRAGMENT = "PREPARATION_FRAGMENT";
    private static final String QUIZ_FRAGMENT = "QUIZ_FRAGMENT";
    public static final String PLAYER_ID = "PLAYER_ID";
    private static final String TAG = MainActivity.class.getSimpleName();
    private long currentObjectId;

    @BindView(R.id.mainAppBar) AppBarLayout mAppBarLayout;
    @BindView(R.id.collapsingToolbar) CollapsingToolbarLayout mCollapsingToolbarLayout;
    @BindView(R.id.fragmentContainer) FrameLayout mFragmentContainer;
    @BindView(R.id.mainFab) FloatingActionButton mFloatingActionButton;
    @BindView(R.id.mainToolbar) Toolbar mToolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 0);
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
                .commit();
    }

    @OnClick(R.id.mainFab)
    public void mainFab(){
        if(getCurrentFragment() instanceof RankingFragment){
            getSupportFragmentManager()
                    .beginTransaction()
                    .setCustomAnimations(R.anim.slide_left, R.anim.slide_lefter, R.anim.slide_right, R.anim.slide_righter)
                    .replace(R.id.fragmentContainer, new com.steveq.photoquiz.ui.fragments.PreparationFragment(), PREPARATION_FRAGMENT)
                    .addToBackStack(PREPARATION_FRAGMENT)
                    .commit();
        } else if(getCurrentFragment() instanceof com.steveq.photoquiz.ui.fragments.PreparationFragment){
            long id = ((com.steveq.photoquiz.ui.fragments.PreparationFragment)getCurrentFragment()).createPlayer();
            if(id > 0) {
                QuizFragment quiz = new QuizFragment();
                Bundle args = new Bundle();
                args.putLong(PLAYER_ID, id);
                quiz.setArguments(args);
                getSupportFragmentManager()
                        .beginTransaction()
                        .setCustomAnimations(R.anim.slide_left, R.anim.slide_lefter, R.anim.slide_right, R.anim.slide_righter)
                        .replace(R.id.fragmentContainer, quiz, QUIZ_FRAGMENT)
                        .addToBackStack(QUIZ_FRAGMENT)
                        .commit();
            } else {
                Toast.makeText(this, "You should insert a name", Toast.LENGTH_SHORT).show();
            }
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        DatabaseManager.getInstance(this).closeDb();
    }

    @TargetApi(24)
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

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(R.anim.slide_left, R.anim.slide_lefter);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(resultCode == RESULT_OK){
            String path = DatabaseManager.getInstance(this).getPath(currentObjectId);
            //call to google vision api
            if(DatabaseManager.getInstance(this).isSaving(QuizFragment.mCurrentPlayer)){
                MediaScannerConnection.scanFile(getApplicationContext(),
                        new String[]{path},
                        null,
                        new MediaScannerConnection.OnScanCompletedListener()
                        {
                            @Override
                            public void onScanCompleted(String path, Uri uri) {
                                if(uri == null){
                                    DatabaseManager.getInstance(com.steveq.photoquiz.ui.activities.MainActivity.this).deletePath(currentObjectId);
                                }
                            }
                        });
            } else {
                //delete photos from cache
                File cache = new File(path);
                Uri uri = Uri.parse(path);
                List<String> pathSegments = uri.getPathSegments();
                StringBuilder builder = new StringBuilder();
                for(int i=0; i < pathSegments.size()-2; i++){
                    builder.append(pathSegments.get(i));
                }
                Log.d(TAG, builder.toString());
                File cacheDir = new File(builder.toString());
            }

        } else if (resultCode != RESULT_CANCELED) {
            Toast.makeText(this, "Sorry, there was an error!", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void takePhotoHandle(long id, Intent intent) {
        currentObjectId = id;
        if(getCurrentFragment() instanceof QuizFragment){
            QuizFragment.mCurrentObject = id;
        }
        startActivityForResult(intent, QuestionsAdapter.REQUEST_TAKE_PHOTO);
    }
}
