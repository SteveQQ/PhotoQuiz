package com.steveq.photoquiz.database;


import android.content.Context;

import com.j256.ormlite.android.apptools.OpenHelperManager;
import com.j256.ormlite.stmt.QueryBuilder;
import com.j256.ormlite.stmt.UpdateBuilder;
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

    public void addNewPlayer(String name, boolean saved){
        try {
            getHelper().getPlayersDao().create(new Players(name, saved));
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void updatePlayerScore(long id, int score){
        try {
            UpdateBuilder<Players, Long> updateBuilder = getHelper().getPlayersDao().updateBuilder();
            updateBuilder.where().eq("_id", id);
            updateBuilder.updateColumnValue("score", score);
            updateBuilder.update();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public boolean isSaved(long id){
        List<Players> results = null;
        try {
            QueryBuilder<Players, Long> queryBuilder = getHelper().getPlayersDao().queryBuilder();
            queryBuilder.where().eq("_id", id);
            queryBuilder.where().eq("saved", true);
            results = queryBuilder.query();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return results.isEmpty();
    }

    public void fillObjectsListRandomly(){

    }
}
