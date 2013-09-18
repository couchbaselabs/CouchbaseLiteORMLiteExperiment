package com.example.todoliteandroid.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.example.todoliteandroid.TodoLiteTask;
import com.j256.ormlite.android.apptools.OrmLiteSqliteOpenHelper;
import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.table.TableUtils;

import java.sql.SQLException;

public class DatabaseHelper extends OrmLiteSqliteOpenHelper {

    /************************************************
     * Suggested Copy/Paste code. Everything from here to the done block.
     ************************************************/

    private static final String DATABASE_NAME = "todolite.db";
    private static final int DATABASE_VERSION = 1;

    private Dao<TodoLiteTask, Integer> todoLiteTaskDao;

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);  // TO-DO: configfile optimization: http://bit.ly/16dnHVf
    }

    /************************************************
     * Suggested Copy/Paste Done
     ************************************************/

    @Override
    public void onCreate(SQLiteDatabase sqliteDatabase, ConnectionSource connectionSource) {
        try {
            TableUtils.createTable(connectionSource, TodoLiteTask.class);
        } catch (SQLException e) {
            Log.e(DatabaseHelper.class.getName(), "Unable to create datbases", e);
        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqliteDatabase, ConnectionSource connectionSource, int oldVer, int newVer) {
        try {
            TableUtils.dropTable(connectionSource, TodoLiteTask.class, true);
            onCreate(sqliteDatabase, connectionSource);
        } catch (SQLException e) {
            Log.e(DatabaseHelper.class.getName(), "Unable to upgrade database from version " + oldVer + " to new "
                    + newVer, e);
        }
    }

    public Dao<TodoLiteTask, Integer> getTodoLiteTaskDao() throws SQLException {
        if (todoLiteTaskDao == null) {
            todoLiteTaskDao = getDao(TodoLiteTask.class);
        }
        return todoLiteTaskDao;
    }


}
