package com.steveq.photoquiz.database;


import android.content.Context;

import com.j256.ormlite.android.apptools.OpenHelperManager;
import com.j256.ormlite.stmt.QueryBuilder;
import com.steveq.photoquiz.database.model.Players;

import java.sql.SQLException;
import java.util.List;

public class DatabaseManager {
    private static DatabaseManager instance;
    private QuizOpenDatabaseHelper dbHelper;

    public static DatabaseManager getInstance(Context ctx){
        if(instance == null){
            instance = new DatabaseManager(ctx);
        }
        return instance;
    }

    private DatabaseManager(Context ctx){
        dbHelper = OpenHelperManager.getHelper(ctx, QuizOpenDatabaseHelper.class);
    }

    private QuizOpenDatabaseHelper getHelper(){
        return dbHelper;
    }

    public List<Players> getBestPlayers(){
        List<Players> bestPlayers = null;
        try {
            QueryBuilder<Players, Long> builder = getHelper().getPlayersDao().queryBuilder();
            builder.limit(10L);
            builder.orderBy("score", false);
            bestPlayers = getHelper().getPlayersDao().query(builder.prepare());
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return bestPlayers;
    }
}
