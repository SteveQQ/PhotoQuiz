package com.steveq.photoquiz.database;


import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.j256.ormlite.android.apptools.OrmLiteSqliteOpenHelper;
import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.table.TableUtils;
import com.steveq.photoquiz.R;
import com.steveq.photoquiz.database.model.Objects;
import com.steveq.photoquiz.database.model.Players;

import java.sql.SQLException;

public class QuizOpenDatabaseHelper extends OrmLiteSqliteOpenHelper {

    private static final String DATABASE_NAME = "quiz_db";
    private static final int DATABASE_VERSION = 1;
    private static final String TAG = QuizOpenDatabaseHelper.class.getSimpleName();

    private Dao<Players, Long> usersDao = null;
    private Dao<Objects, Long> objectsDao = null;

    public Dao<Players, Long> getPlayersDao() throws SQLException {
        if(usersDao == null){
            usersDao = getDao(Players.class);
        }
        return usersDao;
    }

    public Dao<Objects, Long> getObjectsDao() throws SQLException {
        if(objectsDao == null){
            objectsDao = getDao(Objects.class);
        }
        return objectsDao;
    }

    public QuizOpenDatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }


    @Override
    public void onCreate(SQLiteDatabase database, ConnectionSource connectionSource) {
        try{
            TableUtils.createTable(connectionSource, Players.class);
            TableUtils.createTable(connectionSource, Objects.class);
        } catch (SQLException sqle){
            Log.e(TAG, "Can't create database");
            sqle.printStackTrace();
        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase database, ConnectionSource connectionSource, int oldVersion, int newVersion) {

    }

}
