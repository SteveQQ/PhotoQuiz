package com.steveq.photoquiz.database.model;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

@DatabaseTable(tableName = "players_table")
public class Players {

    @DatabaseField(generatedId = true)
    private long _id;

    @DatabaseField(canBeNull = false)
    private String name;

    @DatabaseField(defaultValue = "0")
    private int score;

    @DatabaseField(defaultValue = "false")
    private boolean saved;

    public Players(){

    }

    public Players(String name, boolean saved) {
        this.name = name;
        this.saved = saved;
    }

    public long getId() {
        return _id;
    }

    public void setId(long _id) {
        this._id = _id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public boolean isSaved() {
        return saved;
    }

    public void setSaved(boolean saved) {
        this.saved = saved;
    }
}
