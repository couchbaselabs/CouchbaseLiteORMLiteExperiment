package com.example.todoliteandroid.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.example.todoliteandroid.model.TodoLiteList;
import com.example.todoliteandroid.model.TodoLiteTask;
import com.j256.ormlite.android.apptools.OrmLiteSqliteOpenHelper;
import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.table.TableUtils;

import java.sql.SQLException;

public class DatabaseHelper extends OrmLiteSqliteOpenHelper {

    private static final String DATABASE_NAME = "todolite.db";
    private static final int DATABASE_VERSION = 1;

    private Dao<TodoLiteTask, Integer> todoLiteTaskDao;
    private Dao<TodoLiteList, Integer> todoLiteListDao;


    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);  // TO-DO: configfile optimization: http://bit.ly/16dnHVf
    }

    @Override
    public void onCreate(SQLiteDatabase sqliteDatabase, ConnectionSource connectionSource) {
        try {
            TableUtils.createTable(connectionSource, TodoLiteTask.class);
            TableUtils.createTable(connectionSource, TodoLiteList.class);

        } catch (SQLException e) {
            Log.e(DatabaseHelper.class.getName(), "Unable to create datbases", e);
        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqliteDatabase, ConnectionSource connectionSource, int oldVer, int newVer) {
        try {
            TableUtils.dropTable(connectionSource, TodoLiteTask.class, true);
            TableUtils.dropTable(connectionSource, TodoLiteList.class, true);
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

    public Dao<TodoLiteList, Integer> getTodoLiteListDao() throws SQLException {
        if (todoLiteListDao == null) {
            todoLiteListDao = getDao(TodoLiteList.class);
        }
        return todoLiteListDao;
    }

}
