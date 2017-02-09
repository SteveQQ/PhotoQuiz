package com.steveq.photoquiz.database;


import android.content.Context;

import com.j256.ormlite.android.apptools.OpenHelperManager;
import com.j256.ormlite.stmt.QueryBuilder;
import com.j256.ormlite.stmt.UpdateBuilder;
import com.steveq.photoquiz.database.model.Objects;
import com.steveq.photoquiz.database.model.Players;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

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

    public long addNewPlayer(String name, boolean saved){
        Players newPlayer = null;
        try {
            newPlayer = new Players(name, saved);
            getHelper().getPlayersDao().create(newPlayer);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return newPlayer.getId();
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

    public boolean isSaving(long id){
        List<Players> results = null;
        try {
            QueryBuilder<Players, Long> queryBuilder = getHelper().getPlayersDao().queryBuilder();
            queryBuilder.selectColumns("saved");
            queryBuilder.where().eq("_id", id);
            results = queryBuilder.query();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return (results.get(0)).isSaved();
    }

    public List<Objects> getRandomListObject(){
        List<Objects> result = new ArrayList<>();
        Random randomGenerator = new Random();
        try {
            List<Objects> temp = getHelper().getObjectsDao().queryForAll();
            while(result.size() < 10){
                int index = randomGenerator.nextInt(temp.size());
                result.add(temp.get(index));
                temp.remove(index);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return result;
    }

    public void seedObjectsTable(String[] data){
        for(String s : data){
            Objects object = new Objects(s);
            try {
                getHelper().getObjectsDao().create(object);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
}
