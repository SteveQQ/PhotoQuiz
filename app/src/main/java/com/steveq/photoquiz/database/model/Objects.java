package com.steveq.photoquiz.database.model;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

@DatabaseTable(tableName = "objects_table")
public class Objects {
    @DatabaseField(generatedId = true)
    private long _id;

    @DatabaseField(unique = true)
    private String name;

    public Objects(){
    }

    public Objects(String name) {
        this.name = name;
    }

    public long get_id() {
        return _id;
    }

    public void set_id(long _id) {
        this._id = _id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
